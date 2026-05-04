import cats.effect.IO
import cats.effect.unsafe.IORuntime

// import org.http4s.dsl.io.*
import org.http4s.implicits.uri
import org.http4s.ember.client.EmberClientBuilder

// import io.circe.*
// import io.circe.generic.auto.*
import io.circe.Json
import io.circe.Encoder

// import example.client.models.MyObject
// import example.client.models.MyObject.{given Encoder[MyObject]}
// import example.client.models.{given Encoder[?]}
import example.client.models.MyObject.encoderMyObject
// import example.client.apis.JsonSupports.given
import example.client.models.*
import example.client.apis.DefaultApiEndpointsImpl

@main def main(): Unit =
  val left = MyLeft(
    left = Some("Hello, World"),
    value = Some(3.14),
    eitherType = MyLeftEitherType.MyLeft,
  )
  val right = MyRight(right = Some("Test"), value = Some("String value"))
  val obj =
    MyObject(id = Some(42), value = Some("Test entry"), feature = Some(left))
  println(s"obj: '${MyObject.encoderMyObject(obj)}'")
  // println(s"obj: '${obj.as[Json]}'")
  // println(s"obj(v2): '${obj.as[Json](using encoderMyObject)}'")
  println(s"left: ${MyEither.encoderMyEither(left)}")
  println(s"right: ${MyEither.encoderMyEither(right)}")
  println(s"right(raw): ${MyRight.encoderMyRight(right)}")

  // Test request
  given runtime: IORuntime = cats.effect.unsafe.IORuntime.global
  testSend(obj).unsafeRunSync()
  testSend(MyObject(id = Some(123), feature = Some(right))).unsafeRunSync()

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
      println(MyObject.encoderMyObject(r))
      println(r)
      r,
    )
  }
