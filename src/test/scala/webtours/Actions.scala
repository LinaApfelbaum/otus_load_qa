package webtours

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object Actions {
  val getMain: HttpRequestBuilder = http("getMainPage")
    .get("/webtours")
    .check(status is 200)

  val getUserSession: HttpRequestBuilder = http("getUserSession")
    .get("/cgi-bin/nav.pl?in=home")
    .check(status is 200)
    .check(regex("name=\"userSession\" value=\"(.+)\"").saveAs("userSession"))

  val login: HttpRequestBuilder = http("login")
    .post("/cgi-bin/login.pl")
    .formParamMap(Map(
      "userSession" -> "#{userSession}",
      "username" -> "unique",
      "password" -> "unique"
    ))
    .check(regex("""User password was correct"""))

  val openFlightsPage: HttpRequestBuilder = http("openFlightsPage")
    .get("/cgi-bin/welcome.pl?page=search")
    .check(status is 200)

  val getCities: HttpRequestBuilder = http("getCities")
    .get("/cgi-bin/reservations.pl?page=welcome")
    .check(status is 200)
    .check(regex("""<option.*>(.*)</option>""").findAll.saveAs("cities"))

  val selectFlight: HttpRequestBuilder = http("selectFlight")
    .post("/cgi-bin/reservations.pl")
    .formParamMap(Map(
      "depart" -> "#{departCity}",
      "arrive" -> "#{arriveCity}",
      "departDate" -> "06/05/2023",
      "numPassengers" -> "1",
      "findFlights.x" -> "46"
    ))
    .check(regex("""Flight departing from"""))
    .check(regex("""outboundFlight" value="(.+?)"""").saveAs("outboundFlight"))

  val selectTicket: HttpRequestBuilder = http("selectTicket")
    .post("/cgi-bin/reservations.pl")
    .formParamMap(Map(
      "outboundFlight" -> "#{outboundFlight}",
      "reserveFlights.x" -> "34"
    ))
    .check(regex("""Flight Reservation"""))

  val buyTicket: HttpRequestBuilder = http("buyTicket")
    .post("/cgi-bin/reservations.pl")
    .formParamMap(Map(
      "outboundFlight" -> "#{outboundFlight}",
      "firstName" -> "unique",
      "lastName" -> "unique",
      "buyFlights.x" -> "69"
    ))
    .check(regex("""Reservation Made!"""))

  val visitHome: HttpRequestBuilder = http("visitHome")
    .get("/cgi-bin/login.pl?intro=true")
    .check(regex("""Don't forget to sign off"""))
}
