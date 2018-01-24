package de.htwg.se.awol.controller.gameController

import de.htwg.se.awol.controller.gameController.handler.gameBaseImpl._GameHandler
import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.environmentComponents.CardEnv
import de.htwg.se.awol.model.playerComponent.Player
import org.junit.runner.RunWith
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.junit.JUnitRunner
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{FunSuite, Matchers, WordSpec}

import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.language.postfixOps
import scala.reflect.io.Path

@RunWith(classOf[JUnitRunner])
class GameHandlerSpec extends WordSpec with Matchers with ScalaFutures {
  "Starting a new round" should {
    "not work in the wrong state" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 4)
      an [AssertionError] should be thrownBy controller.startNewRound()
    }
    "work in the correct state" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 4)
      controller.callNextActionByState()
      controller.startNewRound()
    }
  }


  "Evaluating a round" should {
    "not work in the wrong state" in {
      val controller: _GameHandler = new _GameHandler()
      an [AssertionError] should be thrownBy controller.evaluateRound
    }
    "should work in the correct state" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 4)
      Game.setGameState(Game.States.Evaluation)
      controller.callNextActionByState()
    }
  }


  "Finding the starting player" should {
    "not work in the wrong state" in {
      val controller: _GameHandler = new _GameHandler()
      an [AssertionError] should be thrownBy (controller findStartingPlayer())
    }
    "work set the player with the starting card as starting player" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 4)
      controller.handoutCards()
      controller findStartingPlayer()
    }
    "set the first player in the list without starting card in the game" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 2)
      controller.handoutCards()
      controller findStartingPlayer()
    }
  }


  "Handing out cards" should {
    "not work in the wrong state" in {
      val controller: _GameHandler = new _GameHandler()
      an [AssertionError] should be thrownBy (controller handoutCards())
    }
    "should work in the correct state" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 4)
      controller.handoutCards()
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

  "A game" should {
    "be paused if isGamePaused is set true" in {
      val controller: _GameHandler = new _GameHandler()
      controller.setGamePausedStatus(true)
      controller.getGamePausedStatus should be(true)
    }
    "be continued if isGamePaused is set false" in {
      val controller: _GameHandler = new _GameHandler()
      controller.setGamePausedStatus(false)
      controller.getGamePausedStatus should be(false)
    }
  }

  "Setting the player count" should {
    "work as intended" in {
      val controller: _GameHandler = new _GameHandler()
      controller.setPlayerCount(4)
      controller.getPlayerCount should be(4)
    }
  }

  "The playerlist" should {
    "match the initialized players" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 2)
      val playerList: ListBuffer[Player] = new ListBuffer()
      controller.getPlayerList.foreach(player => {
        playerList.append(player)
      })
      controller.getPlayerList should be(playerList)
    }
  }


  "Swapping cards" should {
    "not work in the wrong state" in {
      val controller: _GameHandler = new _GameHandler()
      an [AssertionError] should be thrownBy controller.swapCards()
    }
    "can't be executed without set ranks" in {
      val controller: _GameHandler = new _GameHandler()
      Game.setGameState(Game.States.CardSwap)
      an [RuntimeException] should be thrownBy controller.callNextActionByState()
    }
    "work after setting king and asshole rank" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 2)
      controller.callNextActionByState()
      controller.callNextActionByState()
      Game.setGameState(Game.States.CardSwap)
      val playerList: ListBuffer[Player] = controller.getPlayerList
      controller.setKing(Option(playerList.head))
      controller.setAsshole(Option(playerList.last))
      controller.callNextActionByState()
    }
  }

  "End of game summarization" should {
    "not work if there is more than one active player" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32,2)
      an [AssertionError] should be thrownBy controller.summarizeEndOfGame()
    }
    "work if there is only one active player left" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 2)
      controller.callNextActionByState()
      controller.callNextActionByState()
      Game.setGameState(Game.States.EndOfGame)
      controller.appendAssholeToRankedList()
      controller.callNextActionByState()
    }
    "change swap cards to be true" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 2)
      controller.callNextActionByState()
      controller.callNextActionByState()
      Game.setGameState(Game.States.EndOfGame)
      controller.appendAssholeToRankedList()
      controller.callNextActionByState()
      Game.setGameState(Game.States.FindStartingPlayer)
      controller.findStartingPlayer()
    }
  }


  "Cards on the table" should {
    "contain the latest added card" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 2)
      val card: Card = new Card(CardEnv.Values.Ace, CardEnv.Colors.Diamonds)
      controller.addCardsToCardsOnTable(ListBuffer(card))
      controller.getLatestCardsOnTable should be(ListBuffer(card))
    }
    "be empty after clearing" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 2)
      val card: Card = new Card(CardEnv.Values.Ace, CardEnv.Colors.Diamonds)
      controller.addCardsToCardsOnTable(ListBuffer(card))
      controller.clearCardsOnTable()
      controller.getLatestCardsOnTable should be(ListBuffer())
    }
  }

  "Setting asshole or king" should {
    "not work if there is no player input" in {
      val controller: _GameHandler = new _GameHandler()
      controller.setKing(None)
      controller.setAsshole(None)
    }
  }

  "Check for end of round" should {
    "be true and evaluate if more than one player is left" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 2)
      controller.callNextActionByState()
      controller.callNextActionByState()
      Game.addToPassCounter(1)
      controller.checkForEndOfRound() should be(true)
    }
    "be true and end game if only one player is left"in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 2)
      controller.callNextActionByState()
      controller.callNextActionByState()
      Game.addToPassCounter(1)
      controller.appendAssholeToRankedList()
      controller.checkForEndOfRound() should be(true)
    }
    "be false if the round has not ended yet" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 2)
      controller.callNextActionByState()
      controller.callNextActionByState()
      controller.checkForEndOfRound() should be(false)
    }
  }

  "A player" should {
    "be finished if he doesn't have any cards left" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32,2)
      val player: Player = controller.getPlayerList.head
      player.getCards.foreach(card => {
        player.validatePick(ListBuffer(card), Game.getActualCardCount, Game.getActualCardValue)
      })
      controller.checkForPlayerFinished(player) should be(true)
    }
    "be still playing if he has cards on his hand" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32,2)
      controller.callNextActionByState()
      controller.callNextActionByState()
      controller.checkForPlayerFinished(controller.getPlayerList.head) should be(false)
    }
  }

  "Post play" should {
    "not be validated if the end of round has not been reached" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32,2)
      controller.callNextActionByState()
      controller.callNextActionByState()
      controller.validatePostPlay(controller.getPlayerList.head)
    }
    "be validated if the end of round has been reached" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32,2)
      controller.callNextActionByState()
      controller.callNextActionByState()
      val player: Player = controller.getPlayerList.head
      player.getCards.foreach(card => {
        player.validatePick(ListBuffer(card), Game.getActualCardCount, Game.getActualCardValue)
      })
      Game.addToPassCounter(1)
      controller.validatePostPlay(player)
    }
  }

  "Leading values" should {
    "be set properly" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 2)
      val card: Card = new Card(CardEnv.Values.Ace, CardEnv.Colors.Diamonds)
      controller.setLeadingValues(controller.getPlayerList.head, ListBuffer(card))
    }
  }

  "A Player" should {
    "be playing if he is still an active player" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 4)
      val player: Player = controller.getPlayerList.tail.head
      controller.doPlay(player)
    }
    "increases the pass counter if he has already won" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 4)
      controller.callNextActionByState()
      controller.callNextActionByState()
      val player: Player = controller.getPlayerList.head
      controller.appendAssholeToRankedList()
      controller.doPlay(player)
    }
  }

  "A human player" can {
    "refuse to choose cards" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 4)
      controller.humanPlaying(ListBuffer())
    }
    "choose cards that he doesn't own" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 4)
      controller.callNextActionByState()
      val card: Card = new Card(CardEnv.Values.Eight, CardEnv.Colors.Diamonds)
      controller.humanPlaying(ListBuffer(card))
    }
    "choose to pass" in {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 4)
      Game.setActualCardCount(1)
      controller.humanPlaying(ListBuffer())
    }
  }

  "A bot player" can {
      val controller: _GameHandler = new _GameHandler()
      controller.initNewGame(32, 4)
      controller.callNextActionByState()
      controller.callNextActionByState()
      val player: Player = controller.getPlayerList.tail.head
    "play suitable cards" in {
      controller.botPlaying(player)
    }
    "pass" in {
      Game.setActualCardValue(14)
      Game.setActualCardCount(0)
      controller.botPlaying(player)
    }
  }

  "Loading Settings" should {
    "publish an event if it fails" in {
      val controller: _GameHandler = new _GameHandler()
      val originalPath: Path = Path(sys.props.apply("user.home")).resolve(".awol").resolve("settings.json").toFile
      var originalSettings: String = ""
      if (originalPath.exists) originalSettings = Source.fromFile(originalPath.path).getLines().mkString

      originalPath.delete()
      originalPath.createFile().writeAll("[asdsadasd, asqwe - asd}")
      controller.loadSettings()

      originalPath.delete()
      if (!originalSettings.isEmpty) {
        originalPath.createFile().writeAll(originalSettings)
      }
    }
    "work fine if the path is correct" in {
      val controller: _GameHandler = new _GameHandler()
      controller.loadSettings()
    }
  }
  "create a gameID of type double" in {
    val controller: _GameHandler = new _GameHandler()
    controller.initNewGame(32,4)
    controller.getGameId.getClass
  }
  "have the Impl type Base Impl" in {
    val controller: _GameHandler = new _GameHandler()
    controller.getImplType should be("Base _GameHandler implementation")
  }
}

@RunWith(classOf[JUnitRunner])
class GameHandlerFutureSpec extends FunSuite with Matchers with ScalaFutures {
  implicit val defaultPatience = PatienceConfig(timeout = Span(6, Seconds), interval = Span(3, Seconds))

  test("A valid message should be returned to a valid name") {
    val controller: _GameHandler = new _GameHandler()
    controller.initNewGame(32, 4)
    controller.callNextActionByState()
    controller.triggerNextPlay(controller.getPlayerList.last) match {
      case Some(f) =>
        whenReady(f) { result =>
          result.leftSideValue shouldBe controller.getGameId
        }
        case _=>
    }

    controller.triggerNextPlay(controller.getPlayerList.last) match {
      case Some(f) =>
        whenReady(f) { result =>
          //result.leftSideValue shouldBe controller.getGameId
        }
        case _=>
    }
    controller.initNewGame(32, 4)
  }
}
