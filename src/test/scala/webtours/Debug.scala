package webtours

import io.gatling.core.Predef._

class Debug extends Simulation {

  setUp(
    CommonScenario().inject(
      constantConcurrentUsers(5).during(3600), // 1
//      constantConcurrentUsers(1).during(180), // 1
//      rampConcurrentUsers(1).to(2).during(204), // 2
//      rampConcurrentUsers(2).to(3).during(204), // 3
//      rampConcurrentUsers(3).to(4).during(204), // 4
//      rampConcurrentUsers(4).to(5).during(204), // 5
//      rampConcurrentUsers(5).to(6).during(204), // 6
    )
  )
    .protocols(httpProtocol)
    .assertions(global.responseTime.max.lt(3000))
}
