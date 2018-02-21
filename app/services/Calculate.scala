package services

import play.api.libs.functional.syntax._
import play.api.libs.json._

class Calculate {
  import model.{Alert,Alerts,AlertsRepository}
  
  def convert (response: String) = {
  
    implicit val alertReads: Reads[Alert] = (
      (__ \ "name").read[String] and
        (__ \ "value").read[Int] and
        (__ \ "date").read[Long]
      ) ( Alert.apply _ )
  
    val json: JsValue = Json.parse(response)
  
    val validatedAlerts = json.validate[List[Alert]]
  
    val listAlerts = validatedAlerts match {
      case JsSuccess(list, _) => list
      case e: JsError => {
        println("Error " + e)
        List()
      }
    }
    
    val orderedListAlerts = listAlerts.sortWith(_.date < _.date)
    
    val alerts = Alerts(
        AlertsRepository.getStartDate(orderedListAlerts),
        AlertsRepository.getEndDate(orderedListAlerts),
        AlertsRepository.getListOfAlerts(orderedListAlerts)
      )
    
    implicit val alertsWrites = Json.format[Alerts]
    Json.toJson(alerts)
    
  }
}
