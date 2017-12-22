package de.htwg.se.awol.model.cardComponents

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
      LanguageTranslator.translate(PlayerEnv.Rank.Viceasshole) should be("Viceasshole")
    }
    "should return the German message for \"viceasshole\"" in {
      Settings.setLanguage(LanguageGerman)

      LanguageTranslator.translate(PlayerEnv.Rank.Viceasshole) should be("Vize-Arschloch")
    }
  }

  /*"All language classes" should {
    "have the same amount of translations" in {
      for (keyWord: StakeAndPepper.Value <- StakeAndPepper.values) {
        for (languageClass <- LanguageManager.availableTranslations) {
          withClue(String.format("\"%s\" is missing translation for \"%s\"!", languageClass._1, keyWord.toString)) {
            languageClass._2.messages.contains(keyWord) should be(true)
          }
        }
    }
  }*/
}


