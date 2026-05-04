val scala3Version = "3.7.4"
val circeVersion = "0.14.15"

lazy val generated = project
  .in(file("generated"))
  .enablePlugins(OpenApiGeneratorPlugin)
  // .enablePlugins(ScalaNativePlugin)
  .settings(
    name := "Sandbox Demo Client",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    openApiInputSpec := "openapi.yaml",
    openApiConfigFile := "config.yaml",
    libraryDependencies += "org.typelevel" %% "cats-effect" % "3.6.3",
  )

lazy val root = project
  .in(file("."))
  .aggregate(generated)
  .dependsOn(generated)
  // .enablePlugins(ScalaNativePlugin)
  .settings(
    name := "sandbox-client",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "org.scalameta" %% "munit" % "1.0.0" % Test,
    ),
    // libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test,
  )
