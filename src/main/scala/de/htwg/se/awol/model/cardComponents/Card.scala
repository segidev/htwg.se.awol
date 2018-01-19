package de.htwg.se.awol.model.cardComponents

import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.environmentComponents.CardEnv

class Card(value: CardEnv.Values.Value, color: CardEnv.Colors.Value){

  def cardColorName: String = LanguageTranslator.translate(color)

  def cardName: String = LanguageTranslator.translate(value)

  def cardFilename: String = cardValue + "_of_" + CardEnv.CardFileColorMap.apply(color.id)

  def cardValue: Int = value.id

  override def toString: String = cardName + " [" + cardColorName + "]"
}
