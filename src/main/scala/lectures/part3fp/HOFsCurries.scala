package lectures.part3fp

object HOFsCurries extends App {

  /**
   * Higher order functions (HOF)
   */
  val superFunction: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) = null

  // map, flatMap, filter in MyList

  // function that applies a function n times over value x
  // nTimes(f, n, x)
  // nTimes(f, 3, x) = f(f(f(x))) = nTimes(f, 2, f(x))
  // ntimes(f, n, x) = f(f(...(x))) = nTimes(f, n-1, f(x))
  def nTimes(f: Int => Int, n: Int, x: Int): Int =
    if (n <= 0) x
    else nTimes(f, n - 1, f(x))

  val plusOne = (x: Int) => x + 1

  println(nTimes(plusOne, 10, 1))


  // ntb(f, n) = x => f(f(f...(x)))
  // val increment10 = ntb(plusOne, 10) = x => plusOne(plusOne....(x))
  // val y = increment10(1)
  def nTimesBetter(f: Int => Int, n: Int): Int => Int =
    if (n <= 0) (x: Int) => x
    else (x: Int) => nTimesBetter(f, n-1)(f(x))

  val plusTen = nTimesBetter(plusOne, 10)

  println(plusTen(1))


  /**
   * Curried functions
   */
  val superAdder: Int => (Int => Int) =
    (x: Int) => (y: Int) => x + y

  val add3 = superAdder(3) // y => 3 + y

  println(add3(10)) // 13
  println(superAdder(3)(10)) // 13

  // functions with multiple parameter lists
  def curriedFormatter(c: String) (x: Double): String = c.format(x)

  val standardFormat: (Double => String) = curriedFormatter("%4.2f")
  val perciseFormat: (Double => String) = curriedFormatter("%10.8f")

  println(standardFormat(Math.PI))
  println(perciseFormat(Math.PI))

  /*
    1. Expand MyList
        - foreach A => Unit

        - sort function ((A, A) => Int) => MyList
          [1,2,3].sort((x, y) => y - x) => [3,2,1]

        - zipWith (list, (A, B) => C) => MyList
          [1,2,3].zipWith([4,5,6], x * y) => [1*4, 2*5, 3*6] = [4,10,18]

        - fold(start)(function) = a value
          [1,2,3].fold(0)(x + y) = 6

    2.  toCurry(f: (Int, Int) => Int) => (Int => Int => Int)
        fromCurry(f: (Int => Int => Int)) => (Int, Int) => Int

    3.  compose(f,g) => x => f(g(x))
        andThen(f,g) => x => g(f(x))
   */

  def toCurry(f: (Int, Int) => Int): (Int => (Int => Int)) =
    x => (y => f(x, y))

  def fromCurry(f: Int => (Int => Int)): (Int, Int) => Int =
    (x, y) => f(x)(y)


  def compose[A,B,C](f: A => B, g: C => A): C => B =
    x => f(g(x))

  def andThen[A,B,C](f: A => B, g: B => C): A => C =
    x => g(f(x))


  def superAdder2: (Int => Int => Int) = toCurry(_ + _)
  def add4 = superAdder2(4)
  println(add4(17))
  println(superAdder(4)(17))

  val add2 = (x: Int) => x + 2
  val times3 = (x: Int) => x * 3
  val composed = compose(add2, times3)
  val ordered = andThen(add2, times3)

  println(composed(4))
  println(ordered(4))
}
