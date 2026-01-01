val scala3Version = "3.7.4"
val catsEffectVersion = "3.7-4972921" // 3.6.0 only supports ScalaNative 0.4
val catsEffectVaultVersion = "3.7.0-RC1" // 3.6.0 only supports ScalaNative 0.4
val circeVersion = "0.14.15"
// val http4sVersion = "0.23.33"
val http4sVersion = "1.0.0-M45" // Causes conflict with hardcoded version

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
      "org.typelevel" %%% "cats-effect" % catsEffectVersion,
      "org.typelevel" %%% "vault" % catsEffectVaultVersion,
      "io.circe" %%% "circe-generic" % circeVersion,
      // "org.http4s" %% "http4s-core" % http4sVersion,
      "org.http4s" %% "http4s-client" % http4sVersion,
      // "org.http4s" %% "http4s-ember-client" % http4sVersion,
      // "org.http4s" %% "http4s-circe" % http4sVersion,
      // "org.http4s" %% "http4s-dsl" % http4sVersion,
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
