/**
 * Copyright 2011-2017 GatlingCorp (http://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.typesafe.config.{Config, ConfigFactory}
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._

class BasicSimulation extends Simulation {

  private val config: Config = ConfigFactory.load("performanceTest.conf")
  private val baseUrl = config.getString("url")
  private val users = config.getInt("users")
  private val duration = config.getInt("duration")

  val httpConf: HttpProtocolBuilder =
    http
      .baseURL(baseUrl)
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

  val scn: ScenarioBuilder =
    scenario("Search requests logging simulation")
//      .feed(cities)
      .exec(http("Send search request")
        .post("/add")
        .body(ElFileBody("user-files/bodies/search-request.json"))
        .asJSON)

  setUp(
    scn.inject(rampUsers(users) over (duration seconds))
  ).protocols(httpConf)
}
