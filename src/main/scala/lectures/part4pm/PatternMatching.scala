package lectures.part4pm

import scala.util.Random

object PatternMatching extends App {

  // switch on steroids
  val random = new Random
  val x = random.nextInt(10)

  val description = x match
    case 1 => "the ONE"
    case 2 => "double or nothing"
    case 3 => "third time is the charm"
    case _ => "something else"

  println(x)
  println(description)


  /*
    1. Decompose values
   */
  case class Person(name: String, age: Int)

  val bob = Person("Bob", 20)

  val greeting = bob match
    case Person(n, a) if (a < 21) => s"Hi, my name is $n and I can't drink in the us"
    case Person(n, a) => s"Hi, my name is $n and I'm $a years old"
    case _ => "Hello"

  println(greeting)

  /*
   - cases are matched in order
   - what if no cases match? MatchError
   - type of the PM expression? unified type of all the types in all the cases
   - PM works really well with case classes
   */

  // PM on sealed hierarchies
  sealed class Animal

  case class Dog(breed: String) extends Animal

  case class Parrot(greeting: String) extends Animal

  val animal: Animal = Dog("Terra Nova")
  animal match {
    case Dog(someBreed) => println(s"Matched Dog of the $someBreed breed")
  }

  // match everything
  val isEven = x match
    case n if n % 2 == 0 => true
    case _ => false
  //      overkill

  val isEvenCond = if (x % 2 == 0) true else false // also bad
  val isEvenNormal = x % 2 == 0

  /*
    Exercise
    simple function uses PM
      takes an Expr => human readable form
   */
  trait Expr

  case class Number(n: Int) extends Expr

  case class Sum(e1: Expr, e2: Expr) extends Expr

  case class Prod(e1: Expr, e2: Expr) extends Expr

  def show(e: Expr): String = e match
    case Number(n) => s"$n"
    case Sum(e1, e2) => show(e1) + " + " + show(e2)
    case Prod(e1, e2) => {
      def maybeShowParentheses(exp: Expr) = exp match
        case Prod(_, _) => show(exp)
        case Number(_) => show(exp)
        case _ => "(" + show(exp) + ")"

      maybeShowParentheses(e1) + " * " + maybeShowParentheses(e2)
    }

  println()
  println(show(Sum(Number(2), Number(3))))
  println(show(Sum(Sum(Number(3), Number(2)), Number(3))))
  println(show(Prod(Sum(Number(2), Number(1)), Number(3))))
  println(show(Sum(Prod(Number(2), Number(1)), Number(3))))
}
