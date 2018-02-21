package services

import java.time.{Instant, ZoneOffset}

import model.Alerts
import play.api.libs.functional.syntax._
import play.api.libs.json._
import java.text.{DateFormat, SimpleDateFormat}
import java.util.Date

class ConvertToUTC {
  import model.Alerts
  
  def convert (response: String) = {
  
    implicit val alertReads: Reads[Alerts] = (
      (__ \ "name").read[String] and
        (__ \ "value").read[Int] and
        (__ \ "date").read[Long]
      ) ( Alerts.apply _ )
  
    val json: JsValue = Json.parse(response)
  
    val validatedAlerts = json.validate[List[Alerts]]
  
    val listAlerts = validatedAlerts match {
      case JsSuccess(list, _) => list
      case e: JsError => {
        println("Error " + e)
        List()
      }
    }
  
    listAlerts.map( alert =>
      println(Instant.ofEpochMilli(alert.date).atOffset(ZoneOffset.UTC).toString())
    )
  }
}
