package controllers

import javax.inject.Inject

import akka.http.scaladsl.model.DateTime

import play.api.mvc._
import play.api.libs.ws._

import scala.concurrent._
import ExecutionContext.Implicits.global
import play.api.libs.functional.syntax._
import play.api.libs.json._



class HomeController @Inject() (ws: WSClient) extends InjectedController {
  
  def index = Action.async {
  
    
    
    ws.url("https://sample-api.pascalmetrics.com/api/events")
      .addHttpHeaders("Accept" -> "application/json")
      .addQueryStringParameters("api-key" -> "nu11p0int3r!")
      .get().map { response =>
  
        case class Alert(names: String, value: Int, date: String)
    
        implicit val alertReads: Reads[Alert] = (
          (__ \ "name").read[String] and
            (__ \ "value").read[Int] and
            (__ \ "date").read[String]
          ) ( Alert.apply _ )
      
      
        {response.json \ ""}.validate[Alert] match {
          case JsSuccess(a, _) => println(a)
          case JsError(errors) =>
            println(errors)
        }
      
        val jsonContent = response.json
      
        Ok(views.html.index(jsonContent.toString))
        
        
      }
    
  }
  
  
  
  
}