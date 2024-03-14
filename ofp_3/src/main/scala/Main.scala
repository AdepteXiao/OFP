import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.collection.mutable.ArrayBuffer


object Main {
  def main(args: Array[String]): Unit = {
//    val input = readLine().split("\\s+").toList
    val test_1 = "Shift h e capslock l l shift o".split("\\s+").toList
    val test_2 = "ShiFt    h e capSlOck l    l shift O".split("\\s+").toList
    val test_3 = "shift S r i  capslock Jayawa shift r denepura capslock shift K otte".split("\\s+").toList
    val lst = List(test_1, test_2, test_3)
    val conv = new StringConverter
    for (t <- lst) {
      println("----------------------------")
      println(conv.getAlNum(t))
      println(conv.getRaw(t))
      println(conv.getString(t))
      conv.isCapsLocked(t)
      println("----------------------------")
    }
  }
}