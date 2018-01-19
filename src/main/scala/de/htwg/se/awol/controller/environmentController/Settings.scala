package de.htwg.se.awol.controller.environmentController

import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.environmentComponents.SettingEnv
import de.htwg.se.awol.model.languageComponents.{LanguageEnglish, LanguageGerman, LanguageYouth, _LanguageHandler}
import net.liftweb.json.{DefaultFormats, NoTypeHints, Serialization}
import net.liftweb.json.Serialization.{formats, read, write}

import scala.collection.mutable
import scala.reflect.io.{Directory, File, Path}

case class SettingsJSON(speed: Int, language: String, deckSize: Int, playerCount: Int)

object Settings {


  // Game Settings
  private val normalSpeed: Int = 2000
  private val fastSpeed: Int = 1000
  private val slowSpeed: Int = 3000

  private var timeBetweenPlayerAction: Int = normalSpeed

  def setNormalSpeed(): Unit = { timeBetweenPlayerAction = normalSpeed; }
  def isNormalSpeedActive: Boolean = timeBetweenPlayerAction == normalSpeed

  def setFastSpeed(): Unit = { timeBetweenPlayerAction = fastSpeed; }
  def isFastSpeedActive: Boolean = timeBetweenPlayerAction == fastSpeed

  def setSlowSpeed(): Unit = { timeBetweenPlayerAction = slowSpeed; }
  def isSlowSpeedActive: Boolean = timeBetweenPlayerAction == slowSpeed

  def getTimeBetweenPlayerAction: Int = timeBetweenPlayerAction

  // Language settings
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
    case "German" => setLanguage(LanguageGerman)
    case "English" => setLanguage(LanguageEnglish)
    case "Youth" => setLanguage(LanguageYouth)
  }

  // Saving
  implicit val formats: DefaultFormats.type = DefaultFormats

  def createAndGetSettingsPaths: Option[File] = {
    sys.props.get("user.home") match {
      case Some(dir) =>
        val outputDir = Path(dir).resolve(".awol")

        if (outputDir.exists) {
          val outputFullPath = outputDir.resolve(Path("settings.ini")).createFile()

          if (outputFullPath.exists) {
            Option(outputFullPath)
          } else {
            None
          }
        } else {
          None
        }
      case _ => None
    }
  }

  def saveSettingsToJSON(): Boolean = {
    val (deckSize, playerCount) = Game.getGameSettings

    val jsonSettingsClass = SettingsJSON(timeBetweenPlayerAction, actualLanguage.getLanguageCode.toString,
      deckSize, playerCount)

    val jsonString = write(jsonSettingsClass)

    createAndGetSettingsPaths match {
      case Some(savePath) =>
        savePath.writeAll(jsonString)
        true
      case _ => false
    }
  }

  def loadSettingsFromJSON(): Unit = {
    createAndGetSettingsPaths match {
      case Some(savePath) =>
        try {
          val jsonSettingsClass = read[SettingsJSON](savePath.bufferedReader())
          println(jsonSettingsClass)

          jsonSettingsClass.speed match {
            case `normalSpeed` => setNormalSpeed()
            case `fastSpeed` => setFastSpeed()
            case `slowSpeed` => setSlowSpeed()
          }
          setLanguageFromString(jsonSettingsClass.language)
          Game.setGameSettings(jsonSettingsClass.deckSize, jsonSettingsClass.playerCount, doSave = false)
        } catch {
          case e: Exception => println("Settings couldn't be loaded!\n%s".format(e.getMessage))
        }
      case _ =>
    }
  }
}
