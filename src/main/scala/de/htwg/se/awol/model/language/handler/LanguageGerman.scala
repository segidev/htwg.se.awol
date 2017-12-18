package de.htwg.se.awol.model.language.handler

class LanguageGerman extends LanguageHandler {
  override val messages = Map(
    StakeAndPepper.P_Mob -> "Pöbel",
    StakeAndPepper.P_King -> "König",
    StakeAndPepper.P_Viceroy -> "Vize-König",
    StakeAndPepper.P_Viceasshole -> "Vize-Arschloch",
    StakeAndPepper.P_Asshole -> "Arschloch",

    StakeAndPepper.C_Clubs -> "Kreuz",
    StakeAndPepper.C_Spades -> "Pik",
    StakeAndPepper.C_Hearts -> "Herz",
    StakeAndPepper.C_Diamonds -> "Karo",
    StakeAndPepper.C_Jack -> "Bube",
    StakeAndPepper.C_Queen -> "Dame",
    StakeAndPepper.C_King -> "König",
    StakeAndPepper.C_Ace -> "Ass",

    StakeAndPepper.M_DividableByFour -> "Anzahl der Karten muss durch 4 teilbar sein!",
    StakeAndPepper.M_MaxAmountOfCards -> "Anzahl der Spielkarten muss zwischen 32 und 52 liegen.",
    StakeAndPepper.M_MissingLanguage -> "Die gewünschte Sprache wurde nicht gefunden: "
  )
}