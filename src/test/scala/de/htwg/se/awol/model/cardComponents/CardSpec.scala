package de.htwg.se.awol.model.cardComponents

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.model.environmentComponents.SettingsEnvironment
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CardSpec extends WordSpec with Matchers {
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
    "should throw an exception if a wrong color index is given" in {
      an [IndexOutOfBoundsException] should be thrownBy new Card(11, 5)
    }
    "should throw an exception if a value lower than 2 is given" in {
      an [IndexOutOfBoundsException] should be thrownBy new Card(1, 2)
    }
    "should throw an exception if a value higher than 14 (Ace) is given" in {
      an [IndexOutOfBoundsException] should be thrownBy new Card(15, 2)
    }
  }

  //Settings.setLanguage(SettingsEnvironment.Language.English)
}


