package controllers

import javax.inject.Inject

import play.api.mvc._
import play.api.libs.ws._

import scala.concurrent._
import ExecutionContext.Implicits.global

import services.Calculate

class HomeController @Inject() (ws: WSClient) extends InjectedController {
  
  def alerts = Action.async {
  
    ws.url("https://sample-api.pascalmetrics.com/api/events")
      .addHttpHeaders("Accept" -> "application/json")
      .addQueryStringParameters("api-key" -> "nu11p0int3r!")
      .get().map { response =>
      
        val tmp = new Calculate().convert(response.body.toString)
        Ok(views.html.index("Ok"))
        
        
      }
    
  }
  
}