package de.htwg.se.awol.model.environmentComponents

object CardEnvironment {
  object Colors extends Enumeration {
    val Clubs, Spades, Hearts, Diamonds = Value
  }

  object Descriptions extends Enumeration {
    val Jack, Queen, King, Ace = Value
  }
}