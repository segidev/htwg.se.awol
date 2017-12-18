package de.htwg.se.awol.model.language.handler

// Just called it like that, since i don't find a fitting name :)
// TODO Let's probably find a better solution than handling all important keywords in this object
object StakeAndPepper extends Enumeration {
  type Translation = Value
  val P_Mob, P_King, P_Viceroy, P_Viceasshole, P_Asshole,
  C_Clubs, C_Spades, C_Hearts, C_Diamonds, C_Jack, C_Queen, C_King, C_Ace,
  M_DividableByFour, M_MaxAmountOfCards, M_MissingLanguage = Value
}

trait LanguageHandler {
  val messages: Map[StakeAndPepper.Value, String]

  /**
    * Return the amount of available words in this translation class.
    * @return Amount of translations
    */
  def translationCount: Int = messages.size

  /**
    * Get the given shortstrings translation or return a default warning
    * @param translationKeyword The shortstring for the translation
    * @return The translated string or a message that the translation wasn't found
    */
  def getTranslation(translationKeyword: StakeAndPepper.Value): String = {
    messages.getOrElse(translationKeyword, String.format("Translation for \"%s\" not found!", translationKeyword.toString))
    // throw new Exception(String.format("Translation for \"%s\" not found!", str))
  }
}
