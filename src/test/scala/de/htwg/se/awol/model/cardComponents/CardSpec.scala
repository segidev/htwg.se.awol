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

      val card = new Card(CardEnv.Values.Eight, CardEnv.Colors.Clubs)
      card.cardValueName should be("8")
      card.cardValue should be(8)
      card.cardColorName should be("Kreuz")
    }
    "should have a value of Bube and be the Herz" in {
      Settings.setLanguage(LanguageGerman)

      val card = new Card(CardEnv.Values.Jack, CardEnv.Colors.Hearts)
      card.cardValueName should be("Bube")
      card.cardValue should be(11)
      card.cardColorName should be("Herz")
    }
  }

  "A Card" should {
    "have a file name with its image" in {
      val card1 = new Card(CardEnv.Values.Ace, CardEnv.Colors.Clubs)
      val card2 = new Card(CardEnv.Values.Ace, CardEnv.Colors.Hearts)
      val card3 = new Card(CardEnv.Values.Ace, CardEnv.Colors.Diamonds)
      val card4 = new Card(CardEnv.Values.Ace, CardEnv.Colors.Spades)
      card1.cardFilename should be("14_of_clubs")
      card2.cardFilename should be("14_of_hearts")
      card3.cardFilename should be("14_of_diamonds")
      card4.cardFilename should be("14_of_spades")
    }
  }
  "CardEnv" should {
    "be callable" in {
      CardEnv
    }
  }
}


