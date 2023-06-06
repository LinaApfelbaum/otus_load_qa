package webtours

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import scala.util.Random


object CommonScenario {
  def apply(): ScenarioBuilder = new CommonScenario().scn
}
class CommonScenario {
  val scn: ScenarioBuilder = scenario("Common scenario")
    .exec(Actions.getMain)
    .exec(Actions.getUserSession)
    .exec(Actions.login)
    .exec(Actions.openFlightsPage)
    .exec(Actions.getCities)
    .exec { session =>
      val cities = session("cities").as[List[String]]
      val departCity = Random.shuffle(cities).head
      val arriveCity = Random.shuffle(cities).head

      session.setAll(Map("departCity" -> departCity, "arriveCity" -> arriveCity))
    }
    .asLongAs(session => { //to ensure depart city and arrive city don't match
      session("departCity").as[String] == session("arriveCity").as[String]
    }) {
      exec { session =>
        val cities = session("cities").as[List[String]]
        val arriveCity = Random.shuffle(cities).head
        session.set("arriveCity", arriveCity)
      }
    }
    .exec(Actions.selectFlight)
    .exec(Actions.selectTicket)
    .exec(Actions.buyTicket)
    .exec(Actions.visitHome)
}
