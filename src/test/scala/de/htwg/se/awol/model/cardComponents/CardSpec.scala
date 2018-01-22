package de.htwg.se.awol.model.cardComponents

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.model.environmentComponents.CardEnv
import de.htwg.se.awol.model.languageComponents._
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CardSpec extends WordSpec with Matchers {
  "A new Card" should {
    "should have a value of 8 and be the Kreuz" in {
      Settings.setLanguage(LanguageGerman)

      val card = Card(CardEnv.Values.Eight, CardEnv.Colors.Clubs)
      card.cardValueName should be("8")
      card.cardValue should be(8)
      card.cardColorName should be("Kreuz")
    }
    "should have a value of Bube and be the Herz" in {
      Settings.setLanguage(LanguageGerman)

      val card = Card(CardEnv.Values.Jack, CardEnv.Colors.Hearts)
      card.cardValueName should be("Bube")
      card.cardValue should be(11)
      card.cardColorName should be("Herz")
    }
  }

  "A Card" should {
    "have a file name with its image" in {
      val card = Card(CardEnv.Values.Ace, CardEnv.Colors.Clubs)
      card.cardFilename should be("14_of_clubs")
    }
  }
}


