val scala3Version = "3.7.4"
// val client4Version = "4.0.13" // sttp4 does not work right now
val client3Version = "3.11.0"
val circeVersion = "0.14.15"
val scalaJavaTimeVersion = "2.6.0"

lazy val generated = project
  .in(file("generated"))
  .enablePlugins(OpenApiGeneratorPlugin)
  .enablePlugins(ScalaNativePlugin)
  .settings(
    name := "motis-client",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    openApiInputSpec := "../openapi.yaml",
    openApiConfigFile := "config.yaml",
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client3" %%% "core" % client3Version,
      "com.softwaremill.sttp.client3" %%% "circe" % client3Version,
      "io.circe" %%% "circe-generic" % circeVersion,
      // Provided implementation of the java.time.package
      "io.github.cquiroz" %%% "scala-java-time" % scalaJavaTimeVersion,
      "io.github.cquiroz" %%% "scala-java-time-tzdb" % scalaJavaTimeVersion,
    ),
  )

lazy val root = project
  .in(file("."))
  .aggregate(generated)
  .dependsOn(generated)
  .enablePlugins(ScalaNativePlugin)
  .settings(
    name := "motis-client-test",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    addCommandAlias("clientTest", "openApiGenerate;reload;nativeLink"),
  )
