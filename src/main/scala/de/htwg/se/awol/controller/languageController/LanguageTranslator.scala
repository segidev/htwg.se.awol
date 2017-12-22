package de.htwg.se.awol.controller.languageController

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.model.environmentComponents.SettingEnv
import de.htwg.se.awol.model.languageComponents._

object LanguageTranslator {
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