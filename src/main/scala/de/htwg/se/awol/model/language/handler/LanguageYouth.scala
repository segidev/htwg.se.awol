package de.htwg.se.awol.model.language.handler

import de.htwg.se.awol.model.environmentComponents.PlayerEnvironment

class LanguageYouth extends LanguageHandler {
  override val messages = Map(
    PlayerEnvironment.P_Mob -> "Opfer",
    PlayerEnvironment.P_King -> "Bonze",
    PlayerEnvironment.P_Viceroy -> "Arschkriecher vong Bonze",
    PlayerEnvironment.P_Viceasshole -> "Richtiger Otto",
    PlayerEnvironment.P_Asshole -> "Der Hurensohn",

    CardEnvironment.C_Clubs -> "Kräuz",
    CardEnvironment.C_Spades -> "Dem sei Pik",
    CardEnvironment.C_Hearts -> "Herzblut Blitzgewitter",
    CardEnvironment.C_Diamonds -> "Bling Bling Diamant",
    CardEnvironment.C_Jack -> "Bauer",
    CardEnvironment.C_Queen -> "Schlampe",
    CardEnvironment.C_King -> "Babo",
    CardEnvironment.C_Ace -> "Arsch, haha",

    MessageEnvironment.M_DividableByFour -> "Aldem, lern mal Matte du Spast",
    MessageEnvironment.M_MaxAmountOfCards -> "Junge! Dein Kartem darf nur innen 32 und 54 sein oder so",
    MessageEnvironment.M_MissingLanguage -> "Deine Mongosprache spricht hier keiner: ",
    MessageEnvironment.M_CardColorNotExist -> "Es gibt kein Karte vong der Farbe her"
  )
}