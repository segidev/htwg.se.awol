package de.htwg.se.awol.model.cardComponents

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.model.environmentComponents.{CardEnv, SettingEnv}
import de.htwg.se.awol.model.languageComponents._
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CardSpec extends WordSpec with Matchers {
  "A new Card" should {
    "should have a value of 8 and be the Kreuz" in {
      Settings.setLanguage(LanguageGerman)

      val card = new Card(CardEnv.Values.Eight, CardEnv.Colors.Clubs)
      card.cardName should be("8")
      card.cardValue should be(8)
      card.cardColorName should be("Kreuz")
    }
    "should have a value of Bube and be the Herz" in {
      Settings.setLanguage(LanguageGerman)

      val card = new Card(CardEnv.Values.Jack, CardEnv.Colors.Hearts)
      card.cardName should be("Bube")
      card.cardValue should be(11)
      card.cardColorName should be("Herz")
    }
  }

  //Settings.setLanguage(SettingsEnvironment.Language.English)
}


