package toolbelt

import zio._
import zio.config._
import zio.config.magnolia.descriptor
import zio.config.typesafe.TypesafeConfigSource
import zio.interop.catz.*
import org.flywaydb.core.Flyway
import javax.sql.DataSource
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

trait DatabaseMigrator {
  def migrate: Task[Unit]
}

object DatabaseMigrator {
  def migrate = ZIO.serviceWithZIO[DatabaseMigrator](_.migrate)
}

final case class DatabaseMigrationConfig(migrationsLocation: String)

object DatabaseMigratorLive {
  val config = read {
    descriptor[DatabaseMigrationConfig].from(
      TypesafeConfigSource
        .fromResourcePath
        .at(PropertyTreePath.$("DatabaseMigration")),
    )
  }
  val layer  = ZLayer {
    for {
      ds <- ZIO.service[DataSource]
      c  <- config
    } yield DatabaseMigratorLive(ds, c)
  }
}

final case class DatabaseMigratorLive(ds: DataSource, config: DatabaseMigrationConfig)
    extends DatabaseMigrator {
  def migrate: Task[Unit] =
    ZIO.logInfo("Applying database migrations...") *> ZIO.attemptBlocking {
      Flyway
        .configure
        .dataSource(ds)
        .locations(config.migrationsLocation)
        .load
        .migrate()
    }.flatMap { migration =>
      import migration.*
      val initialVersion = if (initialSchemaVersion == null) 0 else initialSchemaVersion
      ZIO.logInfo(s"Migrated schema $schemaName from $initialVersion to $targetSchemaVersion")
    }
}
