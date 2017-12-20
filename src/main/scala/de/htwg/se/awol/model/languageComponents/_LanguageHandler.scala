package de.htwg.se.awol.model.languageComponents

import de.htwg.se.awol.model.environmentComponents._

trait _LanguageHandler {
  val translations: Map[Any, String]

  def getLanguageCode: SettingEnv.Language.Value

  def getTranslation(word: Any): String = translations.get(word) match {
    case Some(t) => t
    case _ => getTranslation(MessageEnv.Warnings.MissingTranslation)
  }

  def getTranslationWithOption(word: Any): Option[String] = translations.get(word) match {
    case Some(t) => Option(t)
    case _ => None
  }

  def translationCount: Int = translations.size
}
