package de.htwg.se.awol.model.languageComponents

import de.htwg.se.awol.model.environmentComponents.{SettingEnv, _}

object LanguageGerman extends _LanguageHandler {
  override val translations: Map[Any, String] = Map(
    PlayerEnv.Rank.Mob -> "Pöbel",
    PlayerEnv.Rank.King -> "König",
    PlayerEnv.Rank.Viceroy -> "Vize-König",
    PlayerEnv.Rank.Viceasshole -> "Vize-Arschloch",
    PlayerEnv.Rank.Asshole -> "Arschloch",

    PlayerEnv.BotNames.Player_1 -> "Ich",

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

    MessageEnv.Menues.File -> "Datei",
    MessageEnv.Menues.NewGame -> "Neues Spiel",
    MessageEnv.Menues.Quit -> "Beenden",
    MessageEnv.Menues.Options -> "Optionen",
    MessageEnv.Menues.Players_2 -> "2 Spieler",
    MessageEnv.Menues.Players_4 -> "4 Spieler",
    MessageEnv.Menues.Players_6 -> "6 Spieler",
    MessageEnv.Menues.Players_8 -> "8 Spieler",
    MessageEnv.Menues.DeckSize -> "Stapelgröße",
    MessageEnv.Menues.StartGame -> "Spiel starten",
    MessageEnv.Menues.Cancel -> "Abbrechen",

    MessageEnv.Words.Yes -> "Ja",
    MessageEnv.Words.No -> "Nein",
    MessageEnv.Words.Pass -> "Passen",

    MessageEnv.Titles.GameTitle -> "Arschloch",
    MessageEnv.Titles.QuitGame -> "Spiel beenden",
    MessageEnv.Titles.GameOptions -> "Spiel Optionen",

    MessageEnv.Questions.QuitGame -> "Wollen sie das Spiel wirklich beenden?",

    MessageEnv.Phrases.WelcomeToTheGame -> "Willkommen im Spiel",

    SettingEnv.Language.German -> "Deutsch",
    SettingEnv.Language.English -> "Englisch",
    SettingEnv.Language.Youth -> "Jugendsprache"
  )

  override def getLanguageCode: SettingEnv.Language.Value = SettingEnv.Language.German
}