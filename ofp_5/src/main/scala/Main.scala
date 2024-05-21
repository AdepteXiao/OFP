import tree.AVLTree
import utils.Utils

object Main {
  def main(args: Array[String]): Unit = {
    var map = Utils.createListOfMaps("data/json3.json")
    val tree = Utils.makeTreeFromJson("name", map)
    println(tree.root)
    println(tree.search("Aldsworth"))
  }
}