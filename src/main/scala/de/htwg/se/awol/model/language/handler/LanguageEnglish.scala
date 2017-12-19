package de.htwg.se.awol.model.language.handler

import de.htwg.se.awol.model.environmentComponents._

object LangEng {
  def getTranslation(word: PlayerEnvironment): String = word match {
    case P_Mob => "Mob"
    case P_King => "King"
    case P_Viceroy => "Viceroy"
    case P_Viceasshole => "Viceasshole"
    case P_Asshole => "Asshole"
  }

  def getTranslation(word: CardEnvironment): String = word match {
    case C_Clubs => "Club"
    case C_Hearts => "Heart"
  }

  def getTranslation(word: MessageEnvironment): String = word match {
    case M_DividableByFour => "Amount of cards must be dividable by 4!"
    case M_MissingLanguage => "The language you specified was not found: "
  }
}

class LanguageEnglish extends LanguageHandler {
  override val messages = Map(
    PlayerEnvironment.P_Mob -> "Mob",
    PlayerEnvironment.P_King -> "King",
    PlayerEnvironment.P_Viceroy -> "Viceroy",
    PlayerEnvironment.P_Viceasshole -> "Viceasshole",
    PlayerEnvironment.P_Asshole -> "Asshole",

    CardEnvironment.C_Clubs -> "Club",
    CardEnvironment.C_Spades -> "Spade",
    CardEnvironment.C_Hearts -> "Heart",
    CardEnvironment.C_Diamonds -> "Diamond",
    CardEnvironment.C_Jack -> "Jack",
    CardEnvironment.C_Queen -> "Queen",
    CardEnvironment.C_King -> "King",
    CardEnvironment.C_Ace -> "Ace",

    MessageEnvironment.M_DividableByFour -> "Amount of cards must be dividable by 4!",
    MessageEnvironment.M_MaxAmountOfCards -> "Amount of cards must be in between 32 and 52",
    MessageEnvironment.M_MissingLanguage -> "The language you specified was not found: ",
    MessageEnvironment.M_CardColorNotExist -> "The given card color doesn't exist!"
  )
}