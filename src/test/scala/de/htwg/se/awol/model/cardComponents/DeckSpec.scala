package de.htwg.se.awol.model.cardComponents

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.model.languageComponents._
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DeckSpec extends WordSpec with Matchers {
  "A new Deck" should {
    "contain 32 cards" in {
      Settings.setLanguage(LanguageGerman)

      val deck = new Deck()
      deck.getDeckSize should be(32)
    }
    "contain 44 cards when initialized with it" in {
      val deck = new Deck(44)
      deck.getDeckSize should be(44)
    }
    "contain 52 cards when initialized with it" in {
      val deck = new Deck(52)
      deck.getDeckSize should be(52)
    }
    "should throw an exception if the card amount in the deck is not dividable by four" in {
      an [IllegalArgumentException] should be thrownBy new Deck(43)
    }
    "should throw an exception if the card amount is under/overt the allowed amount" in {
      an [IndexOutOfBoundsException] should be thrownBy new Deck(56)
    }
    "return the string representation of the deck in German" in {
      Settings.setLanguage(LanguageGerman)
      val deck = new Deck()

      val testString = deck.toString
      testString should include("Dame")
      testString should include("Karo")
      testString should include("Herz")
    }
  }

  "Switch to english" should {
    "return the string representation of the deck in English" in {
      Settings.setLanguage(LanguageEnglish)
      val deck = new Deck()

      val testString = deck.toString
      testString should include("Queen")
      testString should include("Diamond")
      testString should include("Heart")
    }
  }
}


