import cats.effect.IO
import cats.effect.unsafe.IORuntime

// import org.http4s.dsl.io.*
import org.http4s.implicits.uri
import org.http4s.ember.client.EmberClientBuilder

// import io.circe.*
// import io.circe.generic.auto.*
import io.circe.Json
import io.circe.Encoder
import io.circe.syntax._ // Required for _.asJson

import example.client.models.{given Encoder[?]}
import example.client.models.MyObject.encoderMyObject
// import example.client.models.given
import example.client.models.*
import example.client.apis.DefaultApiEndpointsImpl

@main def main(): Unit =
  val left = MyLeft(
    left = Some("Hello, World"),
    myValue = Some(3.14),
    // typeType = MyLeftType.MyLeft,
    `type` = MyLeftType.MyLeft,
  )
  val right = MyRight(right = Some("Test"), myValue = Some("String value"))
  val obj =
    MyObject(`@id` = 42, myValue = Some("Test entry"), feature = Some(left))
  // println(s"obj: '${MyObject.encoderMyObject(obj)}'"))
  println(s"obj: '${obj.asJson}'")
  // println(s"obj(v2): '${obj.asJson(using encoderMyObject)}'")
  println(s"left: ${left.asJson}")
  println(s"right: ${right.asJson}")
  println(s"right(as MyEither): ${MyEither.encoderMyEither(right)}")
  println(
    s"obj with right: ${obj.copy(feature = Some(right)).asJson}",
  )

  // Test request
  given runtime: IORuntime = cats.effect.unsafe.IORuntime.global
  testSend(obj).unsafeRunSync()
  testSend(MyObject(`@id` = 123, feature = Some(right))).unsafeRunSync()

def testSend(obj: MyObject): IO[MyObject] =
  EmberClientBuilder.default[IO].build.use { httpClient =>
    val client = DefaultApiEndpointsImpl(
      uri"http://localhost:8000",
      httpClient = httpClient,
    )
    val resp = client.echoPost(obj)
    resp.map(r =>
      println("Request completet:")
      println(s"POST(${obj}) ⇒ ${r}")
      println(r.asJson)
      println(r)
      r,
    )
  }
