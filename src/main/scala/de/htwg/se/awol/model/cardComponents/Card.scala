package de.htwg.se.awol.model.cardComponents

import de.htwg.se.awol.model.environmentComponents._
import de.htwg.se.awol.model.language.LanguageM

case object Card extends Enumeration {
  private val colorNames = Map[Int, CardEnvironment.Card](
    0 -> CardEnvironment.C_Clubs,
    1 -> CardEnvironment.C_Spades,
    2 -> CardEnvironment.C_Hearts,
    3 -> CardEnvironment.C_Diamonds
  )

  private val cardNames = Map[Int, CardEnvironment.Card](
    11 -> CardEnvironment.C_Jack,
    12 -> CardEnvironment.C_Queen,
    13 -> CardEnvironment.C_King,
    14 -> CardEnvironment.C_Ace
  )
}

class Card(value: Int, color: Int) extends LanguageM {
  // Validation for color not higher than 3 ?

  def lala = getTranslation(GlobalEnvironment.C_Queen)

  /*
  /**
    * Return the color name of the card
    * @return
    */
  def colorName: String = Card.colorNames.get(color) match {
    case Some(c) => LanguageManager.getTranslation(c)
    case _ => LanguageManager.getTranslation(MessageEnvironment.M_CardColorNotExist)
  }



  /**
    * Return the name of the card.
    * @return Either it's a number string or the name of the value
    */
  def cardName: String = Card.cardNames.get(value) match {
    case Some(name) =>  LanguageManager.getTranslation(name)
    case _ => value.toString
  }

  /**
    * Create a nice representation of the card in a string
    * @return
    */
  override def toString: String = cardName + " [" + colorName + "]"
  */
  override def getTranslation(a: GlobalEnvironment.Card) = ""
}
