package de.htwg.se.awol.environmentController

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.controller.environmentController.settings.SettingsJSON
import de.htwg.se.awol.model.environmentComponents.SettingEnv
import de.htwg.se.awol.model.languageComponents.LanguageGerman
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

import scala.reflect.io.File

@RunWith(classOf[JUnitRunner])
class SettingsSpec extends WordSpec with Matchers {
  "The Settings class" can {
    "check whether a certain language is active" in {
      Settings.setLanguage(LanguageGerman)
      Settings.isLanguageActive(LanguageGerman) should be(true)
    }
    "set the language with a String" in {
      Settings.setLanguageFromString("German")
      Settings.getLanguageAsString should be("Deutsch")
      Settings.setLanguageFromString("English")
      Settings.getLanguageAsString should be("English")
    }
    "create an instance of SettingsJSON" in {
      val settingsObj: SettingsJSON = SettingsJSON(2000, "Deutsch", 32, 4)
      settingsObj.speed should be(2000)
      settingsObj.language should be("Deutsch")
      settingsObj.deckSize should be(32)
      settingsObj.playerCount should be(4)
    }
    "not create a file with wrong directory" in {
      val path: File = File("C:\\Program Files (x86)\\notexistent\\invalid.log")
      Settings.createSettingsFile(path) should be(None)
    }
    "not create a file with invalid file name" in {
      val path: File = File("C:\\Program Files (x86)\\NuGet\\invalid.log")
      Settings.createSettingsFile(path) should be(None)
    }
    "not save into a file without write access" in {
      val path: File = File("C:\\Program Files (x86)\\NuGet\\invalid.log")
      Settings.saveSettingsToJSON(path) should be(false)
    }
    "load a file without write access" in {
      val path: File = File("C:\\Program Files (x86)\\NuGet\\invalid.log")
      Settings.loadSettingsFromJSON(path) should be(None)
    }
    "save and load all different speed settings" in {
      val path: File = File(sys.props.apply("user.home")).resolve(".awol").resolve("settings.ini").toFile
      val settings: String = path.bufferedReader().readLine()
      path.delete()

      path.createFile().writeAll("{\"speed\":1000,\"language\":\"German\",\"deckSize\":32,\"playerCount\":4}")
      Settings.loadSettingsFromJSON(path) should be(None)

      path.createFile().writeAll("{\"speed\":2000,\"language\":\"German\",\"deckSize\":32,\"playerCount\":4}")
      Settings.loadSettingsFromJSON(path) should be(None)

      path.createFile().writeAll("{\"speed\":3000,\"language\":\"German\",\"deckSize\":32,\"playerCount\":4}")
      Settings.loadSettingsFromJSON(path) should be(None)

      path.delete()
      path.createFile().writeAll(settings)
    }

  }
  "The SettingEnv object" should {
    "be callable" in {
      SettingEnv
    }
  }
}
