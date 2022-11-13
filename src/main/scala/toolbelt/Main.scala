package toolbelt

import zhttp.http._
import zhttp.service.Server
import zio._
import zio.interop.catz.*
import toolbelt.customer.CustomerApp
import toolbelt.customer.CustomerRepositoryLive

object Main extends ZIOAppDefault {
  override val run: ZIO[Scope, Throwable, ExitCode] =
    (for {
      _ <- DatabaseMigrator.migrate
      _ <- Server.start(8090, http = CustomerApp())
    } yield ExitCode.success).provideSome[Scope](
      DataSourceLive.layer,
      DatabaseMigratorLive.layer,
      CustomerRepositoryLive.layer,
    )
}
