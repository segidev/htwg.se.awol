package de.htwg.se.awol.controller.languageController

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.model.environmentComponents.SettingEnv
import de.htwg.se.awol.model.languageComponents._

import scala.collection.mutable
import scalafx.beans.property.StringProperty

object LanguageTranslator {
  var actualTranslated: mutable.Map[Any, StringProperty] = mutable.Map()

  def updateTranslations(): Unit = {
    for (transl <- Settings.getlanguage.translations) {
      val key = transl._1
      val text = transl._2

      actualTranslated.get(key) match {
        case Some(s) => s.update(translate(key))
        case _ => actualTranslated.put(key, StringProperty(translate(key)))
      }
    }
  }

  def bindTranslation[T](word: T): Option[StringProperty] = actualTranslated.get(word) match {
    case Some(t) => Option(t)
    case _ => None
  }

  def translate[T](word: T): String = Settings.getLanguageCode match {
    case SettingEnv.Language.German => LanguageGerman.getTranslation(word)
    case SettingEnv.Language.English => LanguageEnglish.getTranslation(word)
    case SettingEnv.Language.Youth => LanguageYouth.getTranslation(word)
    case _ => throw new MatchError("Given language " + Settings.getLanguageCode +  " was not found!")
  }

  def translateWithOption[T](word: T): Option[String] = Settings.getLanguageCode match {
    case SettingEnv.Language.German => LanguageGerman.getTranslationWithOption(word)
    case SettingEnv.Language.English => LanguageEnglish.getTranslationWithOption(word)
    case SettingEnv.Language.Youth => LanguageYouth.getTranslationWithOption(word)
    case _ => throw new MatchError("Given language " + Settings.getLanguageCode +  " was not found!")
  }
}