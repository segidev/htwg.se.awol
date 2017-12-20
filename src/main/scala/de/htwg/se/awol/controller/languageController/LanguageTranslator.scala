package de.htwg.se.awol.controller.languageController

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.model.environmentComponents.SettingsEnvironment
import de.htwg.se.awol.model.languageComponents._

object LanguageTranslator {
  def translate[T](word: T): String = Settings.getLanguage match {
    case SettingsEnvironment.Language.German => LanguageGerman.getTranslation(word)
    case SettingsEnvironment.Language.English => LanguageEnglish.getTranslation(word)
    case SettingsEnvironment.Language.Youth => LanguageYouth.getTranslation(word)
    case _ => throw new MatchError("Given language" + word +  "was not found!")
  }
}