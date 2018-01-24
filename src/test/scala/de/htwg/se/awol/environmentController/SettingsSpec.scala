package de.htwg.se.awol.environmentController

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.controller.environmentController.settings.SettingsOutput
import de.htwg.se.awol.controller.environmentController.settings.json.{SettingsHandler => JSON}
import de.htwg.se.awol.controller.environmentController.settings.xml.{SettingsHandler => XML}

import scala.io.Source
import scala.reflect.io.{Directory, Path}
//import de.htwg.se.awol.controller.environmentController.settings.SettingsJSON
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
      Settings.setLanguageFromString("abc")
      Settings.getLanguageAsString should be("Deutsch")
      Settings.setLanguageFromString("English")
      Settings.getLanguageAsString should be("English")
    }
    "create an instance of SettingsOutput" in {
      new SettingsOutput(1000,"German",32,2)
    }
    "fail to load JSON with a wrong directory" in {
      val jsonSettings = JSON()

      val dir: Directory = Directory("C:\\Program Files (x86)\\notexistent")
      jsonSettings.setSettingsDirectory(dir)
      jsonSettings.load() should be(None)
    }
    "fail to save JSON with a wrong directory" in {
      val jsonSettings = JSON()

      val dir: Directory = Directory("C:\\Program Files (x86)\\notexistent")
      jsonSettings.setSettingsDirectory(dir)
      jsonSettings.save(Settings.getGameSpeed, "Deutsch", 32, 4) should be(false)
    }
    "succeed to load XML with the right directory" in {
      val xmlSettings = XML()

      val dir: Directory = Directory(sys.props.apply("user.home")).resolve(".awol").toDirectory
      xmlSettings.setSettingsDirectory(dir)

      xmlSettings.load() should be(Some(SettingsOutput(1000,"German",32,2)))
    }
    "succeed to save XML with the right directory" in {
      val xmlSettings = XML()

      val dir: Directory = Directory(sys.props.apply("user.home")).resolve(".awol").toDirectory
      val originalPath = Path(sys.props.apply("user.home")).resolve(".awol").resolve("settings.xml").toFile
      val readBuf: Array[Char] = Array()
      val originalSettings: String = Source.fromFile(originalPath.path).getLines().mkString

      xmlSettings.setSettingsDirectory(dir)
      xmlSettings.save(Settings.getGameSpeed, "Deutsch", 32, 4) should be(true)

      originalPath.delete()
      originalPath.createFile().writeAll(originalSettings)
    }
    "fail to load XML with a wrong directory" in {
      val xmlSettings = XML()

      val dir: Directory = Directory("C:\\Program Files (x86)\\notexistent")
      xmlSettings.setSettingsDirectory(dir)
      xmlSettings.load() should be(None)
    }
    "fail to create the settings file in a wrong directory" in {
      val xmlSettings = XML()

      val dir: Directory = Directory("C:\\Program Files (x86)\\NuGet")
      xmlSettings.setSettingsDirectory(dir)
      xmlSettings.load() should be(None)
    }
    "fail to load XML with a damaged file" in {
      val xmlSettings = XML()

      val dir: Directory = Directory(sys.props.apply("user.home")).resolve(".awol").toDirectory
      val originalPath = Path(sys.props.apply("user.home")).resolve(".awol").resolve("settings.xml").toFile
      val originalSettings: String = Source.fromFile(originalPath.path).getLines().mkString

      originalPath.delete()
      originalPath.writeAll("laas- a-s -q qeqw 123")

      xmlSettings.setSettingsDirectory(dir)
      xmlSettings.load() should be(None)

      originalPath.delete()
      originalPath.createFile().writeAll(originalSettings)
    }
    "fail to save XML with a wrong directory" in {
      val xmlSettings = XML()

      val dir: Directory = Directory("C:\\Program Files (x86)\\notexistent")
      xmlSettings.setSettingsDirectory(dir)
      xmlSettings.save(2000, "Deutsch", 32, 4) should be(false)
    }
    /*"create an instance of SettingsJSON" in {
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
    }*/
    "save and load all different speed settings" in {
      val originalPath: File = File(sys.props.apply("user.home")).resolve(".awol").resolve("settings.json").toFile
      val settings: String = Source.fromFile(originalPath.path).getLines().mkString
      val dir: Directory = originalPath.parent
      originalPath.delete()

      originalPath.createFile().writeAll("{\"speed\":1000,\"language\":\"German\",\"deckSize\":32,\"playerCount\":4}")
      Settings.loadSettings(dir) should be(None)

      originalPath.createFile().writeAll("{\"speed\":2000,\"language\":\"German\",\"deckSize\":32,\"playerCount\":4}")
      Settings.loadSettings(dir) should be(None)

      originalPath.createFile().writeAll("{\"speed\":3000,\"language\":\"German\",\"deckSize\":32,\"playerCount\":4}")
      Settings.loadSettings(dir) should be(None)

      originalPath.createFile().writeAll("{\"speed\":500000,\"language\":\"German\",\"deckSize\":32,\"playerCount\":4}")
      Settings.loadSettings(dir) should be(None)

      originalPath.delete()
      originalPath.createFile().writeAll(settings)
    }

  }
  "The SettingEnv object" should {
    "be callable" in {
      SettingEnv
    }
  }
}
