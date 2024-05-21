import tree.AVLTree
import utils.Utils

object Main {
  def main(args: Array[String]): Unit = {
    var map = Utils.createListOfMaps("data/json3.json")
    var tree = AVLTree.makeTreeFromJson("name", map)
    AVLTree.serialize(tree, "data/tree.csv")
    tree = AVLTree.deserialize("data/tree.csv")
    println(tree.search("Aachen"))
    println(Utils.searchInMap(map, "name", "Aachen"))
  }
}