package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.model.environmentComponents.{PlayerEnv, SettingEnv}
import de.htwg.se.awol.model.languageComponents._
import de.htwg.se.awol.model.playerComponent.playerBaseImpl.BotPlayer
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BotPlayerSpec extends WordSpec with Matchers {
  "A new Player" should {
    "be the Pöbel when initialized without a rank" in {
      Settings.setLanguage(LanguageGerman)

      val player = new BotPlayer(0)
      player.getRank should be(PlayerEnv.Rank.Mob)
      player.getRankName should be("Pöbel")
    }
  }

  "A Player" can {
    val player = new BotPlayer(0)

    "change it's rank to be a King and should be the König now" in {
      Settings.setLanguage(LanguageGerman)
      player.setRank(PlayerEnv.Rank.King)
      player.getRank should be(PlayerEnv.Rank.King)
      player.getRankName should be("König")
    }
    "also become an Asshole what makes him an Arschloch" in {
      Settings.setLanguage(LanguageGerman)
      player.setRank(PlayerEnv.Rank.Asshole)
      player.getRank should be(PlayerEnv.Rank.Asshole)
      player.getRankName should be("Arschloch")
    }
    "and should still be the Asshole even when the game is in english now" in {
      Settings.setLanguage(LanguageEnglish)

      player.getRankName should be("Asshole")
    }
  }
}
