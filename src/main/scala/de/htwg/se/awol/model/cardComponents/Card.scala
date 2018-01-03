package de.htwg.se.awol.model.cardComponents

import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.environmentComponents.CardEnv

case class Card(value: CardEnv.Values.Value, color: CardEnv.Colors.Value){
  /**
    * Return the color name of the card
    * @return
    */
  def cardColorName: String = LanguageTranslator.translate(color)

  /**
    * Return the name of the card.
    * @return Either it's a number string or the name of the value
    */
  def cardName: String = LanguageTranslator.translateWithOption(value) match {
    case Some(v) => v
    case _ => value.id.toString
  }

  def cardValue: Int = value.id

  /**
    * Create a nice representation of the card in a string
    * @return
    */
  override def toString: String = cardName + " [" + cardColorName + "]"
}
