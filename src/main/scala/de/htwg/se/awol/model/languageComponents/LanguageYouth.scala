package de.htwg.se.awol.model.languageComponents

import de.htwg.se.awol.model.environmentComponents._

object LanguageYouth extends _LanguageHandler {
  override val translations: Map[Any, String] = Map(
    PlayerEnv.Rank.Mob -> "Opfer",
    PlayerEnv.Rank.King -> "Bonze",
    PlayerEnv.Rank.Viceroy -> "Arschkriecher vong Bonze",
    PlayerEnv.Rank.Viceasshole -> "Richtiger Otto",
    PlayerEnv.Rank.Asshole -> "Der Hurensohn",

    PlayerEnv.BotNames.Player_1 -> "Du fei",

    CardEnv.Colors.Clubs -> "Kräuz",
    CardEnv.Colors.Spades -> "Dem sei Pik",
    CardEnv.Colors.Hearts -> "Herzblut Blitzgewitter",
    CardEnv.Colors.Diamonds -> "Bling Bling Diamant",
    CardEnv.Values.Two -> "2",
    CardEnv.Values.Three -> "3",
    CardEnv.Values.Four -> "4",
    CardEnv.Values.Five -> "5",
    CardEnv.Values.Six -> "6",
    CardEnv.Values.Seven -> "7",
    CardEnv.Values.Eight -> "8",
    CardEnv.Values.Nine -> "9",
    CardEnv.Values.Ten -> "10",
    CardEnv.Values.Jack -> "Bauer",
    CardEnv.Values.Queen -> "Schlampe",
    CardEnv.Values.King -> "Babo",
    CardEnv.Values.Ace -> "Ass",

    MessageEnv.Warnings.DividableByFour -> "Aldem, lern mal Matte du Spast",
    MessageEnv.Warnings.MaxAmountOfCards -> "Junge! Dein Kartem darf nur innen 32 und 54 sein oder so",
    MessageEnv.Warnings.MissingLanguage -> "Deine Mongosprache spricht hier keiner: %s",
    MessageEnv.Warnings.MissingTranslation -> "Keine Übersetzung lan: %s",
    MessageEnv.Warnings.PlayerCountMismatch -> "Soviel Spieler sind fei net erlaubt!",
    MessageEnv.Warnings.LoadSettingsFailed -> "Scheisse! Ganze config weg!",
    MessageEnv.Warnings.WriteSettingsFailed -> "Eine Stellung wurde nicht gespeichert!",

    MessageEnv.Menues.File -> "Hauptding",
    MessageEnv.Menues.NewGame -> "Neu",
    MessageEnv.Menues.Quit -> "Aufhören",
    MessageEnv.Menues.Options -> "Auswahlen",
    MessageEnv.Menues.Players_2 -> "2 Spasten",
    MessageEnv.Menues.Players_4 -> "4 Spasten",
    MessageEnv.Menues.Players_6 -> "6 Spasten",
    MessageEnv.Menues.Players_8 -> "8 Spasten",
    MessageEnv.Menues.DeckSize -> "Karten",
    MessageEnv.Menues.StartGame -> "Anfangen",
    MessageEnv.Menues.Cancel -> "Sein lassen",
    MessageEnv.Menues.GameSpeed -> "Schnellheit",
    MessageEnv.Menues.Fast -> "Schenella",
    MessageEnv.Menues.Normal -> "Ertrhäglich",
    MessageEnv.Menues.Slow -> "Schneckentempo",
    MessageEnv.Menues.GameLanguage -> "Gelaber",

    MessageEnv.Words.Yes -> "Sick",
    MessageEnv.Words.No -> "RIP",
    MessageEnv.Words.Pass -> "Drauf scheissen",
    MessageEnv.Words.CardSwap -> "Gaunerzeit!\n\n",
    MessageEnv.Words.EndOfGame -> "Kampf zu Ente\n\n",

    MessageEnv.Titles.GameTitle -> "Arschloch",
    MessageEnv.Titles.QuitGame -> "Spiel sein lassen",
    MessageEnv.Titles.GameOptions -> "Spiel Auswahlen",

    MessageEnv.Questions.QuitGame -> "Bisch sicher?",

    MessageEnv.PhrasesGeneral.WelcomeToTheGame -> "Was geht, lan",
    MessageEnv.PhrasesGeneral.ClickAnywhereToContinue -> "\nKlick ein Fach, dann geht weida...",
    MessageEnv.PhrasesGeneral.HitEnterToContinue -> "\nDrücke die fette Taste zum weitermachen...",
    MessageEnv.PhrasesGeneral.GameHasStarted -> "Die Party hat begonnen!",
    MessageEnv.PhrasesGeneral.CardsHandedOutToPlayers -> "Jeder Junkie hat sein Koks erhalten",

    MessageEnv.PhrasesBot.WonTheRound -> "%s hat alle kaputt gef****!",
    MessageEnv.PhrasesBot.IsTheWinner -> "%s isch Killer und %s\n",
    MessageEnv.PhrasesBot.IsTheLooser -> "%s hat verkackt und ist %s\n",
    MessageEnv.PhrasesBot.ReceivesCardFrom -> "%s snatcht %s vong %s\n",
    MessageEnv.PhrasesBot.ReceivesCardFromHuman -> "%s snatcht %s vong dir\n",
    MessageEnv.PhrasesBot.IsPlayingNow -> "\n== %s isch jetzt dran! ==\n",
    MessageEnv.PhrasesBot.Passed -> "\n%s hat sich in die Hose geschissen und nix gemacht",
    MessageEnv.PhrasesBot.UsedTheseCards -> "\n%s hat die Karten rausgehauen: %s",
    MessageEnv.PhrasesBot.HasCardsLeft -> "\n%s hat noch %d Karten noch",

    MessageEnv.PhrasesHuman.WonTheRound -> "Du hast alles zerf****! Fett!",
    MessageEnv.PhrasesHuman.IsTheWinner -> "Du hasch geruled und bisch %s\n",
    MessageEnv.PhrasesHuman.IsTheLooser -> "Du Versager, jetzt bist du %s\n",
    MessageEnv.PhrasesHuman.ReceivesCardFrom -> "Du kriegsch %s vom %s\n",
    MessageEnv.PhrasesHuman.SuitableCards -> "Die Karten sind legal zu legen: %s",
    MessageEnv.PhrasesHuman.NoSuitableCards -> "Solche Karten hasch du net!",
    MessageEnv.PhrasesHuman.IsPlayingNow -> "\n== Du bisch dran! ==",
    MessageEnv.PhrasesHuman.Passed -> "Du hast dich gedrückt",
    MessageEnv.PhrasesHuman.PassForbidden -> "Man checks doch. Du kannsch jetzt net passen, Mongo.",
    MessageEnv.PhrasesHuman.YouPlayedThoseCards -> "Dein Karten waren mit dem: %s",
    MessageEnv.PhrasesHuman.HowManyCardsToPlay -> "Wiefiel vong Karten sollen gehen",
    MessageEnv.PhrasesHuman.CommandNotAvailable -> "Des Kommando geht jetzt net.",
    MessageEnv.PhrasesHuman.CardCommandNotAvailable -> "Kommando \"%s\" passt net für Karten",

    SettingEnv.Language.German -> "Kartoffelgelaber",
    SettingEnv.Language.English -> "Obamaland",
    SettingEnv.Language.Youth -> "Sprache vong Oberbabo"
  )

  override def getLanguageCode: SettingEnv.Language.Value = SettingEnv.Language.Youth
}