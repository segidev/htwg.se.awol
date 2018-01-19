package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.environmentComponents.{PlayerEnv, SettingEnv}
import de.htwg.se.awol.model.languageComponents._
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

import scala.collection.mutable.ListBuffer

@RunWith(classOf[JUnitRunner])
class BotPlayerSpec extends WordSpec with Matchers {
  "A new Player" should {
    "be the Pöbel when initialized without a rank" in {
      Settings.setLanguage(LanguageGerman)

      val bot: BotPlayer = new BotPlayer(0)
      bot.getRank should be(PlayerEnv.Rank.Mob)
      bot.getRankName should be("Pöbel")
    }
  }

  "A Bot Player" can {
    val bot: BotPlayer = new BotPlayer(0)

    "not use the Human player function" in {
      val notUsable: ListBuffer[Card] = new ListBuffer()
      bot.pickAndDropCard(notUsable) should be(None)
    }
    "change it's rank to be a King and should be the König now" in {
      Settings.setLanguage(LanguageGerman)
      bot.setRank(PlayerEnv.Rank.King)
      bot.getRank should be(PlayerEnv.Rank.King)
      bot.getRankName should be("König")
    }
    "also become an Asshole what makes him an Arschloch" in {
      Settings.setLanguage(LanguageGerman)
      bot.setRank(PlayerEnv.Rank.Asshole)
      bot.getRank should be(PlayerEnv.Rank.Asshole)
      bot.getRankName should be("Arschloch")
    }
    "and should still be the Asshole even when the game is in english now" in {
      Settings.setLanguage(LanguageEnglish)

      bot.getRankName should be("Asshole")
    }
    "have no suitable cards to play" in {
      val noSuitableCards: Map[Int, ListBuffer[Card]] = Map[Int, ListBuffer[Card]]()
      bot.pickAndDropCard(noSuitableCards) should be(None)
    }
    "be able to play" in {
      println("IMPLEMENT!!!!!!!!!!!!!")
    }
  }

  "The rank" should {
    "be reset when requested" in{
      val player: Player = new BotPlayer(0)
      player.setRank(PlayerEnv.Rank.Asshole)
      player.resetRank()
      player.getRank should be(PlayerEnv.Rank.Mob)

    }
  }
}
