package de.htwg.se.awol.model.environmentComponents

object MessageEnv {
  object Warnings extends Enumeration {
    val DividableByFour, MaxAmountOfCards, MissingLanguage, MissingTranslation = Value
  }

  object Questions extends Enumeration {
    val QuitGame = Value
  }

  object Phrases extends Enumeration {
    val WelcomeToTheGame, HandingOutCards, HasWonTheRound, YouHaveWonTheRound, EndOfGameText = Value
  }

  object Titles extends Enumeration {
    val GameTitle, QuitGame, GameOptions = Value
  }

  object Words extends Enumeration {
    val Yes, No, Pass = Value
  }

  object Menues extends Enumeration {
    val File, NewGame, Quit, Options, Players_2, Players_4, Players_6, Players_8, DeckSize,
    StartGame, Cancel, GameSpeed, Fast, Normal, Slow, GameLanguage = Value
  }
}