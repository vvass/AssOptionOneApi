package model

import java.text.SimpleDateFormat

import play.api.libs.json.{JsValue, Json}

case class Alerts(start: String, end: String, alerts: JsValue)

object AlertsRepository {
  
  implicit
  
  def getStartDate(ol:List[Alert]) = {
    formatUTC(ol(0).date)
  }
  
  def getEndDate(ol:List[Alert]) = {
    formatUTC(ol.last.date)
  }
  
  def getListOfAlerts(ol:List[Alert]) = {
    
    val rangeOfDates: List[Alert] = ol.filter( l =>
      l.date <= 1477923010745L && l.date >= (1477923010745L - 100086400)
    ).sortWith(_.name < _.name)
    
    val sums = rangeOfDates.map(a=>a.name).distinct map(x=>
      ThresholdStatus(
        x,
        addup(rangeOfDates,x),
        findTimestamp(rangeOfDates,x).toString
      )
    )
  
    implicit val alertsWrites = Json.format[ThresholdStatus]
    Json.toJson(sums)
  }
  
  def formatUTC (date: Long): String = {
    val simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    return simpleDateFormat.format(date)
  }
  
  def addup(ol:List[Alert],str:String) = {
    var sum = 0
    ol.filter(a => a.name == str).map( b =>
      if(sum < 2000)
        sum += b.value
    )
    sum
  }
  
  def findTimestamp(ol:List[Alert],str:String) = {
    var sum = 0
    var time = 0L
    ol.filter(a => a.name == str).map( b =>
      if(sum < 2000){
        time = b.date
        sum += b.value
      }
    )
    formatUTC(time)
  }
}