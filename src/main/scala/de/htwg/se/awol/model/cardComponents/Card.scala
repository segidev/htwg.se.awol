package de.htwg.se.awol.model.cardComponents

import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.environmentComponents.CardEnvironment

case object Card extends Enumeration {
  // Colors
  val colorClubsIndex: Int = 0
  val colorSpadesIndex: Int = 1
  val colorHeartsIndex: Int = 2
  val colorDiamondsIndex: Int = 3

  private val colorNames: Map[Int, CardEnvironment.Colors.Value] = Map[Int, CardEnvironment.Colors.Value](
    colorClubsIndex -> CardEnvironment.Colors.Clubs,
    colorSpadesIndex -> CardEnvironment.Colors.Spades,
    colorHeartsIndex -> CardEnvironment.Colors.Hearts,
    colorDiamondsIndex -> CardEnvironment.Colors.Diamonds
  )

  // Values
  val minCardValue: Int = 2
  val maxCardValue: Int = 14

  val descriptionJackIndex: Int = 11
  val descriptionQueenIndex: Int = 12
  val descriptionKingIndex: Int = 13
  val descriptionAceIndex: Int = 14

  private val cardNames: Map[Int, CardEnvironment.Descriptions.Value] = Map[Int, CardEnvironment.Descriptions.Value](
    descriptionJackIndex -> CardEnvironment.Descriptions.Jack,
    descriptionQueenIndex -> CardEnvironment.Descriptions.Queen,
    descriptionKingIndex -> CardEnvironment.Descriptions.King,
    descriptionAceIndex -> CardEnvironment.Descriptions.Ace
  )
}

case class Card(value: Int, color: Int){
  validateCard()

  /**
    * Return the color name of the card
    * @return
    */
  def colorName: String = Card.colorNames.get(color) match {
    case Some(c) => LanguageTranslator.translate(c)
    case _ => throw new MatchError("Card color is undefined for " + color)
  }

  /**
    * Return the name of the card.
    * @return Either it's a number string or the name of the value
    */
  def cardName: String = Card.cardNames.get(value) match {
    case Some(v) =>  LanguageTranslator.translate(v)
    case _ => value.toString
  }

  /**
    * Create a nice representation of the card in a string
    * @return
    */
  override def toString: String = cardName + " [" + colorName + "]"

  // TODO Maybe find a better solution here?
  /**
    * Input validation on creation
    */
  private def validateCard(): Unit = {
    if (!Card.colorNames.contains(color)) {
      throw new IndexOutOfBoundsException("Card color exceeds allowed range, got " + color)
    }
    if (value < Card.minCardValue || value > Card.maxCardValue) {
      throw new IndexOutOfBoundsException("Card value exceeds allowed range, got " + value)
    }
  }
}
