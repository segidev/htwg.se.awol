package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.model.language.LanguageManager
import de.htwg.se.awol.model.language.handler.StakeAndPepper
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PlayerSpec extends WordSpec with Matchers {
  "A new Player" should {
    "be the Pöbel when initialized without a rank" in {
      val player = new Player()
      player.rank should be(StakeAndPepper.P_Mob)
      player.rankName should be("Pöbel")
    }
  }

  "A Player" can {
    val player = new Player()

    "change it's rank to be a King and should be the König now" in {
      player.setRank(StakeAndPepper.P_King)
      player.rank should be(StakeAndPepper.P_King)
      player.rankName should be("König")
    }
    "also become an Asshole what makes him an Arschloch" in {
      player.setRank(StakeAndPepper.P_Asshole)
      player.rank should be(StakeAndPepper.P_Asshole)
      player.rankName should be("Arschloch")
    }
    "shall still be the Asshole even when the game is in english now" in {
      LanguageManager.switchLanguage("en")
      player.setRank(StakeAndPepper.P_Asshole)
      player.rank should be(StakeAndPepper.P_Asshole)
      player.rankName should be("Asshole")
    }
  }

  /*"A Player" when { "new" should {
    val player = new Player(1)
    "have a name"  in {
      player.rankName should be("Vize-König")
    }
    "have a nice String representation" in {
      player.toString should be("Vize-König")
    }
  }}*/
}
