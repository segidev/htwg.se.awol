package de.htwg.se.awol.controller.gameController

import de.htwg.se.awol.controller.gameController.gameMockImpl._GameHandler
import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.environmentComponents.CardEnv
import de.htwg.se.awol.model.playerComponent.Player
import de.htwg.se.awol.model.playerComponent.playerBaseImpl.BotPlayer
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

import scala.collection.mutable.ListBuffer

@RunWith(classOf[JUnitRunner])
class MockGameHandlerSpec extends WordSpec with Matchers {
  "MockImpl Functions" should {
    "be callable for Code Coverage" in {
      val bot: Player = new BotPlayer(0)
      val cards: ListBuffer[Card] = ListBuffer(new Card(CardEnv.Values.Ace, CardEnv.Colors.Hearts))
      val controller: _GameHandler = new _GameHandler
      controller.loadSettings()
      controller.initNewGame(0, 0)
      controller.validateNewGame(0, 0)
      controller.setNewGameId()
      controller.callNextActionByState()
      controller.createPlayers()
      controller.handoutCards()
      controller.findStartingPlayer()
      controller.swapCards()
      controller.startNewRound()
      controller.triggerNextPlay(bot)
      controller.doPlay(bot)
      controller.humanPlaying(cards)
      controller.botPlaying(bot)
      controller.setLeadingValues(bot, cards)
      controller.validatePostPlay(bot)
      controller.checkForPlayerFinished(bot)
      controller.checkForEndOfRound()
      controller.evaluateRound()
      controller.summarizeEndOfGame()
      controller.markNextActivePlayer()
      controller.setCardLeadingPlayer(bot)
      controller.addCardsToCardsOnTable(cards)
      controller.clearCardsOnTable()
      controller.getLatestCardsOnTable
      controller.getPlayerList
      controller.getPlayerByIndex(0)
      controller.getPlayerCount
      controller.setPlayerCount(0)
      controller.getGamePausedStatus
      controller.setGamePausedStatus(true)
    }
  }

}
