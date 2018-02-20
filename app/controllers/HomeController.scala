package controllers

import javax.inject.Inject

import scala.concurrent.Future
import play.api.mvc._
import play.api.libs.ws._
import scala.concurrent._
import ExecutionContext.Implicits.global
import play.api.libs.json._

case class Alert(name: String, value: Int, date: String)

class HomeController @Inject() (ws: WSClient) extends InjectedController {
  
  def index = Action.async {
  
    ws.url("https://sample-api.pascalmetrics.com/api/events")
      .addHttpHeaders("Accept" -> "application/json")
      .addQueryStringParameters("api-key" -> "nu11p0int3r!")
      .get().map { response =>
      
        
      
        Ok(views.html.index(response.body))
      }
    
  }
  
  
  
  
}