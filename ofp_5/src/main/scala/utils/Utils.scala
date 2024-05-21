package utils

import play.api.libs.json._
import tree.{AVLTree, Node}

import java.io.NotSerializableException
import scala.io.Source

object Utils {
def createListOfMaps(jsonFilePath: String): List[Map[String, String]] = {
  val source = Source.fromFile(jsonFilePath)
  val jsonString = try {
    source.mkString
  } finally {
    source.close()
  }

  val jsonArr = Json.parse(jsonString).as[JsArray]

  jsonArr.value.map(jsValue => {
    jsValue.as[JsObject].value.toMap.map {
      case (key, value: JsString) => (key, value.as[String])
      case (key, value) => (key, value.toString())
    }
  }).toList
}

  def searchInMap(map: List[Map[String, String]], key: String, keyVal: String): ( Int, Map[String, String]) = {
    var counter = 0
    map.foreach { dict =>
      if (dict.contains(key)) {
        counter += 1
        if (dict(key) == keyVal) {
          return (counter, dict)
        }
      }
    }
    (counter, null)
  }

}
