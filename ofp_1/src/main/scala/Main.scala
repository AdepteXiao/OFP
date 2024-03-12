import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.math.Pi


case class Calcs(xSt: BigDecimal,
                 xFin: BigDecimal,
                 dx: BigDecimal,
                 e: BigDecimal) {

  private def taylorSeriesArctan(x: BigDecimal, precision: BigDecimal): (BigDecimal, Int) = {

    def taylorCurr(n: Int): BigDecimal =
      BigDecimal(math.pow(-1, n + 1)) / (BigDecimal(2 * n + 1) * x.pow(2 * n + 1))

    @tailrec
    def taylorSum(n: Int, sum: BigDecimal): (BigDecimal, Int) = {
      val curr = taylorCurr(n)
      if (curr.abs < precision) (sum, n)
      else taylorSum(n + 1, sum + curr)
    }

    val (sum, n) = taylorSum(0, BigDecimal(0))
    (Pi / 2 + sum, n)
  }

  def printTaylorSeriesTable(): Unit = {
    println("Значения функции на заданном интервале\n" +
            "\tx\t\t|\tf(x)\t\t|\tTaylor(x)\t|\tTI")
    println("-------------------------------------------------")
    val x = xSt
    printTaylorSerie(x)
  }

  @tailrec
  private def printTaylorSerie(x: BigDecimal): Unit = {
    val fx = math.atan(x.toDouble)
    val (taylorX, n) = taylorSeriesArctan(x, e)
    println("%.4f \t|\t %.4f \t|\t %.4f \t|\t %d".format(x, fx, taylorX, n))
    if (if (xSt < xFin) x < xFin else x > xFin) {
      printTaylorSerie(x + dx)
    }
  }
}


object Main {
  def main(args: Array[String]): Unit = {
    println("Введите числа через пробел:")
    val input = readLine().replace(",", ".").split("\\s+")
    if (input.length == 4) {
      try {
        val numbers = input.map(BigDecimal(_))
        val Array(xSt, xFin, dx, e) = numbers
//        require(xFin > xSt, "Конечное значение должно быть больше начального значения")
        require(xFin > 1 && xSt > 1, "Х должны быть > 1")
        require((xFin - xSt).abs % dx.abs == 0 && ((xFin - xSt > 0 && dx > 0) || (xFin - xSt < 0 && dx < 0)), "С заданным шагом невозможно достичь наибольшее значение x ")
//        require(dx > 0, "Шаг должен быть положительным числом")
        require(e > 0, "Ошибка должна быть положительным числом")
        val calcs = Calcs(xSt, xFin, dx, e)
        calcs.printTaylorSeriesTable()
      } catch {
        case e: IllegalArgumentException =>
          println(s"Error: ${e.getMessage}")
      }
    } else {
      println("Количество чисел должно равняться четырем!")
    }
  }
}