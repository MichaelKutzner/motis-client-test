val scala3Version = "3.7.4"
// val client4Version = "4.0.13" // sttp4 does not work right now
val client3Version = "3.11.0"
val circeVersion = "0.14.15"

lazy val generated = project
  .in(file("generated"))
  .enablePlugins(OpenApiGeneratorPlugin)
  .settings(
    name := "motis-client",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    openApiInputSpec := "../openapi.yaml",
    openApiConfigFile := "config.yaml",
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client3" %% "core" % client3Version,
      "com.softwaremill.sttp.client3" %% "circe" % client3Version,
      "io.circe" %% "circe-generic" % circeVersion,
    ),
  )

lazy val root = project
  .in(file("."))
  .aggregate(generated)
  .dependsOn(generated)
  .settings(
    name := "motis-client-test",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    addCommandAlias("clientTest", "openApiGenerate;reload;run"),
  )
