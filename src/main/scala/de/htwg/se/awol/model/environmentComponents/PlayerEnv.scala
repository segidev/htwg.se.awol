package de.htwg.se.awol.model.environmentComponents

object PlayerEnv {
  object Rank extends Enumeration {
    val Mob, King, Viceroy, Viceasshole, Asshole = Value
  }

  object BotNames extends Enumeration {
    val Player_1 = Value("YOU")
    val Player_2 = Value("Jan")
    val Player_3 = Value("Ralph")
    val Player_4 = Value("Marko")
    val Player_5 = Value("Friedrich")
    val Player_6 = Value("Jürgen")
    val Player_7 = Value("Günther")
    val Player_8 = Value("Homer")
  }
}