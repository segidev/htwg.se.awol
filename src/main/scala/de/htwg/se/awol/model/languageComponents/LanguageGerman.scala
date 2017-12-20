package de.htwg.se.awol.model.languageComponents

import de.htwg.se.awol.model.environmentComponents._

object LanguageGerman extends _LanguageHandler {
  override val translations: Map[Any, String] = Map(
    PlayerEnv.Rank.Mob -> "Pöbel",
    PlayerEnv.Rank.King -> "König",
    PlayerEnv.Rank.Viceroy -> "Vize-König",
    PlayerEnv.Rank.Viceasshole -> "Vize-Arschloch",
    PlayerEnv.Rank.Asshole -> "Arschloch",

    CardEnv.Colors.Clubs -> "Kreuz",
    CardEnv.Colors.Spades -> "Pik",
    CardEnv.Colors.Hearts -> "Herz",
    CardEnv.Colors.Diamonds -> "Karo",
    CardEnv.Values.Jack -> "Bube",
    CardEnv.Values.Queen -> "Dame",
    CardEnv.Values.King -> "König",
    CardEnv.Values.Ace -> "Ass",

    MessageEnv.Warnings.DividableByFour -> "Anzahl der Karten muss durch 4 teilbar sein!",
    MessageEnv.Warnings.MaxAmountOfCards -> "Anzahl der Spielkarten muss zwischen 32 und 52 liegen.",
    MessageEnv.Warnings.MissingLanguage -> "Die gewünschte Sprache wurde nicht gefunden: ",
    MessageEnv.Warnings.MissingTranslation -> "Es gibt keine Übersetzung für: ",

    SettingEnv.Language.German -> "Deutsch",
    SettingEnv.Language.English -> "Englisch",
    SettingEnv.Language.Youth -> "Jugendsprache"
  )

  override def getLanguageCode = SettingEnv.Language.German
}