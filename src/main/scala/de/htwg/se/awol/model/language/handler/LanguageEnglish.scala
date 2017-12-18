package de.htwg.se.awol.model.language.handler

class LanguageEnglish extends LanguageHandler {
  override val messages = Map(
    StakeAndPepper.P_Mob -> "Mob",
    StakeAndPepper.P_King -> "King",
    StakeAndPepper.P_Viceroy -> "Viceroy",
    StakeAndPepper.P_Viceasshole -> "Viceasshole",
    StakeAndPepper.P_Asshole -> "Asshole",

    StakeAndPepper.C_Clubs -> "Club",
    StakeAndPepper.C_Spades -> "Spade",
    StakeAndPepper.C_Hearts -> "Heart",
    StakeAndPepper.C_Diamonds -> "Diamond",
    StakeAndPepper.C_Jack -> "Jack",
    StakeAndPepper.C_Queen -> "Queen",
    StakeAndPepper.C_King -> "King",
    StakeAndPepper.C_Ace -> "Ace",

    StakeAndPepper.M_DividableByFour -> "Amount of cards must be dividable by 4!",
    StakeAndPepper.M_MaxAmountOfCards -> "Amount of cards must be in between 32 and 52",
    StakeAndPepper.M_MissingLanguage -> "The language you specified was not found: "
  )
}