import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer

class StringConverter {
  private def getSymbol(sym: String, cs: Boolean, sh: Boolean): String = {
    val flag = if (sh) !cs else cs
    if (flag) {
      return sym.toUpperCase()
    }
    sym.toLowerCase()
  }

  def getString(inp: List[String]): String = {
    val len = inp.size

    @tailrec
    def makeString(i: Int, cs: Boolean, prgr: String, sh: Boolean): String = {
      val ch = inp(i).toUpperCase()
      if (i == len - 1) {
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

    makeString(0, cs = false, "", sh = false)
  }

  def getAlNum(inp: List[String]): String = {
    val str = getString(inp)
    val symbols = ArrayBuffer[Char]()
    for (let <- str) {
      symbols.addOne(let)
    }
    String.format("[%s]", symbols.toList.mkString(", "))
  }

  def getRaw(inp: List[String]): String = {
    val symbols = ArrayBuffer[String]()
    for (let <- inp) {
      if (let.toUpperCase != "CAPSLOCK" && let.toUpperCase != "SHIFT") {
        symbols.addOne(let)
      }
    }
    String.format("[%s]", symbols.toList.mkString(", "))
  }

  def isCapsLocked(inp: List[String]): Boolean = {
    val len = inp.size

    @tailrec
    def Cl(cs: Boolean, i: Int): Boolean = {
      val ch = inp(i).toUpperCase()
      if (i != len - 1) {
        if (ch.equals("CAPSLOCK")) {
          Cl(!cs, i + 1)
        } else {
          Cl(cs, i + 1)
        }
      } else {
        cs
      }
    }

    val res = Cl(cs = false, 0)
    res
  }
}
