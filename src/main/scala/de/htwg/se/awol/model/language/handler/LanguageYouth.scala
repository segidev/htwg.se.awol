package de.htwg.se.awol.model.language.handler

class LanguageYouth extends LanguageHandler {
  override val messages = Map(
    StakeAndPepper.P_Mob -> "Opfer",
    StakeAndPepper.P_King -> "Bonze",
    StakeAndPepper.P_Viceroy -> "Arschkriecher vong Bonze",
    StakeAndPepper.P_Viceasshole -> "Richtiger Otto",
    StakeAndPepper.P_Asshole -> "Der Hurensohn",

    StakeAndPepper.C_Clubs -> "Kräuz",
    StakeAndPepper.C_Spades -> "Dem sei Pik",
    StakeAndPepper.C_Hearts -> "Herzblut Blitzgewitter",
    StakeAndPepper.C_Diamonds -> "Bling Bling Diamant",
    StakeAndPepper.C_Jack -> "Bauer",
    StakeAndPepper.C_Queen -> "Schlampe",
    StakeAndPepper.C_King -> "Babo",
    StakeAndPepper.C_Ace -> "Arsch, haha",

    StakeAndPepper.M_DividableByFour -> "Aldem, lern mal Matte du Spast",
    StakeAndPepper.M_MaxAmountOfCards -> "Junge! Dein Kartem darf nur innen 32 und 54 sein oder so",
    StakeAndPepper.M_MissingLanguage -> "Deine Mongosprache spricht hier keiner: "
  )
}