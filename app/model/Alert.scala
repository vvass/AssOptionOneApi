package model

import java.text.{DateFormat, SimpleDateFormat}
import java.time.{Instant, LocalDate, ZoneOffset}

import scala.collection.mutable.ListBuffer

case class Alert(name: String, value: Int, date: Long)

object AlertsRespository {
  
  var sum = 0
  var list = new ListBuffer[Any]()
  
  
  def findName(a:List[Alert],str:String):Boolean = a.find(x => x.name == str) match {
    case Some(_) => true
    case _ => false
  }
  
  def addUpValues(a:List[Alert],str:String) = {
    sum = 0
    a.filter(l => l.name == str).map(r => sum += isIn24HourPeriodValue(r))
    sum
  }
  
//  def getListOfDates(a:List[Alerts],str: String) = {
//    list = new ListBuffer[Any]()
//
//    a.filter(l => l.name == str).map(r => list += periodReachThreshold(r))
//    println(list)
//
//    List()
//
//  }
  
  def isOverThreshold():Boolean = {
    if(sum > 2000) true else false
  }
  
  def isIn24HourPeriodValue(r:Alert) = {
//    if (((System.currentTimeMillis) - r.date) < 86400)  r.value else 0
    //2016/10/31 10:10:10
    if (((1477923010745L) - r.date) < 86400)  r.value else 0
  }
  
  def periodReachThreshold(r:Alert) = {
//    if (((System.currentTimeMillis) - r.date) < 86400)  true else false
    //2016/10/31 10:10:10
    if (((1477923010745L) - r.date) < 86400)  r.date else None
  }
  
  def formatUTC (date: Long): String = {
    val simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    return simpleDateFormat.format(date)
  }
  
}