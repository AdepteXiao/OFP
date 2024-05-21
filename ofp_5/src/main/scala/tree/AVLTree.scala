package tree

import java.io._
import scala.annotation.tailrec

class AVLTree extends Serializable {
  var root: Node = _

  private def findHeight(node: Node): Int = if (node != null) node.height else 0

  private def fixHeight(node: Node): Unit = {
    val hl = findHeight(node.left)
    val hr = findHeight(node.right)
    node.height = (hl max hr) + 1
  }

  private def rotateRight(p: Node): Node = {
    val q = p.left
    p.left = q.right
    q.right = p
    fixHeight(p)
    fixHeight(q)
    q
  }

  private def rotateLeft(p: Node): Node = {
    val q = p.right
    p.right = q.left
    q.left = p
    fixHeight(p)
    fixHeight(q)
    q
  }

  private def balanceFactor(node: Node): Int = findHeight(node.right) - findHeight(node.left)

  private def balance(node: Node): Node = {
    fixHeight(node)
    if (balanceFactor(node) == 2) {
      if (balanceFactor(node.right) < 0)
        node.right = rotateRight(node.right)
      return rotateLeft(node)
    }
    if (balanceFactor(node) == -2) {
      if (balanceFactor(node.left) > 0)
        node.left = rotateLeft(node.left)
      return rotateRight(node)
    }
    node
  }

  def insert(node: Node, key: String, data: Map[String, Any]): Node = {
    if (node == null) {
      return new Node(key, data)
    }
    if (data(key).toString < node.keyVal)
      node.left = insert(node.left, key, data)
    else
      node.right = insert(node.right, key, data)
    balance(node)
  }

  def search(keyVal: String): (Int, Node) = {
    var counter = 0
    @tailrec
    def searchFrom(node: Node): (Int, Node) = {
      counter += 1
//      println(node.toString)
//      println(node.keyVal.equals(keyVal))
      if (node == null || node.keyVal.equals(keyVal)) {
        return (counter, node)
      } else if (node.keyVal > keyVal) {
//        println(node.keyVal)
//        println(keyVal)
        searchFrom(node.left)
      } else {
//        println(node.keyVal, keyVal, node.keyVal < keyVal)
        searchFrom(node.right)
      }
    }

    searchFrom(this.root)
  }


}

object AVLTree {
  def serialize(tree: AVLTree, fileName: String): Unit = {
    val fileOutputStream = new FileOutputStream(fileName)
    val objectOutputStream = new ObjectOutputStream(fileOutputStream)
    objectOutputStream.writeObject(tree)
    objectOutputStream.close()
    fileOutputStream.close()
  }

  def deserialize(fileName: String): AVLTree = {
    val fileInputStream = new FileInputStream(fileName)
    val objectInputStream = new ObjectInputStream(fileInputStream)
    val tree = objectInputStream.readObject().asInstanceOf[AVLTree]
    objectInputStream.close()
    fileInputStream.close()
    tree
  }

  def makeTreeFromJson(key: String, list: List[Map[String, String]]): AVLTree = {
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
