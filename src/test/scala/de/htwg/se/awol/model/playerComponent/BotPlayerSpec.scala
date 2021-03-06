package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.environmentComponents.{CardEnv, PlayerEnv}
import de.htwg.se.awol.model.languageComponents._
import de.htwg.se.awol.model.playerComponent.bot.baseImpl.BotPlayer
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

import scala.collection.mutable.ListBuffer

@RunWith(classOf[JUnitRunner])
class BotPlayerSpec extends WordSpec with Matchers {
  "A new Player" should {
    val bot: BotPlayer = new BotPlayer(2)
    "be the Pöbel when initialized without a rank" in {
      Settings.setLanguage(LanguageGerman)

      bot.getRank should be(PlayerEnv.Rank.Mob)
      bot.getRankName should be("Pöbel")
    }
    "with index 2 should have the name Ralph" in {
      bot.getPlayerNameObject should be(PlayerEnv.BotNames.Player_3)
    }
  }

  "A Bot Player" can {
    val bot: BotPlayer = new BotPlayer(0)
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
      bot.pickFromSuitableCards(noSuitableCards, Game.getActualCardCount) should be(ListBuffer())
    }
    "be able to play" in {
      Game.setActualCardCount(1)
      val suitableCard: Card = new Card(CardEnv.Values.Ten, CardEnv.Colors.Diamonds)
      val suitableCards: Map[Int, ListBuffer[Card]] = Map(
        2 -> ListBuffer(suitableCard)
      )
      bot.pickFromSuitableCards(suitableCards, Game.getActualCardCount)
    }
    "not pick no cards if he is starting the round" in {
      val pickedCards: ListBuffer[Card] = ListBuffer.empty
      bot.validatePick(pickedCards, 0, 0) should be(None)
    }
    "not pick less cards than needed" in {
      val card: Card = new Card(CardEnv.Values.Ace, CardEnv.Colors.Clubs)
      val pickedCards: ListBuffer[Card] = ListBuffer(card)
      bot.validatePick(pickedCards, 2, 10) should be(None)
    }
    "not pick cards with lower value than needed" in {
      val card: Card = new Card(CardEnv.Values.Seven, CardEnv.Colors.Clubs)
      val pickedCards: ListBuffer[Card] = ListBuffer(card)
      bot.validatePick(pickedCards, 1, 10) should be(None)
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
