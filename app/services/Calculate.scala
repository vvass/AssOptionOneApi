package services


import model.Alerts
import play.api.libs.functional.syntax._
import play.api.libs.json._

class Calculate {
  import model.{Alert,AlertsRepository}
  
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
    
//    val names = orderedListAlerts.map(
//      alert => alert.name
//    ).distinct map ( x =>
//      Sums(
//        x,
//        AlertRespository.addUpValues(orderedListAlerts,x),
//        AlertRespository.isOverThreshold()
//      )
//    )
    
    val tmp = Alerts(
        AlertsRepository.getStartDate(orderedListAlerts),
        AlertsRepository.getEndDate(orderedListAlerts),
        AlertsRepository.getListOfAlerts(orderedListAlerts)
      )
    
    println(tmp)
//    implicit val sums = Json.format[Alerts]
//    val sumsList = tmp
//
//    println(Json.toJson(sumsList))

    
    
  
    
  }
}
