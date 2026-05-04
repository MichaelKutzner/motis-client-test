val scala3Version = "3.7.4"
val circeVersion = "0.14.15"
val http4sVersion = "0.23.34"

lazy val root = project
  .in(file("."))
  .aggregate(client)
  .dependsOn(client)
  // .enablePlugins(ScalaNativePlugin)
  .settings(
    name := "sandbox-client",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      // "io.circe" %% "circe-literal" % circeVersion,
      "org.http4s" %% "http4s-ember-client" % http4sVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
      "org.scalameta" %% "munit" % "1.0.0" % Test,
    ),
  )

lazy val client = project
  .in(file("http4s-client"))
  .enablePlugins(OpenApiGeneratorPlugin)
  // .enablePlugins(ScalaNativePlugin)
  .settings(
    name := "Sandbox Demo Client",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    openApiInputSpec := "openapi.yaml",
    openApiConfigFile := "config-http4s.yaml",
    libraryDependencies += "org.typelevel" %% "cats-effect" % "3.6.3",
  )

lazy val pythonClient = project
  .in(file("python-client"))
  .enablePlugins(OpenApiGeneratorPlugin)
  .settings(
    name := "Sandbox Demo Client (Python)",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    openApiInputSpec := "openapi.yaml",
    openApiConfigFile := "config-python.yaml",
  )
