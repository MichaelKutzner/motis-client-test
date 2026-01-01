## About
This repository contains minimal examples how to create and use a MOTIS client for Scala 3.

The client generation code is based on this example:
https://github.com/OpenAPITools/sbt-openapi-generator/tree/master/src/sbt-test/sbt-openapi-generator/simple

## sttp4_default
Simple to use client using `json4s`.

## sttp_circe
Modification of `sttp4_default`. Uses `circe`.
Requires `scala-sttp` instead of `scala-sttp4`, because of hardcoded `sttp.client3`.

## sttp_circe_native
Modification of `sttp_circe`.
Attempt to compile with Scala Native.

## http4s_default
Functional client using Cats Effect.

## http4s_native
Attempt to compile with Scala Native.
Uses outdated `http4s` version, that does not support Scala Native.

