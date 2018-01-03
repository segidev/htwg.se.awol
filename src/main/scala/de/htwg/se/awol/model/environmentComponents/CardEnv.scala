package de.htwg.se.awol.model.environmentComponents

object CardEnv {
  object Colors extends Enumeration {
    val Clubs, Spades, Hearts, Diamonds = Value
  }

  object Values extends Enumeration{
    val Two: Values.Value = Value(2)
    val Three: Values.Value = Value(3)
    val Four: Values.Value = Value(4)
    val Five: Values.Value = Value(5)
    val Six: Values.Value = Value(6)
    val Seven: Values.Value = Value(7)
    val Eight: Values.Value = Value(8)
    val Nine: Values.Value = Value(9)
    val Ten: Values.Value = Value(10)
    val Jack: Values.Value = Value(11)
    val Queen: Values.Value = Value(12)
    val King: Values.Value = Value(13)
    val Ace: Values.Value = Value(14)
  }
}