package controllers

import javax.inject.Inject

import akka.http.scaladsl.model.DateTime

import play.api.mvc._
import play.api.libs.ws._

import scala.concurrent._
import ExecutionContext.Implicits.global
import play.api.libs.functional.syntax._
import play.api.libs.json._


case class Alerts(name: String, value: Int, date: Long)

class HomeController @Inject() (ws: WSClient) extends InjectedController {
  
  def index = Action.async {
  
    ws.url("https://sample-api.pascalmetrics.com/api/events")
      .addHttpHeaders("Accept" -> "application/json")
      .addQueryStringParameters("api-key" -> "nu11p0int3r!")
      .get().map { response =>
      
        implicit val alertReads: Reads[Alerts] = (
            (__ \ "name").read[String] and
            (__ \ "value").read[Int] and
            (__ \ "date").read[Long]
          ) ( Alerts.apply _ )
      
        val json: JsValue = Json.parse(response.body.toString)
      
        val validatedAlerts = json.validate[List[Alerts]]
        
        val listAlerts = validatedAlerts match {
          case JsSuccess(list, _) => list
          case e: JsError => {
            println("Error " + e)
            List()
          }
        }
      
        listAlerts.map( alert =>
          println(alert.name)
        )
        
      
        Ok(views.html.index(listAlerts.toString))
        
        
      }
    
  }
  
  
  
  
}