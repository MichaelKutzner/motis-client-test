// import org.openapitools.client.custom.api.TimetableApiEndpointsImpl
// import org.http4s.Uri
// import org.http4s.client.Client
// import org.http4s.client.JavaNetClientBuilder
// import scala.concurrent.ExecutionContext
// import java.util.concurrent.Executors
//
// import cats.effect._
// import java.util.concurrent._
// import org.http4s.client._
// import org.http4s.implicits._
// import org.http4s.dsl._
// import org.http4s._
// import scala.concurrent.duration.Duration
// // import cats.effect.IO
// // import sttp.client4.DefaultSyncBackend

import io.circe.Json
import io.circe.Encoder

// import org.openapitools.client.models.MyObject
// import org.openapitools.client.models.MyObject.{given Encoder[MyObject]}
// import org.openapitools.client.models.{given Encoder[?]}
import org.openapitools.client.models.MyObject.encoderMyObject
import org.openapitools.client.apis.JsonSupports.given
import org.openapitools.client.models.*

@main def main(): Unit =
  val left = MyLeft(
    left = Some("Hello, World"),
    value = Some(3.14),
    eitherType = "MyLeft",
  )
  val right = MyRight(right = Some("Test"), value = Some("String value"))
  val obj =
    MyObject(id = Some(42), value = Some("Test entry"), feature = Some(left))
  println(s"obj: '${MyObject.encoderMyObject(obj)}'")
  // println(s"obj: '${obj.as[Json]}'")
  // println(s"obj(v2): '${obj.as[Json](using encoderMyObject)}'")
  println(s"left: ${MyEither.encoderMyEither(left)}")
  println(s"right: ${MyEither.encoderMyEither(right)}")
import org.openapitools.client.models.MyObject
