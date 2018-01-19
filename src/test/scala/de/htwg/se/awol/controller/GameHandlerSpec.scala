package de.htwg.se.awol.controller
import de.htwg.se.awol.controller.gameController.{Game, _GameHandler}
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GameHandlerSpec extends WordSpec with Matchers {
  "Starting a new round" should {
    "not work in the wrong state" in {
      val controller: _GameHandler = new _GameHandler()
      an [AssertionError] should be thrownBy controller.startNewRound()
    }
    "work in the correct state" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 4)
      controller.callNextActionByState
      println(Game.getGameState)
      controller.startNewRound
    }
  }

  "Finding the starting player" should {
    "not work in the wrong state" in {
      val controller: _GameHandler = new _GameHandler()
      an [AssertionError] should be thrownBy controller.findStartingPlayer()
    }
    "work set the player with the starting card as starting player" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 4)
      controller.handoutCards
      controller.findStartingPlayer
    }
    "set the first player in the list without starting card in the game" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(4, 2)
      controller.handoutCards
      controller.findStartingPlayer
    }
  }


  "Handing out cards" should {
    "not work in the wrong state" in {
      val controller: _GameHandler = new _GameHandler()
      an [AssertionError] should be thrownBy controller.handoutCards()
    }
    "should work in the correct state" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 4)
      controller.handoutCards
    }
  }


  "Initializing a new game" should {
    "not throw any exceptions" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 4)
    }
    "exit if playerCount is too high" in {
      val controller: _GameHandler = new _GameHandler()
      an [IllegalArgumentException] should be thrownBy controller.initNewGame(32, 9)
    }
  }



}
