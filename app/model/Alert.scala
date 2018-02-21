package model

import java.text.{DateFormat, SimpleDateFormat}
import java.time.{Instant, LocalDate, ZoneOffset}

case class Alerts(name: String, value: Int, date: Long)

object AlertsRespository {
  
  var sum = 0
  
  def findName(a:List[Alerts],str:String):Boolean = a.find(x => x.name == str) match {
    case Some(_) => true
    case _ => false
  }
  
  def addUpValues(a:List[Alerts],str:String) = {
    sum = 0
    a.filter(l => l.name == str).map(r => sum += isIn24HourPeriodValue(r))
    sum
  }
  
  def isOverThreshold():Boolean = {
    if(sum > 2000) true else false
  }
  
  def isIn24HourPeriodValue(r:Alerts) = {
//    if (((System.currentTimeMillis) - r.date) < 86400)  r.value else 0
    //2016/10/31 10:10:10
    if (((1477923010745L) - r.date) < 86400)  r.value else 0
  }
  
  def formatUTC (date: Long): String = {
    val simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    return simpleDateFormat.format(date)
  }
  
}