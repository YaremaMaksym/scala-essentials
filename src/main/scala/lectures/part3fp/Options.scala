package lectures.part3fp

import scala.util.Random

object Options extends App {

  val myFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int] = None

  println(myFirstOption)
  println(noOption)

  // unsafe API
  def unsafeMethod(): String = null
  val result = Option(unsafeMethod())

  // chained methods
  def backupMehod(): String = "A valid result"
  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMehod()))

//  Design unsafe APIs
  def betterUnsafeMethod: Option[String] = None
  def betterBackupMethod: Option[String] = Some("A valid result")
  val betterChainedResult = betterUnsafeMethod.orElse(betterBackupMethod)



  /*
    Functions on Options
   */
  println(myFirstOption.isEmpty)
  println(myFirstOption.get) // Unsafe

  // map, flatMap, filter
  println(myFirstOption.map(_ * 2))
  println(myFirstOption.flatMap(x => Option(x * 10)))
  println(myFirstOption.filter(_ > 10))

}