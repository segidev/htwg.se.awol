package de.htwg.se.awol.model.cardComponents

import de.htwg.se.awol.model.environmentComponents.PlayerEnvironment
import de.htwg.se.awol.model.language.LanguageManager
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class LanguageSpec extends WordSpec with Matchers {
  "The language Handler" should {
    "be in german by default" in {
      LanguageManager.getLanguage should be("de")
    }
    "be in english when changed to" in {
      LanguageManager.switchLanguage(LanguageManager.english)
      LanguageManager.getLanguage should be("en")
    }
    "should return the english message for \"viceasshole\"" in {
      LanguageManager.getTranslation(PlayerEnvironment.P_Viceasshole) should be("Viceasshole")
    }
    "should return the german message for \"viceasshole\"" in {
      LanguageManager.switchLanguage("de")
      LanguageManager.getTranslation(PlayerEnvironment.P_Viceasshole) should be("Vize-Arschloch")
    }
  }

  "All language classes" should {
    "have a translation for a keyword in the EnvironmentHandler" in {
      for (keyWord: PlayerEnvironment.Value <- PlayerEnvironment.values) {
        for (languageClass <- LanguageManager.availableTranslations) {
          withClue(String.format("\"%s\" is missing translation for \"%s\"!", languageClass._1, keyWord.toString)) {
            languageClass._2.messages.contains(keyWord) should be(true)
          }
        }
    }
  }

  /*"All language classes" should {
    "have the same amount and same keywords for translations" in {
      for (a <- LanguageManager.availableTranslations) {
        for (b <- LanguageManager.availableTranslations) {
          withClue(String.format("=> \"%s\" != \"%s\"%n", a._1, b._1)) {a._2.translationCount == b._2.translationCount}

          for(translationEntry <- a._2.messages) {
            val hint = String.format("\"%s\" and \"%s\" have different translations", a._1, b._1)
            withClue(hint) {assert(b._2.messages.contains(translationEntry._1))}
          }
        }
      }
    }*/
  }
}


