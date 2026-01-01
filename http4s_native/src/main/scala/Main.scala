import org.openapitools.client.motis.api.{
  GeocodeApiEndpointsImpl,
  TimetableApiEndpointsImpl,
}
import org.openapitools.client.models.{Mode, LocationType, Stoptimes200Response}

import cats.effect.{IO, IOApp}
import org.http4s.client.Client
import org.http4s.implicits.uri
import org.http4s.ember.client.EmberClientBuilder

object ClientTester extends IOApp.Simple:
  def run: IO[Unit] =
    EmberClientBuilder.default[IO].build.use { client =>
      getStopId(stationName)(using client)
        .map(_.map(stopId => getStopTimes(stopId)(using client)))
        .flatMap(_ match
          case Left(error) => IO.println(error)
          case Right(stopTimes) =>
            stopTimes
              .map(
                _.stopTimes
                  .foldLeft("")(_ + '\n' + _.toString() + '\n'),
              )
              .flatMap(IO.println),
        )
    }

def getStopId(station: String)(using
    client: Client[IO],
): IO[Either[String, String]] =
  GeocodeApiEndpointsImpl(baseUrl = baseUrl, httpClient = client)
    .geocode(
      text = stationName,
      `type` = Some(LocationType.STOP),
      mode = Some(modes),
      language = None,
    )
    .map(_ match
      case x :: xs => Right(x.id)
      case _       => Left("Request failed!"),
    )

def getStopTimes(stopId: String, count: Int = 5)(using
    client: Client[IO],
): IO[Stoptimes200Response] =
  TimetableApiEndpointsImpl(baseUrl = baseUrl, httpClient = client)
    .stoptimes(
      stopId = stopId,
      n = count,
      mode = Some(modes),
      language = None,
    )

def baseUrl = uri"https://api.transitous.org"
def stationName = "Berlin Hbf, Berlin"
def modes = Seq(Mode.RAIL, Mode.BUS)
