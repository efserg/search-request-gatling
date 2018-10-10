import com.typesafe.config.{Config, ConfigFactory}
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._

class BasicSimulation extends Simulation {

  private val config: Config = ConfigFactory.load("performanceTest.conf")
  private val baseUrl = config.getString("url")
  private val stressUsers = config.getInt("stress-users")
  private val stressDuration = config.getInt("stress-duration")
  private val warmupUsers = config.getInt("warmup-users")
  private val warmupDuration = config.getInt("warmup-duration")

  val httpConf: HttpProtocolBuilder =
    http
      .baseURL(baseUrl)
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

  val scn: ScenarioBuilder =
    scenario("Search requests logging simulation")
      .exec(http("Send search request")
        .post("/add")
        .body(ElFileBody("user-files/bodies/search-request.json")).asJSON)
  //      .exec(_.set("${timestamp}", System.currentTimeMillis() / 1000 + Math.random()))
  //      .exec(_.set("${uid}", RandomStringUtils.randomAlphabetic(2) + RandomStringUtils.randomNumeric(5)))


  setUp(
    scn.inject(
      rampUsers(warmupUsers) over (warmupDuration second),
      constantUsersPerSec(stressUsers) during (stressDuration second),
    )
  ).protocols(httpConf)
}
