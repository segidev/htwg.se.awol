package de.htwg.se.awol.view

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.cardComponents.Deck
import de.htwg.se.awol.model.environmentComponents.SettingEnv
import de.htwg.se.awol.model.languageComponents._
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class Prints extends WordSpec with Matchers {
  "A new Deck" should {
    "print in german" in {
      Settings.setLanguage(LanguageGerman)
      val deck = new Deck(32)
    }
    "print in english" in {
      Settings.setLanguage(LanguageEnglish)
      val deck = new Deck(32)
    }
    "print in youth talk" in {
      Settings.setLanguage(LanguageYouth)
      val deck = new Deck(32)
    }
  }

  "Every language" should {
    "return all available languages in its own translation for German" in {
      Settings.setLanguage(LanguageGerman)
      LanguageTranslator.translate(SettingEnv.Language.German) should be("Deutsch")
      LanguageTranslator.translate(SettingEnv.Language.English) should be("Englisch")
      LanguageTranslator.translate(SettingEnv.Language.Youth) should be("Jugendsprache")
    }
    "return all available languages in its own translation for English" in {
      Settings.setLanguage(LanguageEnglish)
      LanguageTranslator.translate(SettingEnv.Language.German) should be("German")
      LanguageTranslator.translate(SettingEnv.Language.English) should be("English")
      LanguageTranslator.translate(SettingEnv.Language.Youth) should be("Youngster Talk")
    }
    "return all available languages in its own translation for Youth talk" in {
      Settings.setLanguage(LanguageYouth)
      LanguageTranslator.translate(SettingEnv.Language.German) should be("Kartoffelgelaber")
      LanguageTranslator.translate(SettingEnv.Language.English) should be("Obamaland")
      LanguageTranslator.translate(SettingEnv.Language.Youth) should be("Sprache vong Oberbabo")
    }
  }

  //Settings.setLanguage(SettingsEnvironment.Language.English)
}


