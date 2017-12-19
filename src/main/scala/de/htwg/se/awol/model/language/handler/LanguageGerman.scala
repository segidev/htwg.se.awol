package de.htwg.se.awol.model.language.handler

import de.htwg.se.awol.model.environmentComponents.PlayerEnvironment

class LanguageGerman extends LanguageHandler {
  override val messages = Map(
    PlayerEnvironment.P_Mob -> "Pöbel",
    PlayerEnvironment.P_King -> "König",
    PlayerEnvironment.P_Viceroy -> "Vize-König",
    PlayerEnvironment.P_Viceasshole -> "Vize-Arschloch",
    PlayerEnvironment.P_Asshole -> "Arschloch",

    CardEnvironment.C_Clubs -> "Kreuz",
    CardEnvironment.C_Spades -> "Pik",
    CardEnvironment.C_Hearts -> "Herz",
    CardEnvironment.C_Diamonds -> "Karo",
    CardEnvironment.C_Jack -> "Bube",
    CardEnvironment.C_Queen -> "Dame",
    CardEnvironment.C_King -> "König",
    CardEnvironment.C_Ace -> "Ass",

    MessageEnvironment.M_DividableByFour -> "Anzahl der Karten muss durch 4 teilbar sein!",
    MessageEnvironment.M_MaxAmountOfCards -> "Anzahl der Spielkarten muss zwischen 32 und 52 liegen.",
    MessageEnvironment.M_MissingLanguage -> "Die gewünschte Sprache wurde nicht gefunden: ",
    MessageEnvironment.M_CardColorNotExist -> "Die gegebene Kartenfarbe existiert nicht!"
  )
}