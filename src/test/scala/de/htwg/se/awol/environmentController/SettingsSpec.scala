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
    "succeed to save and load XML with the right directory" in {
      val xmlSettings = XML()

      val dir: Directory = Directory(sys.props.apply("user.home")).resolve(".awol").toDirectory
      val originalPath = Path(sys.props.apply("user.home")).resolve(".awol").resolve("settings.xml").toFile
      val originalSettings: String = Source.fromFile(originalPath.path).getLines().mkString

      xmlSettings.setSettingsDirectory(dir)
      xmlSettings.save(3000, "German", 36, 4) should be(true)

      xmlSettings.load() should be(Some(SettingsOutput(3000,"German",36,4)))

      originalPath.delete()
      originalPath.createFile().writeAll(originalSettings)
    }
    "fail to load XML with a wrong directory" in {
      val xmlSettings = XML()

      val dir: Directory = Directory("C:\\Program Files (x86)\\notexistent")
      xmlSettings.setSettingsDirectory(dir)
      xmlSettings.load() should be(None)
    }
    "fail to save XML with a wrong directory" in {
      val xmlSettings = XML()

      val dir: Directory = Directory("C:\\Program Files (x86)\\notexistent")
      xmlSettings.setSettingsDirectory(dir)
      xmlSettings.save(2000, "Deutsch", 32, 4) should be(false)
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
    "save and load all different speed settings" in {
      val originalPath: File = File(sys.props.apply("user.home")).resolve(".awol").resolve("settings.json").toFile
      val settings: String = Source.fromFile(originalPath.path).getLines().mkString
      val dir: Directory = originalPath.parent

      originalPath.delete()

      originalPath.createFile().writeAll("{\"speed\":1000,\"language\":\"German\",\"deckSize\":32,\"playerCount\":4}")
      Settings.loadSettings(dir) should be(None)

      originalPath.writeAll("{\"speed\":2000,\"language\":\"German\",\"deckSize\":32,\"playerCount\":4}")
      Settings.loadSettings(dir) should be(None)

      originalPath.writeAll("{\"speed\":3000,\"language\":\"German\",\"deckSize\":32,\"playerCount\":4}")
      Settings.loadSettings(dir) should be(None)

      originalPath.writeAll("{\"speed\":500000,\"language\":\"German\",\"deckSize\":32,\"playerCount\":4}")
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
