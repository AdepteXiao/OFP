package tree

import scala.annotation.tailrec

class AVLTree {
  var root: Node = _

  def findHeight(node: Node): Int = if (node != null) node.height else 0

  def fixHeight(node: Node): Unit = {
    val hl = findHeight(node.left)
    val hr = findHeight(node.right)
    node.height = (hl max hr) + 1
  }

  def rotateRight(p: Node): Node = {
    val q = p.left
    p.left = q.right
    q.right = p
    fixHeight(p)
    fixHeight(q)
    q
  }

  def rotateLeft(p: Node): Node = {
    val q = p.right
    p.right = q.left
    q.left = p
    fixHeight(p)
    fixHeight(q)
    q
  }

  def balanceFactor(node: Node): Int = findHeight(node.right) - findHeight(node.left)

  def balance(node: Node): Node = {
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
    if (node == null) return new Node(key, data)
    if (data(key).toString.compareTo(node.keyVal) < 0)
      node.left = insert(node.left, key, data)
    else
      node.right = insert(node.right, key, data)
    balance(node)
  }

  def search(keyVal: String): Node = {
    @tailrec
    def searchFrom(node: Node): Node = {
      println(node.toString)
      if (node == null || node.keyVal == keyVal) {
        node
      } else if (node.keyVal.compareTo(keyVal) > 0) {
        searchFrom(node.left)
      } else {
        searchFrom(node.right)
      }
    }
    searchFrom(this.root)
  }


}
