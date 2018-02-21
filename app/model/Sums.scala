package model

case class Sums(name: String, sum: Int, threshold: Boolean )

object SumsRespository {
  def findName(s:List[Sums],str:String):Boolean = s.find(x => x.name == str) match {
      case Some(_) => true
      case _ => false
  }
}