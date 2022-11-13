import Dependencies._

ThisBuild / organization := "immaterial.industries"
ThisBuild / version      := "1.0.0"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(BuildHelper.stdSettings)
  .settings(
    name := "toolbelt-api",
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
    libraryDependencies ++= Seq(
      `zio-test`,
      `zio-test-sbt`,
      `zio-http`,
      `zio-http-test`,
      `zio-interop-cats`,
      `zio-json`,
      `doobie-core`,
      `doobie-postgres`,
      `hikari`,
      `postgres`,
      `zio-config`,
      `zio-config-typesafe`,
      `zio-config-magnolia`,
      `flyway`,
    ),
  )
  .settings(
    Docker / version          := version.value,
    Compile / run / mainClass := Option("toolbelt.Main"),
  )

addCommandAlias("fmt", "scalafmt; Test / scalafmt; sFix;")
addCommandAlias("fmtCheck", "scalafmtCheck; Test / scalafmtCheck; sFixCheck")
addCommandAlias("sFix", "scalafix OrganizeImports; Test / scalafix OrganizeImports")
addCommandAlias(
  "sFixCheck",
  "scalafix --check OrganizeImports; Test / scalafix --check OrganizeImports",
)
