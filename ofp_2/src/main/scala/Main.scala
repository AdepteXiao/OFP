import java.io.File
import scala.io.Source
import scala.io.StdIn.readLine

object Main {
  def main(args: Array[String]): Unit = {
    println("Введите путь к файлу:")
    val filePath = readLine()
    try {
      val file = Source.fromFile(filePath)
      println("Файл считан")
      println("Введите количество слов наименьшей длины, которые следует вывести")
      val num = readLine().toInt
      require(num >= 0, "Количество слов не может быть меньше нуля")
      val words = file.getLines.flatMap(_.split("\\s+")).filter(_.length > 1).toList
      val shortestWords = words.sortBy(word => (word.length, word)).take(num)
      shortestWords.foreach(println)
    } catch {
      case e: NumberFormatException =>
        println("Введеное число не является целым")
      case e: java.io.FileNotFoundException =>
        println("Файл по данному путь не найден")
    }
  }
}
