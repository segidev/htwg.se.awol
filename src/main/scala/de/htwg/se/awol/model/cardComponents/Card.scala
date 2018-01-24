package de.htwg.se.awol.model.cardComponents

import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.environmentComponents.CardEnv

class Card(value: CardEnv.Values.Value, color: CardEnv.Colors.Value){

  def cardColorName: String = LanguageTranslator.translate(color)

  def cardValueName: String = LanguageTranslator.translate(value)

  def cardFilename: String = cardValue + "_of_" + Card.CardFileColorMap.apply(color.id)

  def cardValue: Int = value.id

  override def toString: String = cardValueName + " [" + cardColorName + "]"
  object Card {
    val CardFileColorMap: Map[Int, String] = Map(
      0 -> "clubs",
      1 -> "spades",
      2 -> "hearts",
      3 -> "diamonds"
    )
  }
}

