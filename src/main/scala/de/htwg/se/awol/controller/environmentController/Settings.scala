package de.htwg.se.awol.controller.environmentController

import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.environmentComponents.SettingEnv
import de.htwg.se.awol.model.languageComponents.{LanguageGerman, _LanguageHandler}

object Settings {

  // Language settings
  var actualLanguage: _LanguageHandler = LanguageGerman //SettingEnv.Language.German

  def getlanguage: _LanguageHandler = actualLanguage
  def getLanguageCode: SettingEnv.Language.Value = actualLanguage.getLanguageCode
  def getLanguageAsString: String = LanguageTranslator.translate(getLanguageCode)
  def setLanguage(lang: _LanguageHandler): Unit = { actualLanguage = lang }
}
