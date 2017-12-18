package de.htwg.se.awol.model.cardComponents

import de.htwg.se.awol.model.language.LanguageManager
import de.htwg.se.awol.model.language.handler.StakeAndPepper
import de.htwg.se.awol.model.language.handler.StakeAndPepper.Translation

case object Card extends Enumeration {
  private val colorNames = Array[Translation](
    StakeAndPepper.C_Clubs,
    StakeAndPepper.C_Spades,
    StakeAndPepper.C_Hearts,
    StakeAndPepper.C_Diamonds
  )

  private val cardNames = Map[Int, Translation](
    11 -> StakeAndPepper.C_Jack,
    12 -> StakeAndPepper.C_Queen,
    13 -> StakeAndPepper.C_King,
    14 -> StakeAndPepper.C_Ace
  )
}

case class Card(value: Int, color: Int){
  // Validation for color not higher than 3 ?

  /**
    * Return the color name of the card
    * @return
    */
  def colorName: String = LanguageManager.getTranslation(Card.colorNames.apply(color))

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
}
