package unit.controllers

import akka.stream.Materializer
import akka.util.Timeout
import controllers.HealthController
import org.scalatest.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status.{NO_CONTENT, OK}
import play.api.test.Helpers.{GET, status}
import play.api.test._

import scala.concurrent.duration.DurationInt

class TemplateSpec extends PlaySpec with MockitoSugar with GuiceOneAppPerSuite {

  implicit lazy val materializer: Materializer = app.materializer
  implicit val timeout: Timeout = Timeout(5.seconds)

  "HealthController" must {
    "return an error when the body is missing" in {

      val request = FakeRequest(GET, "/v1/health")
      val healthController = new HealthController(Helpers.stubControllerComponents())

      val result = healthController.index()(request)
      status(result) shouldBe OK
    }
  }
}
