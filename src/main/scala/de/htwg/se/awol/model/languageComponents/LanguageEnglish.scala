package de.htwg.se.awol.model.languageComponents

import de.htwg.se.awol.model.environmentComponents._

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

    SettingEnv.Language.German -> "German",
    SettingEnv.Language.English -> "English",
    SettingEnv.Language.Youth -> "Youngster Talk"
  )

  override def getLanguageCode = SettingEnv.Language.English
}