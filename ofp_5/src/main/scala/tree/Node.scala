package tree
import play.api.libs.json.JsValue

class Node(var key: String,
           var data: Map[String, Any],
           var height: Int = 1,
           var left: Node = null,
           var right: Node = null) extends Serializable{
  val keyVal: String = data(key) match {
    case str: String => str
    case _ => data(key).toString
  }

  override def toString: String = {
    s"Node(key=$keyVal, data=$data)"
  }
}

