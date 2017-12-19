package de.htwg.se.awol.model.cardComponents

import de.htwg.se.awol.model.language.LanguageManager
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DeckSpec extends WordSpec with Matchers {
  //LanguageManager.switchLanguage("en")

  "A new Deck" should {
    "contain 32 cards" in {
      val deck = new Deck()
      println(deck)
      deck.size should be(32)
    }
    "contain 44 cards when initialized with it" in {
      val deck = new Deck(44)
      println(deck)
      deck.size should be(44)
    }
    "contain 52 cards when initialized with it" in {
      val deck = new Deck(52)
      println(deck)
      deck.size should be(52)
    }
    "fail because it's not dividable by four " in {
      val deck = new Deck(43)
      deck.size should be(0)
    }
    "fail because less cards then allowed are defined " in {
      val deck = new Deck(28)
      deck.size should be(0)
    }
    "fail because more cards then allowed are defined " in {
      val deck = new Deck(56)
      deck.size should be(0)
    }
  }
}


