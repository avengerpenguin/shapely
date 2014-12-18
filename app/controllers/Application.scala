package controllers

import play.api._
import play.api.mvc._
import play.api.libs.iteratee._
import de.fuberlin.wiwiss.r2r.Mapper
import de.fuberlin.wiwiss.r2r.NTriplesOutput
import de.fuberlin.wiwiss.r2r.Repository
import scala.concurrent.ExecutionContext.Implicits.global

object Application extends Controller {

  def index = Action {
    Ok("Shapely")
  }

  def transform = Action { request =>
    
		val vocabulary = "@prefix schema: <http://schema.org/>" +
		"(" +
			"schema:name" +
		")";
		
    val enumerator = Enumerator.outputStream { os =>

      val output: NTriplesOutput = new NTriplesOutput(os)

      Mapper.transform(
        new StreamSource(request),
        output,
        Repository.createFileOrUriRepository("mappings.ttl"),
        vocabulary)

      output.close()

    }

    Ok.feed(enumerator >>> Enumerator.eof)

  }

}
