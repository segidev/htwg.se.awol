package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.model.environmentComponents.{PlayerEnvironment, SettingsEnvironment}
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PlayerSpec extends WordSpec with Matchers {
  "A new Player" should {
    "be the Pöbel when initialized without a rank" in {
      val player = new Player()
      player.rank should be(PlayerEnvironment.Rank.Mob)
      player.rankName should be("Pöbel")
    }
  }

  "A Player" can {
    val player = new Player()

    "change it's rank to be a King and should be the König now" in {
      player.setRank(PlayerEnvironment.Rank.King)
      player.rank should be(PlayerEnvironment.Rank.King)
      player.rankName should be("König")
    }
    "also become an Asshole what makes him an Arschloch" in {
      player.setRank(PlayerEnvironment.Rank.Asshole)
      player.rank should be(PlayerEnvironment.Rank.Asshole)
      player.rankName should be("Arschloch")
    }
    "and should still be the Asshole even when the game is in english now" in {
      Settings.setLanguage(SettingsEnvironment.Language.English)

      player.rankName should be("Asshole")
    }
  }
}
