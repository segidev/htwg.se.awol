sealed trait PlayerEnvironment

case object P_Mob extends PlayerEnvironment
case object P_King extends PlayerEnvironment

sealed trait CardEnvironment

case object C_Clubs extends CardEnvironment
case object C_Hearts extends CardEnvironment

sealed trait MessageEnvironment

case object M_DividableByFour extends MessageEnvironment
case object M_MissingLanguage extends MessageEnvironment

object LangEng {
  def getTranslation(word: PlayerEnvironment): String = word match {
    case P_Mob => "Mob"
    case P_King => "King"
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

object LanguageManager {
  val actualTranslation = LangEng

  def getTranslation(word: PlayerEnvironment): String = actualTranslation.getTranslation(word)
  def getTranslation(word: CardEnvironment): String = actualTranslation.getTranslation(word)
  def getTranslation(word: MessageEnvironment): String = actualTranslation.getTranslation(word)
}

LanguageManager.getTranslation(P_Mob)

LanguageManager.getTranslation(C_Clubs)

LanguageManager.getTranslation(M_DividableByFour)

/*object LanguageManager {
  val actualTranslation = LanguageEnglish

  /*def getTranslation[T](w: T): String = actualTranslation.get(w) match {
    case Some(c) => c
    case  _ => "No translation found for: " + w
  }*/

  def getTranslation(w: PlayerEnvironment): String = actualTranslation.get(w) match {
    case Some(c) => c
    case  _ => "No translation found for: " + w
  }
}

object PlayerEnvironment extends Enumeration {
  type Player = Value
  val P_Mob, P_King, P_Viceroy, P_Viceasshole, P_Asshole = Value
}

object CardEnvironment extends Enumeration {
  type Card = Value
  val C_Clubs, C_Spades, C_Hearts, C_Diamonds, C_Jack, C_Queen, C_King, C_Ace = Value
}

object MessageEnvironment extends Enumeration {
  type Message = Value
  val M_DividableByFour, M_MaxAmountOfCards, M_MissingLanguage, M_CardColorNotExist = Value
}

//LanguageManager.getTranslation(1234)

//LanguageManager.getTranslation("HALLO!")

LanguageManager.getTranslation(MessageEnvironment.M_DividableByFour)*/