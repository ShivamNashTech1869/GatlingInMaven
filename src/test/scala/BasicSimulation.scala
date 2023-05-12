import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import scala.concurrent.duration._
import scala.language.postfixOps
import io.gatling.core.Predef._
import io.gatling.core.feeder.BatchableFeederBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.util.Try

class BasicSimulation extends Simulation {

  // Read simulation parameters from the terminal
  val baseUrl: String = System.getProperty("baseUrl", "https://reqres.in/api/")
  val userFeederPath: String = System.getProperty("userFeederPath", "user_data.csv")
  val pauseDuration: Int = readInt("Enter pause duration in seconds (default: 1): ")
  val nothingForDuration: Int = readInt("Enter nothingFor duration in seconds (default: 5): ")
  val atOnceUsersValue: Int = readInt("Enter atOnceUsers value (default: 50): ")
  val constUsersPerSecond: Double = readDouble("Enter constUsersPerSecond value (default: 20): ")
  val constDuration: Int = readInt("Enter constDuration in seconds (default: 15): ")
  val rampUsersPerSecond: Double = readDouble("Enter rampUsersPerSecond value (default: 100): ")
  val rampDuration: Int = readInt("Enter rampDuration in seconds (default: 30): ")
  val globalResponseTime: Int = readInt("Enter global response time in milliseconds (default: 5000): ")
  val globalSuccessfulRequestsPercent: Double = readDouble("Enter global successful requests percent (default: 99): ")
  val maxSimulationDuration: Int = readInt("Enter max simulation duration in minutes (default: 1): ")

  // some using status code
  val created = 201
  val ok = 200
  val noContent = 204

  val httpProtocol: HttpProtocolBuilder = http.baseUrl(baseUrl)
  val userFeeder: BatchableFeederBuilder[String] = csv(userFeederPath).random

  // Helper method to read Int from the terminal
  def readInt(prompt: String): Int = {
    val input = System.console().readLine(prompt)
    Try(input.toInt).getOrElse(0)
  }

  // Helper method to read Double from the terminal
  def readDouble(prompt: String): Double = {
    val input = System.console().readLine(prompt)
    Try(input.toDouble).getOrElse(0.0)
  }

  // Define the chain for creating a user
  val createFirstUsers: ChainBuilder = group("Create User") {
    feed(userFeeder)
      .exec { session =>
        val uniqueId = java.util.UUID.randomUUID().toString
        session.set("uniqueId", uniqueId)
      }
      .exec(http("Post 1 User Only Request")
        .post("users")
        .header("Content-Type", "application/json")
        .body(StringBody("{\n    \"name\": \"${name}\",\n    \"job\": \"${job}\",\n    \"id\": \"${uniqueId}\"\n}")).asJson
        .check(status.is(created))
      )
      .pause(pauseDuration)
  }

  // Define the chain for updating a user
  val updateFirstUser: ChainBuilder = group("Update user") {
    feed(userFeeder)
      .exec(http("PUT User Request")
        .put("users/2")
        .header("Content-Type", "application/json")
        .body(StringBody("{\n    \"name\": \"${name}\",\n    \"job\": \"${job}\"\n}")).asJson
        .check(status.is(ok)))
      .pause(pauseDuration)
  }

  // Define the chain for getting the first user
  val getFirstUser: ChainBuilder = group("Get User") {
    exec(http("GET 1 User Only Request")
      .get("users/2")
      .check(status.is(ok)))
      .pause(pauseDuration)
  }

  // Define the chain for deleting the first user
  val deleteFirstUser: ChainBuilder = group("Delete User") {
    exec(http("DELETE User Request")
      .delete("users/2")
      .check(status.is(noContent)))
      .pause(pauseDuration)
  }

  // Define individual chain builders for each simulation step
  val firstSimulation: ChainBuilder = createFirstUsers
  val secondSimulation: ChainBuilder = updateFirstUser
  val thirdSimulation: ChainBuilder = getFirstUser
  val fourthSimulation: ChainBuilder = deleteFirstUser

  // Define the scenario by chaining the individual simulation steps
  val scenarioBuilder: ScenarioBuilder = scenario("Load_Test_Scenario")
    .exec(firstSimulation)
    .exec(secondSimulation)
    .exec(thirdSimulation)
    .exec(fourthSimulation)

  // Set up the simulation with the desired injection profiles and protocols
  setUp(scenarioBuilder.inject(
    nothingFor(nothingForDuration.seconds),
    atOnceUsers(atOnceUsersValue),
    constantUsersPerSec(constUsersPerSecond).during(constDuration.seconds),
    rampUsersPerSec(1).to(rampUsersPerSecond).during(rampDuration.seconds)
  ))
    .protocols(httpProtocol)
    .assertions(
      global.responseTime.mean.lt(globalResponseTime),
      global.successfulRequests.percent.gte(globalSuccessfulRequestsPercent)
    )
    .maxDuration(maxSimulationDuration.minute)
}

