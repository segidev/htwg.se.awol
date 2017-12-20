package de.htwg.se.awol.model.languageComponents

import de.htwg.se.awol.model.environmentComponents._

object LanguageYouth extends _LanguageHandler {
  override val translations: Map[Any, String] = Map(
    PlayerEnvironment.Rank.Mob -> "Opfer",
    PlayerEnvironment.Rank.King -> "Bonze",
    PlayerEnvironment.Rank.Viceroy -> "Arschkriecher vong Bonze",
    PlayerEnvironment.Rank.Viceasshole -> "Richtiger Otto",
    PlayerEnvironment.Rank.Asshole -> "Der Hurensohn",

    CardEnvironment.Colors.Clubs -> "Kräuz",
    CardEnvironment.Colors.Spades -> "Dem sei Pik",
    CardEnvironment.Colors.Hearts -> "Herzblut Blitzgewitter",
    CardEnvironment.Colors.Diamonds -> "Bling Bling Diamant",
    CardEnvironment.Descriptions.Jack -> "Bauer",
    CardEnvironment.Descriptions.Queen -> "Schlampe",
    CardEnvironment.Descriptions.King -> "Babo",
    CardEnvironment.Descriptions.Ace -> "Arsch, haha",

    MessageEnvironment.Warnings.DividableByFour -> "Aldem, lern mal Matte du Spast",
    MessageEnvironment.Warnings.MaxAmountOfCards -> "Junge! Dein Kartem darf nur innen 32 und 54 sein oder so",
    MessageEnvironment.Warnings.MissingLanguage -> "Deine Mongosprache spricht hier keiner: ",
    MessageEnvironment.Warnings.MissingTranslation -> "Des wohrt gibts nit, du spasst: ",

    SettingsEnvironment.Language.German -> "Kartoffelgelaber",
    SettingsEnvironment.Language.English -> "Obamaland",
    SettingsEnvironment.Language.Youth -> "Sprache vong Babo"
  )
}