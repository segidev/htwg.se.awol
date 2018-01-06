package de.htwg.se.awol.model.languageComponents

import de.htwg.se.awol.model.environmentComponents.{SettingEnv, _}

object LanguageEnglish extends _LanguageHandler {
  override val translations: Map[Any, String] = Map(
    PlayerEnv.Rank.Mob -> "Mob",
    PlayerEnv.Rank.King -> "King",
    PlayerEnv.Rank.Viceroy -> "Viceroy",
    PlayerEnv.Rank.Viceasshole -> "Viceasshole",
    PlayerEnv.Rank.Asshole -> "Asshole",

    CardEnv.Colors.Clubs -> "Club",
    CardEnv.Colors.Spades -> "Spade",
    CardEnv.Colors.Hearts -> "Heart",
    CardEnv.Colors.Diamonds -> "Diamond",
    CardEnv.Values.Jack -> "Jack",
    CardEnv.Values.Queen -> "Queen",
    CardEnv.Values.King -> "King",
    CardEnv.Values.Ace -> "Ace",

    MessageEnv.Warnings.DividableByFour -> "Amount of cards must be dividable by 4!",
    MessageEnv.Warnings.MaxAmountOfCards -> "Amount of cards must be in between 32 and 52",
    MessageEnv.Warnings.MissingLanguage -> "The language you specified was not found: ",
    MessageEnv.Warnings.MissingTranslation -> "No translation found for: ",

    MessageEnv.Menues.File -> "File",
    MessageEnv.Menues.NewGame -> "New Game",
    MessageEnv.Menues.Quit -> "Quit",
    MessageEnv.Menues.Options -> "Options",
    MessageEnv.Menues.Players_2 -> "2 Players",
    MessageEnv.Menues.Players_4 -> "4 Players",
    MessageEnv.Menues.Players_6 -> "6 Players",
    MessageEnv.Menues.Players_8 -> "8 Players",
    MessageEnv.Menues.DeckSize -> "Decksize",
    MessageEnv.Menues.StartGame -> "Start Game",
    MessageEnv.Menues.Cancel -> "Cancel",

    MessageEnv.Words.Yes -> "Yes",
    MessageEnv.Words.No -> "No",

    MessageEnv.Titles.GameTitle -> "Asshole",
    MessageEnv.Titles.QuitGame -> "Quit Game",
    MessageEnv.Titles.GameOptions -> "Game Options",

    MessageEnv.Questions.QuitGame -> "Do you really want to exit the game?",

    MessageEnv.Phrases.WelcomeToTheGame -> "Welcome to the game",

    SettingEnv.Language.German -> "German",
    SettingEnv.Language.English -> "English",
    SettingEnv.Language.Youth -> "Youngster Talk"
  )

  override def getLanguageCode: SettingEnv.Language.Value = SettingEnv.Language.English
}