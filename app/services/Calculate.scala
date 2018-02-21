package services


import play.api.libs.functional.syntax._
import play.api.libs.json._

class Calculate {
  import model.{Alerts,AlertsRespository}
  import model.Sums
  
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
    
    val orderedListAlerts = listAlerts.sortWith(_.date < _.date)
    
    val names = orderedListAlerts.map(
      alert => alert.name
    ).distinct map ( x =>
      Sums(
        x,
        AlertsRespository.addUpValues(orderedListAlerts,x),
        AlertsRespository.isOverThreshold()
      )
    )
    
    implicit val sums = Json.format[Sums]
    val sumsList = names
    
    println(Json.toJson(sumsList))

  
    
  }
}
