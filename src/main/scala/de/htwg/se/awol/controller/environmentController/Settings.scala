package de.htwg.se.awol.controller.environmentController

import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.environmentComponents.SettingEnv
import de.htwg.se.awol.model.languageComponents.{LanguageEnglish, LanguageGerman, _LanguageHandler}

import scalafx.beans.property.BooleanProperty

object Settings {
  // Game Settings
  private var normalSpeed: Int = 2000
  private var fastSpeed: Int = 1000
  private var slowSpeed: Int = 3000

  private var timeBetweenPlayerAction: Int = normalSpeed

  def setNormalSpeed(): Unit = { timeBetweenPlayerAction = normalSpeed }
  def setFastSpeed(): Unit = { timeBetweenPlayerAction = fastSpeed }
  def setSlowSpeed(): Unit = { timeBetweenPlayerAction = slowSpeed }

  def getTimeBetweenPlayerAction: Int = timeBetweenPlayerAction

  // Language settings
  private var actualLanguage: _LanguageHandler = LanguageEnglish

  def isLanguageActive(language: _LanguageHandler): Boolean = { actualLanguage == language }
  def getlanguage: _LanguageHandler = actualLanguage
  def getLanguageCode: SettingEnv.Language.Value = actualLanguage.getLanguageCode
  def getLanguageAsString: String = LanguageTranslator.translate(getLanguageCode)
  def setLanguage(lang: _LanguageHandler): Unit = {
    actualLanguage = lang
    LanguageTranslator.updateTranslations()
  }
}
