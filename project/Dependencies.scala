import sbt._

object Dependencies {
  val ZioVersion   = "2.0.2"
  val ZHTTPVersion = "2.0.0-RC11"

  val `zio-http`      = "io.d11" %% "zhttp" % ZHTTPVersion
  val `zio-http-test` = "io.d11" %% "zhttp" % ZHTTPVersion % Test

  val `zio-test`         = "dev.zio" %% "zio-test"         % ZioVersion % Test
  val `zio-test-sbt`     = "dev.zio" %% "zio-test-sbt"     % ZioVersion % Test
  val `zio-interop-cats` = "dev.zio" %% "zio-interop-cats" % "3.3.0"
  val `zio-json`         = "dev.zio" %% "zio-json"         % "0.3.0"

  val `doobie-core`     = "org.tpolecat"  %% "doobie-core"     % "1.0.0-RC2"
  val `doobie-postgres` = "org.tpolecat"  %% "doobie-postgres" % "1.0.0-RC2"
  val `hikari`          = "com.zaxxer"     % "HikariCP"        % "5.0.1"
  val `postgres`        = "org.postgresql" % "postgresql"      % "42.5.0"
  val `flyway`          = "org.flywaydb"   % "flyway-core"     % "9.7.0"

  val `zio-config`          = "dev.zio" %% "zio-config"          % "3.0.1"
  val `zio-config-typesafe` = "dev.zio" %% "zio-config-typesafe" % "3.0.1"
  val `zio-config-magnolia` = "dev.zio" %% "zio-config-magnolia" % "3.0.1"
}
