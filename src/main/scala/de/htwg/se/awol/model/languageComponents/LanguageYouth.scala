package de.htwg.se.awol.model.languageComponents

import de.htwg.se.awol.model.environmentComponents.{SettingEnv, _}

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
    CardEnv.Values.Ace -> "Arsch, haha",

    MessageEnv.Warnings.DividableByFour -> "Aldem, lern mal Matte du Spast",
    MessageEnv.Warnings.MaxAmountOfCards -> "Junge! Dein Kartem darf nur innen 32 und 54 sein oder so",
    MessageEnv.Warnings.MissingLanguage -> "Deine Mongosprache spricht hier keiner: ",
    MessageEnv.Warnings.MissingTranslation -> "Keine Übersetzung lan: ",

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

    MessageEnv.Titles.GameTitle -> "Arschloch",
    MessageEnv.Titles.QuitGame -> "Spiel sein lassen",
    MessageEnv.Titles.GameOptions -> "Spiel Auswahlen",

    MessageEnv.Questions.QuitGame -> "Bisch sicher?",

    MessageEnv.Phrases.WelcomeToTheGame -> "Was geht, lan",
    MessageEnv.Phrases.HandingOutCards -> "Drogen werden gedealt\nKlick mal irgendwo",
    MessageEnv.Phrases.HasWonTheRound -> "hat alle kaputt gef****!",
    MessageEnv.Phrases.YouHaveWonTheRound -> "Du hast alles zerf****! Fett xD",
    MessageEnv.Phrases.EndOfGameText -> "Party zuende!\n\n%s ruled und ist %s\n%s hat versagt und ist %s\n\nKlick einfach, dann geht weida!",

    SettingEnv.Language.German -> "Kartoffelgelaber",
    SettingEnv.Language.English -> "Obamaland",
    SettingEnv.Language.Youth -> "Sprache vong Oberbabo"
  )

  override def getLanguageCode: SettingEnv.Language.Value = SettingEnv.Language.Youth
}