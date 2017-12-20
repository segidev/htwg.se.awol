package de.htwg.se.awol.controller.environmentController

import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.environmentComponents.SettingsEnvironment

object Settings {

  // Language settings
  private var actualLanguage = SettingsEnvironment.Language.German

  def getLanguage = actualLanguage
  def getLanguageAsString: String = LanguageTranslator.translate(actualLanguage)
  def setLanguage(lang: SettingsEnvironment.Language.Value): Unit = { actualLanguage = lang }
}
