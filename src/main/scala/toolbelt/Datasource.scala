package toolbelt

import zio.Task
import zio.*
import com.zaxxer.hikari.HikariDataSource
import com.zaxxer.hikari.HikariConfig

object DataSourceLive {
  private def datasource =
    ZIO.fromAutoCloseable(
      ZIO.attempt(
        new HikariDataSource(
          hikariConfig("jdbc:postgresql://localhost:5432/toolbelt", "toolbelt-user", "password"),
        ),
      ),
    )

  private def hikariConfig(
      jdbcUrl: String,
      username: String,
      password: String,
    ): HikariConfig = {
    val hikariConfig = new HikariConfig()
    hikariConfig.setJdbcUrl(jdbcUrl)
    hikariConfig.setUsername(username)
    hikariConfig.setPassword(password)
    hikariConfig
  }

  val layer = ZLayer.fromZIO(datasource)
}
