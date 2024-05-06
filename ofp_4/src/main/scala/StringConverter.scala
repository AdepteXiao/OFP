package ru.ad.ofp_4

import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer
import com.github.tototoshi.csv._

class StringConverter {
  val buffer: ArrayBuffer[(String, Func, String)] = ArrayBuffer().empty

  private def addToBuffer(str: String, func: Func, result: String): Unit = {
    buffer += ((str, func, result))
  }

  private def getSymbol(sym: String, cs: Boolean, sh: Boolean): String = {
    val flag = if (sh) !cs else cs
    if (flag) {
      return sym.toUpperCase()
    }
    sym.toLowerCase()
  }

  /**
   * Переводит последовательность нажатий в строку
   */
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

    val res = makeString(0, cs = false, "", sh = false)
    addToBuffer(inp.mkString(", "), Funcs.GETSTRING, res)
    res
  }

  /**
   * Возвращает из списка нажатий только нажатия алфавитно-цифровых клавиш
   */
  def getAlNum(inp: List[String]): String = {
    val str = getString(inp)
    val symbols = ArrayBuffer[Char]()
    for (let <- str) {
      symbols.addOne(let)
    }
    val res = String.format("[%s]", symbols.toList.mkString(", "))
    addToBuffer(inp.mkString(", "), Funcs.GETALLNUM, res)
    res
  }

  /**
   * Возвращает строку, составленную из нажатых символов без учета информации о Shift и CapsLock
   */
  def getRaw(inp: List[String]): String = {
    val symbols = ArrayBuffer[String]()
    for (let <- inp) {
      if (let.toUpperCase != "CAPSLOCK" && let.toUpperCase != "SHIFT") {
        symbols.addOne(let)
      }
    }
    val res = String.format("[%s]", symbols.toList.mkString(", "))
    addToBuffer(inp.mkString(", "), Funcs.GETRAW, res)
    res
  }

  /**
   * По последовательности нажатий определяет, остался ли CapsLock в активном состоянии
   */
  def isCapsLocked(inp: List[String]): String = {
    val len = inp.size
    val cs = false

    def Cl(cs: Boolean, i: Int): Boolean = {
      val ch = inp(i).toUpperCase()
      if (i != len - 1) {
        Cl(if (ch == "CAPSLOCKED") !cs else cs, i + 1)
      }
      cs
    }

    val res = Cl(cs, 0)
    addToBuffer(inp.mkString(", "), Funcs.ISCAPSLOCKED, res.toString)
    res.toString
  }
}

object StringConverter{
  def writeToCSV(filename: String, logs: List[Log]): Unit = {
    println("Сохранение")
    val writer = CSVWriter.open(filename)
    val headers = Seq("inpStr", "func", "resStr")
    writer.writeRow(headers)

    logs.foreach { log =>
      val row = Seq(
        log.inpStr,
        log.func,
        log.resStr,
      )
      writer.writeRow(row)
    }
    writer.close()
  }

  def readFromCSV(filename: String): List[Log] = {
    println("Загрузка")
    val reader = CSVReader.open(filename)
    val logs = reader.allWithHeaders().map { row =>
      Log(
        row("inpStr"),
        Funcs.withName(row("func")),
        row("resStr"),
      )
    }
    reader.close()
    logs
  }
}

