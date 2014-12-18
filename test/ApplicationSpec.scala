import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  "Application" should {

    "send 404 on a bad request" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }

    "render the index page" in new WithApplication {
      val home = route(FakeRequest(GET, "/")).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "text/plain")
      contentAsString(home) must contain("Shapely")
    }

    "converts rdfs label to schema.org name" in new WithApplication {
      val response = route(
        FakeRequest(POST, "/")
          .withBody("@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> . <http://example.com/ross> rdfs:label \"Ross\" .")
      ).get

      status(response) must equalTo(OK)
      contentAsString(response) must contain("<http://example.com/ross> <http://schema.org/name> \"Ross\" .")
    }
  }
}
