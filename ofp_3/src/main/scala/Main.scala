import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.collection.mutable.ArrayBuffer

case class StringConverter(inp: List[String]){
  private val len = inp.size
  private val str = makeString(0, cs = false, "", sh = false)

  @tailrec
  private def makeString(i: Int, cs: Boolean, prgr: String, sh: Boolean): String = {
    val ch = inp(i).toUpperCase()
    if (i == len - 1){
      if (ch != "CAPSLOCK" && ch != "SHIFT") {
        return prgr + getSymbol(ch, cs, sh)
      }
      return prgr
    }
    if (ch == "CAPSLOCK") {
      makeString(i + 1, !cs, prgr, sh = false)
    }
    else if (ch == "SHIFT") {
      makeString(i + 1, cs, prgr, sh = true)
    }
    else {
      makeString(i + 1, cs, prgr + getSymbol(ch, cs, sh), sh = false)
    }
  }

  private def getSymbol(sym: String, cs: Boolean, sh: Boolean): String = {
    val flag = if (sh) !cs else cs
    if (flag) {
      return sym.toUpperCase()
    }
    sym.toLowerCase()
  }

  def getString: String = {
    str
  }

  def getAlNum: ArrayBuffer[Char]  = {
    val symbols = ArrayBuffer[Char]()
    for (let <- str) {
      symbols.addOne(let)
    }
    symbols
  }

  def getRaw: ArrayBuffer[String]  = {
    val symbols = ArrayBuffer[String]()
    for (let <- inp) {
      if (let.toUpperCase != "CAPSLOCK" && let.toUpperCase != "SHIFT"){
        symbols.addOne(let)
      }
    }
    symbols
  }

  def isCapsLocked: Boolean = {
    val cs = false
    def Cl(cs: Boolean, i: Int): Boolean = {
      val ch = inp(i).toUpperCase()
      if (i != len - 1) {
        Cl(if (ch == "CAPSLOCKED") !cs else cs, i + 1)
      }
      cs
    }
    val res = Cl(cs, 0)
    println(res)
    res
  }
}


object Main {
  def main(args: Array[String]): Unit = {
//    val input = readLine().split("\\s+").toList
    val test_1 = "Shift h e capslock l l shift o".split("\\s+").toList
    val test_2 = "ShiFt    h e capSlOck l    l shift O".split("\\s+").toList
    val test_3 = "shift S r i  capslock Jayawa shift r denepura capslock shift K otte".split("\\s+").toList
    val lst = List(test_1, test_2, test_3)
    for (t <- lst) {
      val conv = StringConverter(t)
      println("----------------------------")
      println(conv.getAlNum)
      println(conv.getRaw)
      println(conv.getString)
      conv.isCapsLocked
      println("----------------------------")

    }

  }
}