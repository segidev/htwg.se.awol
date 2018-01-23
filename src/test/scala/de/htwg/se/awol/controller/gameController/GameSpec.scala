package de.htwg.se.awol.controller.gameController

import de.htwg.se.awol.model.playerComponent.playerBaseImpl.BotPlayer
import de.htwg.se.awol.model.playerComponent.{HumanPlayer, Player}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class GameSpec extends WordSpec with Matchers {
  "The pass counter" should {
    "be able to be set and added to" in {
      Game.setPassCounter(0)
      val passCounter: Int = Game.getPassCounter
      passCounter should be(0)
      Game.addToPassCounter(1)
      Game.getPassCounter should be(1)
    }
  }
  "The Human Player" should {
    "be able to be set" in {
      val human: Player = new HumanPlayer(0)
      Game.setHumanPlayer(new HumanPlayer(0))
      Game.getHumanPlayer should be(human)
    }
  }
  "The Leading Player" should {
    "be able to be set" in {
      val bot: Player = new BotPlayer(1)
      Game.setLeadingPlayer(bot)
      Game.getLeadingPlayer should be(bot)
    }
  }
  "The Active Player" should {
    "be able to be set" in {
      val bot: Player = new BotPlayer(7)
      Game.setActivePlayer(bot)
      Game.getActivePlayer should be(bot)
    }
  }
  "The Actual Card Value" should {
    "be able to be set" in {
      Game.setActualCardValue(5)
      Game.getActualCardValue should be(5)
    }
  }
  "The Actual Card Count" should {
    "be able to be set" in {
      Game.setActualCardCount(3)
      Game.getActualCardCount should be (3)
    }
  }
  "The Game State" should {
    "be able to be set" in {
      Game.setGameState(Game.States.EndOfGame)
      Game.getGameState should be(Game.States.EndOfGame)
    }
  }
  "The minimum and maximum of allowed players" should {
    "be 2 and 8" in {
      Game.getMinPlayers should be(2)
      Game.getMaxPlayers should be(8)
    }
  }
  "The default deck size" should {
    "be 32" in {
      Game.getDefaultDeckSize should be(32)
    }
  }
  "The default player count" should {
    "be 2" in {
      Game.getDefaultPlayerCount should be(4)
    }
  }
}
