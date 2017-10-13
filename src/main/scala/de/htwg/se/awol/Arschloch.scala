package de.htwg.se.awol

import de.htwg.se.awol.model.Player

object Arschloch {
  def main(args: Array[String]): Unit = {
    val student = Player("Your Name")
    println("Hello, " + student.name)
  }
}
