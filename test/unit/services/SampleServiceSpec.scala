package unit.services

import akka.stream.Materializer
import akka.util.Timeout
import org.scalatest.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import services.SampleService

import scala.concurrent.duration.DurationInt

class SampleServiceSpec extends PlaySpec with MockitoSugar with GuiceOneAppPerSuite {

  implicit lazy val materializer: Materializer = app.materializer
  implicit val timeout: Timeout = Timeout(5.seconds)

  val expectedGoodResult = """[ {
                     |  "score" : 13214012,
                     |  "id" : "085a11e1b82b441184f4a193a3c9a13c"
                     |}, {
                     |  "score" : 11446512,
                     |  "id" : "84a0ccfec7d1475b8bfcae1945aea8f0"
                     |}, {
                     |  "score" : 11269569,
                     |  "id" : "7ec85fe3aa3c4dd599e23111e7abf5c1"
                     |}, {
                     |  "score" : 11027069,
                     |  "id" : "f812d487de244023a6a713e496a8427d"
                     |}, {
                     |  "score" : 10622876,
                     |  "id" : "3c867674494e4a7aac9247a9d9a2179c"
                     |} ]""".stripMargin

  "SampleService" must {
    "return true from doTheThing" in {

      val goodResult = SampleService.getHighestScores("conf/highest_scores_clean.data")
      goodResult shouldBe expectedGoodResult

      val badResult = SampleService.getHighestScores("conf/highest_scores_dirty.data")
      badResult shouldBe "invalid json format No JSON object could be decoded"
    }
  }
}
