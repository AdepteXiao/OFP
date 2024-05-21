package utils

import play.api.libs.json._
import tree.{AVLTree, Node}

import java.io.NotSerializableException
import scala.io.Source

object Utils {
  def createListOfMaps(jsonFilePath: String): List[Map[String, JsValue]] = {
    val source = Source.fromFile(jsonFilePath)
    val jsonString = try {
      source.mkString
    } finally {
      source.close()
    }

    val jsonArr = Json.parse(jsonString).as[JsArray]

    jsonArr.value.map(jsValue => jsValue.as[JsObject].value.toMap).toList
  }

  def makeTreeFromJson(key: String, list: List[Map[String, JsValue]]): AVLTree = {
    val tree = new AVLTree
    list.foreach { map =>
      if (map.contains(key)) {
        tree.root = tree.insert(tree.root, key, map)
//        println(map(key))
      }
    }
    tree match {
      case tree: AVLTree => tree
      case _ => throw new NotSerializableException("Невозможно сделать дерево (убедитесь что ключ существует и файл не пустой)")
    }
  }

}
