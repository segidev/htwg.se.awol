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
    CardEnv.Values.Jack -> "Bauer",
    CardEnv.Values.Queen -> "Schlampe",
    CardEnv.Values.King -> "Babo",
    CardEnv.Values.Ace -> "Arsch, haha",

    MessageEnv.Warnings.DividableByFour -> "Aldem, lern mal Matte du Spast",
    MessageEnv.Warnings.MaxAmountOfCards -> "Junge! Dein Kartem darf nur innen 32 und 54 sein oder so",
    MessageEnv.Warnings.MissingLanguage -> "Deine Mongosprache spricht hier keiner: ",
    MessageEnv.Warnings.MissingTranslation -> "Des wohrt gibts nit, du spasst: ",

    SettingEnv.Language.German -> "Kartoffelgelaber",
    SettingEnv.Language.English -> "Obamaland",
    SettingEnv.Language.Youth -> "Sprache vong Oberbabo"
  )

  override def getLanguageCode = SettingEnv.Language.Youth
}