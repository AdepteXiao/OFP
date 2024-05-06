package ru.ad.ofp_4


object Main {
  def main(args: Array[String]): Unit = {
    val packageName = this.getClass.getPackage.getName
    println(packageName)
    val test_1 = "Shift h e capslock l l shift o".split("\\s+").toList
    val test_2 = "ShiFt    h e capSlOck l    l shift O".split("\\s+").toList
    val test_3 = "shift S r i  capslock Jayawa shift r denepura capslock shift K otte".split("\\s+").toList
    val lst = List(test_1, test_2, test_3)
    val conv = new StringConverter
    for (t <- lst) {
      conv.getAlNum(t)
      conv.getString(t)
      conv.isCapsLocked(t)
    }
    StringConverter.writeToCSV("logs.csv", conv.buffer.map { case (inpStr, func, resStr) =>
      Log(inpStr, func, resStr)}.toList
    )
    StringConverter.readFromCSV("logs.csv").foreach(println)

    }
//    for (t <- lst) {
//      println("----------------------------")
//      println(conv.getAlNum(t))
//      println(conv.getRaw(t))
//      println(conv.getString(t))
//      conv.isCapsLocked(t)
//      println("----------------------------")
//    }
}