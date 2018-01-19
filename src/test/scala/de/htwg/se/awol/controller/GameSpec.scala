package de.htwg.se.awol.controller

import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.model.playerComponent.{BotPlayer, HumanPlayer, Player}
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GameSpec extends WordSpec with Matchers {
  "The pass counter" should {
    "be able to be set and added to" in {
      val passCounter: Int = Game.getPassCounter
      passCounter should be(0)
      Game.addToPassCounter(1)
      Game.getPassCounter should be(1)
      Game.setPassCounter(5)
      Game.getPassCounter should be(5)
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
}