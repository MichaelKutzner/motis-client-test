val scala3Version = "3.7.4"
val catsEffectVersion = "3.6.0"

lazy val generated = project
  .in(file("generated"))
  .enablePlugins(OpenApiGeneratorPlugin)
  .settings(
    name := "motis-client",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    openApiInputSpec := "../openapi.yaml",
    openApiConfigFile := "config.yaml",
    libraryDependencies += "org.typelevel" %% "cats-effect" % catsEffectVersion,
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
