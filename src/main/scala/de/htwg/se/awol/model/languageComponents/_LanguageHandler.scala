package de.htwg.se.awol.model.languageComponents

import de.htwg.se.awol.model.environmentComponents._

trait _LanguageHandler {
  val translationObjects = Array (
    PlayerEnv.Rank,
    CardEnv.Colors, CardEnv.Values,
    MessageEnv.Warnings, MessageEnv.Menues, MessageEnv.PhrasesGeneral, MessageEnv.PhrasesHuman, MessageEnv.PhrasesBot,
    MessageEnv.Questions, MessageEnv.Titles, MessageEnv.Words, SettingEnv.Language
  )

  def getTranslation(word: Any): String = translations.get(word) match {
    case Some(t) => t
    case _ => LanguageEnglish.getTranslationWithOption(word) match {
      case Some(t) => t
      case _ => LanguageEnglish.getTranslation(MessageEnv.Warnings.MissingTranslation).format(word.toString)
    }
  }

  def getTranslationWithOption(word: Any): Option[String] = translations.get(word) match {
    case Some(t) => Option(t)
    case _ => None
  }

  def translationCount: Int = translations.size

  val translations: Map[Any, String]

  def getLanguageCode: SettingEnv.Language.Value
}
