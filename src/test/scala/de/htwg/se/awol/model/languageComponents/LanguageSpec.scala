package de.htwg.se.awol.model.languageComponents

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.environmentComponents.{PlayerEnv, SettingEnv}
import de.htwg.se.awol.model.languageComponents._
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class LanguageSpec extends WordSpec with Matchers {
  "The language Handler" should {
    "be in German by default and return the language representation in this language" in {
      Settings.setLanguage(LanguageGerman)

      Settings.getLanguageCode should be(SettingEnv.Language.German)
      Settings.getLanguageAsString should be("Deutsch")
    }
    "be in English when changed to and return the language representation in this language now" in {
      Settings.setLanguage(LanguageEnglish)

      Settings.getLanguageCode should be(SettingEnv.Language.English)
      Settings.getLanguageAsString should be("English")
    }
    "should return the English message for \"viceasshole\"" in {
      Settings.setLanguage(LanguageEnglish)
      LanguageTranslator.translate(PlayerEnv.Rank.Viceasshole) should be("Viceasshole")
    }
    "should return the German message for \"viceasshole\"" in {
      Settings.setLanguage(LanguageGerman)

      LanguageTranslator.translate(PlayerEnv.Rank.Viceasshole) should be("Vize-Arschloch")
    }
  }

  "All languages" should {
    "have a translation for every key" in {
      LanguageEnglish.translationObjects.foreach(translationGroup => {
        translationGroup.values.foreach(translation => {
          withClue("English Language: ") { LanguageEnglish.translations should contain key translation }
          withClue("German Language: ") { LanguageGerman.translations should contain key translation }
          withClue("Youth Language: ") { LanguageYouth.translations should contain key translation }
        })
      })
    }
  }
}


