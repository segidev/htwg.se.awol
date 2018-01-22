package de.htwg.se.awol.model.environmentComponents

object PlayerEnv {
  object Rank extends Enumeration {
    val Mob, King, Viceroy, Viceasshole, Asshole = Value
  }

  object BotNames extends Enumeration {
    val Player_1: BotNames.Value = Value("YOU")
    val Player_2: BotNames.Value = Value("Jan")
    val Player_3: BotNames.Value = Value("Ralph")
    val Player_4: BotNames.Value = Value("Marko")
    val Player_5: BotNames.Value = Value("Friedrich")
    val Player_6: BotNames.Value = Value("Jürgen")
    val Player_7: BotNames.Value = Value("Günther")
    val Player_8: BotNames.Value = Value("Homer")
  }
}