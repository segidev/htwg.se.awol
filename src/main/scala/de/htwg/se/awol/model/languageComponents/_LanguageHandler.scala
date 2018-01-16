package de.htwg.se.awol.model.languageComponents

import de.htwg.se.awol.model.environmentComponents._

trait _LanguageHandler {
  val translationObjects = Array(
    PlayerEnv.Rank,
    CardEnv.Colors, CardEnv.Values,
    MessageEnv.Warnings, MessageEnv.Menues, MessageEnv.Phrases, MessageEnv.Questions, MessageEnv.Titles, MessageEnv.Words,
    SettingEnv.Language
  )

  val translations: Map[Any, String]

  def getLanguageCode: SettingEnv.Language.Value

  def getTranslation(word: Any): String = translations.get(word) match {
    case Some(t) => t
    case _ => LanguageEnglish.getTranslationWithOption(word) match {
      case Some(t) => t
      case _ => LanguageEnglish.getTranslationWithOption(MessageEnv.Warnings.MissingTranslation) match {
        case Some(t) => t + word.toString
        case _ => throw new MatchError("Translation and fallback translation not available!")
      }
    }
  }

  def getTranslationWithOption(word: Any): Option[String] = translations.get(word) match {
    case Some(t) => Option(t)
    case _ => None
  }

  def translationCount: Int = translations.size
}
