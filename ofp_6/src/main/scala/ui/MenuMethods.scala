package ui

import scala.annotation.tailrec
import scala.io.{Source, StdIn}
import rsa.RSA
import rsa.Utils

import java.io.PrintWriter

object MenuMethods extends Menu {

  var rsa: Option[RSA] = None

  private var isRun = true

  def run(): Unit = {
    while (isRun) {

      println("1. Create from P Q")
      println("2. Create from existing keys")
      if (rsa.isDefined) {
        println("3. Encrypt message")
        println("4. Decrypt message")
        println("5. Encrypt to file")
        println("6. Decrypt to file")
      }
      println("0. Exit")

      var choice: Int = -1

      try {
        choice = scala.io.StdIn.readInt()
      } catch {
        case e: NumberFormatException =>
      }

      choice match {
        case MAKE_FROM_PQ => makeRsaFromPqHandler()
        case MAKE_FROM_EXISTS => makeRsaFromExistsHandler()
        case ENCRYPT_CONSOLE if rsa.isDefined => encryptFromConsoleHandler()
        case DECRYPT_CONSOLE if rsa.isDefined => decryptFromConsoleHandler()
        case ENCRYPT_FILE if rsa.isDefined => encryptToFileHandler()
        case DECRYPT_FILE if rsa.isDefined => decryptFromFileHandler()
        case EXIT => isRun = false
        case _ => println("Некорректный ввод")
      }

    }
  }
  def makeRsaFromPqHandler(): Unit = {
    val (p, q) = pickPQ()
    val rsa = RSA.makeFromPQ(p, q)
    println("Success")
    this.rsa = Option.apply(rsa)
  }

  def makeRsaFromExistsHandler(): Unit = {
    val n = Utils.readBigInt("Enter N:")
    val e = Utils.readBigInt("Enter E:")
    val d = Utils.readBigInt("Enter D:")
    val rsa = new RSA(n, e, d)
    println("Success")
    this.rsa = Option.apply(rsa)
  }

  def encryptFromConsoleHandler(): Unit = {
    println("Enter message:")
    val message = StdIn.readLine()
    println(s"Encrypted message:\n ${rsa.get.encrypt(message)}")
  }

  def decryptFromConsoleHandler(): Unit = {
    println("Enter message:")
    val message = StdIn.readLine()
    try {
      println(s"Decrypted message:\n ${rsa.get.decrypt(message)}")
    } catch {
    case e: NumberFormatException => System.err.println(s"Incorrect input ${e.getMessage}")
  }
  }

  def encryptToFileHandler(): Unit = {
    println("Enter file name:")
    val filePath = StdIn.readLine()
    println("Enter message:")
    val message = StdIn.readLine()
    val enc = rsa.get.encrypt(message)
    val writer = new PrintWriter(filePath)
    try {
      writer.write(enc)
      println("Successfully saved")
    } catch {
      case e: Exception => System.err.println(e.getMessage)
    } finally {
      writer.close()
    }
  }

  def decryptFromFileHandler(): Unit = {
    println("Enter file name:")
    val filePath = StdIn.readLine()
    val source = Source.fromFile(filePath)
    try {
      println(s"Decrypted message:\n ${rsa.get.decrypt(source.mkString)}")
    } catch {
      case e: Exception => System.err.println(e.getMessage)
    } finally {
      source.close()
    }
  }

  private def pickPQ(): (BigInt, BigInt) = {
    val primes = pickPrimes()

    primes.zipWithIndex.foreach {
      case (prime, index) =>
        println(s"$index. $prime")
    }

    val p = primes(chooseIndex("Choose index for p", primes.size))
    val q = primes(chooseIndex("Choose index for q", primes.size))
    println(s"p: $p\nq: $q")
    (p, q)
  }

  @tailrec
  private def pickPrimes(): List[BigInt] = {
    val leftBound = Utils.readBigInt("Enter left bound")
    val rightBound = Utils.readBigInt("Enter right bound")
    if (rightBound < leftBound || leftBound < 1 || rightBound < 1) {
      System.err.println("Incorrect input")
      pickPrimes()
    } else {
      val primes = Utils.primes(leftBound, rightBound)
      if (primes.isEmpty) {
        println("No numbers found")
        pickPrimes()
      } else {
        primes
      }
    }
  }

  @tailrec
  private def chooseIndex(prompt: String, listSize: Int): Int = {
    println(prompt)
    val index = StdIn.readInt()
    if (index < 0 || index >= listSize) {
      System.err.println("Incorrect index")
      chooseIndex(prompt, listSize)
    } else {
      index
    }
  }



}
