package controllers

import javax.inject.Inject

import play.api.mvc._
import play.api.libs.ws._

import scala.concurrent._
import ExecutionContext.Implicits.global
import play.api.libs.functional.syntax._
import play.api.libs.json._
import java.time.Instant
import java.time.ZoneOffset
import java.text.{DateFormat, SimpleDateFormat}
import java.util.Date

import services.ConvertToUTC


class HomeController @Inject() (ws: WSClient) extends InjectedController {
  
  def index = Action.async {
  
    ws.url("https://sample-api.pascalmetrics.com/api/events")
      .addHttpHeaders("Accept" -> "application/json")
      .addQueryStringParameters("api-key" -> "nu11p0int3r!")
      .get().map { response =>
      
        val tmp = new ConvertToUTC().convert(response.body.toString)
        Ok(views.html.index("Ok"))
        
        
      }
    
  }
  
}