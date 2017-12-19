package de.htwg.se.awol.model.language

import de.htwg.se.awol.model.environmentComponents._
import de.htwg.se.awol.model.language.handler._

case object LanguageManager {
  val english: String = "en"
  val german: String = "de"
  val youth: String = "yt"

  private val languages: Map[String, LanguageHandler] = Map(
    english -> new LanguageEnglish,
    german -> new LanguageGerman,
    youth -> new LanguageYouth
  )

  // By default the language is German
  private var actualLanguage: LanguageHandler = languages.apply(german)
  private var actualLangCode: String = german

  /**
    * Return the given language if found. Otherwise inform the player.
    * @param lang Must be an available country code string. eg: de, en, ...
    */
  def switchLanguage(lang: String): Unit = languages.get(lang) match {
    case Some(langHandler) => {
      actualLanguage = langHandler
      actualLangCode = lang
    }
    case _ => println(getTranslation(MessageEnvironment.M_MissingLanguage) + lang)
  }

  /**
    * Return the actual language as the country code
    * @return "de" or "en"
    */
  def getLanguage: String = actualLangCode

  /**
    * Return the translated string of the actual chosen language if a translation is available
    * @param translationKeyword The short string for the translation
    * @return The translated string or a message that the translation wasn't found
    */
  def getTranslation(word: PlayerEnvironment): String = actualLanguage.getTranslation(word)
  def getTranslation(word: CardEnvironment): String = actualLanguage.getTranslation(word)
  def getTranslation(word: MessageEnvironment): String = actualLanguage.getTranslation(word)
  //def getTranslation(translationKeyword: MessageEnvironment.Message): String = actualLanguage.getTranslation(translationKeyword)

  def availableTranslations: Map[String, LanguageHandler] = languages
}

trait LanguageM {
  def getTranslation(a: GlobalEnvironment.Value): String
}