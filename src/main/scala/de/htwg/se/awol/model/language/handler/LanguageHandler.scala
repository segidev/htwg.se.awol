package de.htwg.se.awol.model.language.handler

import de.htwg.se.awol.model.environmentComponents._

trait LanguageHandler {
  def getTranslation(word: PlayerEnvironment): String
  def getTranslation(word: CardEnvironment): String
  def getTranslation(word: MessageEnvironment): String
}
