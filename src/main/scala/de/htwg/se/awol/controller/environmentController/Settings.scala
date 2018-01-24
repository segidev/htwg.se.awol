package de.htwg.se.awol.controller.environmentController

import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.awol.ArschlochModule
import de.htwg.se.awol.controller.environmentController.settings.TSettingsHandlers
import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.environmentComponents.SettingEnv
import de.htwg.se.awol.model.languageComponents.{LanguageEnglish, LanguageGerman, _LanguageHandler}

import scala.reflect.io.Directory
import scalafx.beans.property.BooleanProperty

object Settings {
  val injector: Injector = Guice.createInjector(new ArschlochModule())
  @Inject() private val settingsHandler: TSettingsHandlers = injector.getInstance(classOf[TSettingsHandlers])
  private val defaultSettingsDirectory: Directory = Directory(sys.props.apply("user.home")).resolve(".awol").toDirectory

  // Game Settings
  private val fastSpeed: Int = 1000
  private val normalSpeed: Int = 2000
  private val slowSpeed: Int = 3000

  private var timeBetweenPlayerAction: Int = normalSpeed

  val isNormalSpeedActive: BooleanProperty = BooleanProperty(true)
  isNormalSpeedActive.onChange {
    (_, _, newVal) => if (newVal) timeBetweenPlayerAction = normalSpeed
  }

  val isFastSpeedActive: BooleanProperty = BooleanProperty(false)
  isFastSpeedActive.onChange {
    (_, _, newVal) => if (newVal) timeBetweenPlayerAction = fastSpeed
  }

  val isSlowSpeedActive: BooleanProperty = BooleanProperty(false)
  isSlowSpeedActive.onChange {
    (_, _, newVal) => if (newVal) timeBetweenPlayerAction = slowSpeed
  }

  def getGameSpeed: Int = timeBetweenPlayerAction

  // Language settings
  val isGermanActive: BooleanProperty = BooleanProperty(false)
  isGermanActive.onChange(
    (_, _, newVal) => if (newVal) setLanguage(LanguageGerman)

  )
  val isEnglishActive: BooleanProperty = BooleanProperty(false)
  isEnglishActive.onChange(
    (_, _, newVal) => if (newVal) setLanguage(LanguageEnglish)
  )

  private var actualLanguage: _LanguageHandler = LanguageGerman

  def isLanguageActive(language: _LanguageHandler): Boolean = { actualLanguage == language }
  def getlanguage: _LanguageHandler = actualLanguage
  def getLanguageCode: SettingEnv.Language.Value = actualLanguage.getLanguageCode
  def setLanguage(lang: _LanguageHandler): Unit = {
    actualLanguage = lang
    LanguageTranslator.updateTranslations()
  }
  def getLanguageAsString: String = LanguageTranslator.translate(getLanguageCode)
  def setLanguageFromString(langStr: String): Unit = langStr match {
    case "German" => isGermanActive.update(true)
    case "English" => isEnglishActive.update(true)
    case _ => isGermanActive.update(true)
  }

  // Load / Saving
  def loadSettingsFromJSON(directory: Directory = defaultSettingsDirectory): Option[String] = {
    settingsHandler.setSettingsDirectory(directory)

    settingsHandler.load() match {
      case Some(settingsClass) =>
        settingsClass.speed match {
          case `normalSpeed` => isNormalSpeedActive.update(true)
          case `fastSpeed` => isFastSpeedActive.update(true)
          case `slowSpeed` => isSlowSpeedActive.update(true)
          case _ => isNormalSpeedActive.update(true)
        }
        setLanguageFromString(settingsClass.language)
        Game.setGameSettings(settingsClass.deckSize, settingsClass.playerCount, doSave = false)

        None
      case _ => Some("Couldn't load settings!")
    }
  }

  def saveSettingsToJSON(directory: Directory = defaultSettingsDirectory): Boolean = {
    settingsHandler.setSettingsDirectory(directory)

    val (deckSize, playerCount) = Game.getGameSettings

    settingsHandler.save(getGameSpeed, actualLanguage.getLanguageCode.toString, deckSize, playerCount)
  }
}
