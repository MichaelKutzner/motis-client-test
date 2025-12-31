import org.openapitools.client.motis.api.{GeocodeApi, TimetableApi}
import org.openapitools.client.model.{
  Mode,
  ModelMatch,
  LocationType,
  Stoptimes200Response,
}

import sttp.client3.{HttpURLConnectionBackend, Identity, SttpBackend}

@main def clientTest(): Unit =
  val backend = HttpURLConnectionBackend()

  val stopTimes = for
    stopId <- getStopId(stationName)(using backend)
    stopTimes <- getStopTimes(stopId)(using backend)
  yield stopTimes.stopTimes.foldLeft("")(_ + '\n' + _.toString() + '\n')
  println(stopTimes)

def getStopId[A](station: String)(using
    backend: SttpBackend[Identity, A],
): Either[String, String] =
  GeocodeApi(baseUrl)
    .geocode(
      text = stationName,
      `type` = Some(LocationType.STOP),
      mode = modes,
      language = Seq(),
    )
    .send(backend)
    .body match
    case Left(_) => Left("Request failed!")
    case Right(matches: (Seq[ModelMatch] | List[ModelMatch])) =>
      matches match
        case x :: xs => Right(x.id)
        case _       => Left("Nothing matched!")

def getStopTimes[A](stopId: String, count: Int = 5)(using
    backend: SttpBackend[Identity, A],
): Either[String, Stoptimes200Response] =
  TimetableApi(baseUrl)
    .stoptimes(
      stopId = stopId,
      n = count,
      mode = modes,
      language = Seq(),
    )
    .send(backend)
    .body match
    case Left(_)                                => Left("Request failed!")
    case Right(stopTimes: Stoptimes200Response) => Right(stopTimes)

def baseUrl = "https://api.transitous.org"
def stationName = "Berlin Hbf, Berlin"
def modes = Seq(Mode.RAIL, Mode.BUS)
