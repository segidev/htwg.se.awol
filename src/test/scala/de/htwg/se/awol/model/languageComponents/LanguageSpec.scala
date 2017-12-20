package de.htwg.se.awol.model.cardComponents

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.environmentComponents.{PlayerEnvironment, SettingsEnvironment}
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class LanguageSpec extends WordSpec with Matchers {
  "The language Handler" should {
    "be in German by default and return the language representation in this language" in {
      Settings.setLanguage(SettingsEnvironment.Language.German)

      Settings.getLanguage should be(SettingsEnvironment.Language.German)
      Settings.getLanguageAsString should be("Deutsch")
    }
    "be in English when changed to and return the language representation in this language now" in {
      Settings.setLanguage(SettingsEnvironment.Language.English)

      Settings.getLanguage should be(SettingsEnvironment.Language.English)
      Settings.getLanguageAsString should be("English")
    }
    "should return the English message for \"viceasshole\"" in {
      LanguageTranslator.translate(PlayerEnvironment.Rank.Viceasshole) should be("Viceasshole")
    }
    "should return the German message for \"viceasshole\"" in {
      Settings.setLanguage(SettingsEnvironment.Language.German)

      LanguageTranslator.translate(PlayerEnvironment.Rank.Viceasshole) should be("Vize-Arschloch")
    }
  }

  // TODO Finde einen guten Test hierfür
  /*"All language classes" should {
    "have a translation for a keyword in the LanguageHandler" in {
      for (keyWord: StakeAndPepper.Value <- StakeAndPepper.values) {
        for (languageClass <- LanguageManager.availableTranslations) {
          withClue(String.format("\"%s\" is missing translation for \"%s\"!", languageClass._1, keyWord.toString)) {
            languageClass._2.messages.contains(keyWord) should be(true)
          }
        }
    }
  }*/
}


