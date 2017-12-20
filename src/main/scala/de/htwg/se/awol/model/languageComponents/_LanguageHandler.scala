package de.htwg.se.awol.model.languageComponents

import de.htwg.se.awol.model.environmentComponents.MessageEnvironment

trait _LanguageHandler {
  val translations: Map[Any, String]

  def getTranslation(word: Any): String = translations.get(word) match {
    case Some(t) => t
    case _ => getTranslation(MessageEnvironment.Warnings.MissingTranslation)
  }

  def translationCount: Int = translations.size
}
