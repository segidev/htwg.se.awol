package de.htwg.se.awol.model.languageComponents

import de.htwg.se.awol.model.environmentComponents._

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
    CardEnv.Values.Two -> "2",
    CardEnv.Values.Three -> "3",
    CardEnv.Values.Four -> "4",
    CardEnv.Values.Five -> "5",
    CardEnv.Values.Six -> "6",
    CardEnv.Values.Seven -> "7",
    CardEnv.Values.Eight -> "8",
    CardEnv.Values.Nine -> "9",
    CardEnv.Values.Ten -> "10",
    CardEnv.Values.Jack -> "Bube",
    CardEnv.Values.Queen -> "Dame",
    CardEnv.Values.King -> "König",
    CardEnv.Values.Ace -> "Ass",

    MessageEnv.Warnings.DividableByFour -> "Anzahl der Karten muss durch 4 teilbar sein!",
    MessageEnv.Warnings.MaxAmountOfCards -> "Anzahl der Spielkarten muss zwischen 32 und 52 liegen.",
    MessageEnv.Warnings.MissingLanguage -> "Die gewünschte Sprache wurde nicht gefunden: %s",
    MessageEnv.Warnings.MissingTranslation -> "Es gibt keine Übersetzung für: %s",
    MessageEnv.Warnings.PlayerCountMismatch -> "Spieleranzahl von %d ist nicht gestattet!",
    MessageEnv.Warnings.LoadSettingsFailed -> "Einstellungen konnten nicht geladen werden!",
    MessageEnv.Warnings.WriteSettingsFailed -> "Einstellungen konnten nicht gespeichert werden!",

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
    MessageEnv.Menues.GameSpeed -> "Spielgeschwindigkeit",
    MessageEnv.Menues.Fast -> "Schnell",
    MessageEnv.Menues.Normal -> "Normal",
    MessageEnv.Menues.Slow -> "Langsam",
    MessageEnv.Menues.GameLanguage -> "Sprache",

    MessageEnv.Words.Yes -> "Ja",
    MessageEnv.Words.No -> "Nein",
    MessageEnv.Words.Pass -> "Passen",
    MessageEnv.Words.CardSwap -> "Kartentausch!\n\n",
    MessageEnv.Words.EndOfGame -> "Spiel beendet\n\n",

    MessageEnv.Titles.GameTitle -> "Arschloch",
    MessageEnv.Titles.QuitGame -> "Spiel beenden",
    MessageEnv.Titles.GameOptions -> "Spiel Optionen",

    MessageEnv.Questions.QuitGame -> "Wollen sie das Spiel wirklich beenden?",

    MessageEnv.PhrasesGeneral.WelcomeToTheGame -> "Willkommen im Spiel",
    MessageEnv.PhrasesGeneral.ClickAnywhereToContinue -> "\nKlicke irgendwo um fortzufahren...",
    MessageEnv.PhrasesGeneral.HitEnterToContinue -> "\nDrücke die Eingabetaste um fortzufahren...",
    MessageEnv.PhrasesGeneral.GameHasStarted -> "Das Spiel hat begonnen",
    MessageEnv.PhrasesGeneral.CardsHandedOutToPlayers -> "Jeder Spieler hat seine Karten erhalten",

    MessageEnv.PhrasesBot.WonTheRound -> "%s hat die Runde gewonnen!",
    MessageEnv.PhrasesBot.IsTheWinner -> "%s ist der Gewinner und somit %s\n",
    MessageEnv.PhrasesBot.IsTheLooser -> "%s hat verloren und ist das neue %s\n",
    MessageEnv.PhrasesBot.ReceivesCardFrom -> "%s erhält %s von %s\n",
    MessageEnv.PhrasesBot.ReceivesCardFromHuman -> "%s erhält %s von dir\n",
    MessageEnv.PhrasesBot.IsPlayingNow -> "\n== %s ist jetzt dran! ==\n",
    MessageEnv.PhrasesBot.Passed -> "\n%s hat gepasst",
    MessageEnv.PhrasesBot.UsedTheseCards -> "\n%s hat folgende Karten gespielt: %s",
    MessageEnv.PhrasesBot.HasCardsLeft -> "\n%s hat %d Karten übrig",

    MessageEnv.PhrasesHuman.WonTheRound -> "Du hast die Runde gewonnen! Super :)\n",
    MessageEnv.PhrasesHuman.IsTheWinner -> "Du bist der Gewinner und somit %s\n",
    MessageEnv.PhrasesHuman.IsTheLooser -> "Du hast verloren und bist das neue %s\n",
    MessageEnv.PhrasesHuman.ReceivesCardFrom -> "Du erhälst %s von %s\n",
    MessageEnv.PhrasesHuman.SuitableCards -> "Diese Karten kannst du spielen: %s",
    MessageEnv.PhrasesHuman.NoSuitableCards -> "Keine passenden Karten gefunden!",
    MessageEnv.PhrasesHuman.IsPlayingNow -> "\n== Du bist dran! ==",
    MessageEnv.PhrasesHuman.Passed -> "Du hast gepasst",
    MessageEnv.PhrasesHuman.PassForbidden -> "Du kannst jetzt nicht passen. Bitte lege deine Karten.",
    MessageEnv.PhrasesHuman.YouPlayedThoseCards -> "Du hast folgende Karten gespielt: %s",
    MessageEnv.PhrasesHuman.HowManyCardsToPlay -> "Wieviele Karten willst du spielen?",
    MessageEnv.PhrasesHuman.CommandNotAvailable -> "Befehl nicht möglich, bitte versuche einen anderen.",
    MessageEnv.PhrasesHuman.CardCommandNotAvailable -> "Befehl \"%s\" nicht möglich für Karten",

    SettingEnv.Language.German -> "Deutsch",
    SettingEnv.Language.English -> "Englisch",
    SettingEnv.Language.Youth -> "Jugendsprache"
  )

  override def getLanguageCode: SettingEnv.Language.Value = SettingEnv.Language.German
}