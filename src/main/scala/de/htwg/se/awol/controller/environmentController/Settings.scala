package de.htwg.se.awol.controller.environmentController

import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.environmentComponents.SettingEnv
import de.htwg.se.awol.model.languageComponents.{LanguageEnglish, LanguageGerman, _LanguageHandler}
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.{read, write}

import scala.reflect.io.{Directory, File, Path}
import scalafx.beans.property.BooleanProperty

case class SettingsJSON(speed: Int, language: String, deckSize: Int, playerCount: Int)

object Settings {

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
  def getLanguageAsString: String = LanguageTranslator.translate(getLanguageCode)
  def setLanguage(lang: _LanguageHandler): Unit = {
    actualLanguage = lang
    LanguageTranslator.updateTranslations()
  }
  def setLanguageFromString(langStr: String): Unit = langStr match {
    case "German" => isGermanActive.update(true)
    case "English" => isEnglishActive.update(true)
  }

  // Saving
  implicit val formats: DefaultFormats.type = DefaultFormats

  def getSettingsPath: File = {
    Path(sys.props.apply("user.home")).resolve(".awol").resolve("settings.ini").toFile
  }

  def createSettingsFile: Option[File] = {
    val fullSettingsPath: File = getSettingsPath
    val directorySettingsPath: Directory = fullSettingsPath.parent

    directorySettingsPath.createDirectory()

    if (directorySettingsPath.exists) {
      val writeFile: File = fullSettingsPath.createFile()

      if (writeFile.exists) {
        Option(writeFile)
      } else {
        None
      }
    } else {
      None
    }
  }

  def saveSettingsToJSON(): Boolean = {
    val (deckSize, playerCount) = Game.getGameSettings

    val jsonSettingsClass = SettingsJSON(getGameSpeed, actualLanguage.getLanguageCode.toString,
      deckSize, playerCount)

    val jsonString = write(jsonSettingsClass)

    createSettingsFile match {
      case Some(writeFile) =>
        writeFile.writeAll(jsonString)
        true
      case _ => false
    }
  }

  def loadSettingsFromJSON(): Boolean = {
    val fullSettingsPath: File = getSettingsPath

    if (fullSettingsPath.exists) {
      try {
        val jsonSettingsClass = read[SettingsJSON](fullSettingsPath.bufferedReader())

        jsonSettingsClass.speed match {
          case `normalSpeed` => isNormalSpeedActive.update(true)
          case `fastSpeed` => isFastSpeedActive.update(true)
          case `slowSpeed` => isSlowSpeedActive.update(true)
        }
        setLanguageFromString(jsonSettingsClass.language)
        Game.setGameSettings(jsonSettingsClass.deckSize, jsonSettingsClass.playerCount, doSave = false)

        true
      } catch {
        case _: Exception => false
      }
    } else {
      true
    }
  }
}
