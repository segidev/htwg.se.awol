package de.htwg.se.awol.model.languageComponents

import de.htwg.se.awol.model.environmentComponents._

object LanguageEnglish extends _LanguageHandler {
  override val translations: Map[Any, String] = Map(
    PlayerEnvironment.Rank.Mob -> "Mob",
    PlayerEnvironment.Rank.King -> "King",
    PlayerEnvironment.Rank.Viceroy -> "Viceroy",
    PlayerEnvironment.Rank.Viceasshole -> "Viceasshole",
    PlayerEnvironment.Rank.Asshole -> "Asshole",

    CardEnvironment.Colors.Clubs -> "Club",
    CardEnvironment.Colors.Spades -> "Spade",
    CardEnvironment.Colors.Hearts -> "Heart",
    CardEnvironment.Colors.Diamonds -> "Diamond",
    CardEnvironment.Descriptions.Jack -> "Jack",
    CardEnvironment.Descriptions.Queen -> "Queen",
    CardEnvironment.Descriptions.King -> "King",
    CardEnvironment.Descriptions.Ace -> "Ace",

    MessageEnvironment.Warnings.DividableByFour -> "Amount of cards must be dividable by 4!",
    MessageEnvironment.Warnings.MaxAmountOfCards -> "Amount of cards must be in between 32 and 52",
    MessageEnvironment.Warnings.MissingLanguage -> "The language you specified was not found: ",
    MessageEnvironment.Warnings.MissingTranslation -> "Not translation found for: ",

    SettingsEnvironment.Language.German -> "German",
    SettingsEnvironment.Language.English -> "English",
    SettingsEnvironment.Language.Youth -> "Youngster Talk"
  )
}