package de.htwg.se.awol.model.languageComponents

import de.htwg.se.awol.model.environmentComponents._

object LanguageGerman extends _LanguageHandler {
  override val translations: Map[Any, String] = Map(
    PlayerEnvironment.Rank.Mob -> "Pöbel",
    PlayerEnvironment.Rank.King -> "König",
    PlayerEnvironment.Rank.Viceroy -> "Vize-König",
    PlayerEnvironment.Rank.Viceasshole -> "Vize-Arschloch",
    PlayerEnvironment.Rank.Asshole -> "Arschloch",

    CardEnvironment.Colors.Clubs -> "Kreuz",
    CardEnvironment.Colors.Spades -> "Pik",
    CardEnvironment.Colors.Hearts -> "Herz",
    CardEnvironment.Colors.Diamonds -> "Karo",
    CardEnvironment.Descriptions.Jack -> "Bube",
    CardEnvironment.Descriptions.Queen -> "Dame",
    CardEnvironment.Descriptions.King -> "König",
    CardEnvironment.Descriptions.Ace -> "Ass",

    MessageEnvironment.Warnings.DividableByFour -> "Anzahl der Karten muss durch 4 teilbar sein!",
    MessageEnvironment.Warnings.MaxAmountOfCards -> "Anzahl der Spielkarten muss zwischen 32 und 52 liegen.",
    MessageEnvironment.Warnings.MissingLanguage -> "Die gewünschte Sprache wurde nicht gefunden: ",
    MessageEnvironment.Warnings.MissingTranslation -> "Es gibt keine Übersetzung für: ",

    SettingsEnvironment.Language.German -> "Deutsch",
    SettingsEnvironment.Language.English -> "Englisch",
    SettingsEnvironment.Language.Youth -> "Jugendsprache"
  )
}