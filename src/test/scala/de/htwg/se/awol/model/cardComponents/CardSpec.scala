package de.htwg.se.awol.model.cardComponents

import de.htwg.se.awol.model.language.LanguageManager
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CardSpec extends WordSpec with Matchers {
  //LanguageManager.switchLanguage("en")

  "A new Card" should {
    "should have a value of 8 and be the Kreuz" in {
      val card = new Card(8, 0)
      card.cardName should be("8")
      card.colorName should be("Kreuz")
    }
    "should have a value of Bube and be the Herz" in {
      val card = new Card(11, 2)
      card.cardName should be("Bube")
      card.colorName should be("Herz")
    }
    "should have a value of Bube and be unknown" in {
      val card = new Card(11, 5)
      card.cardName should be("Bube")
      card.colorName should be("Die gegebene Kartenfarbe existiert nicht!")
    }
  }
}