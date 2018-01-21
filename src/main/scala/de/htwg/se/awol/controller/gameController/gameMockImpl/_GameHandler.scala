package de.htwg.se.awol.controller.gameController.gameMockImpl

import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.playerComponent.{HumanPlayer, Player}

import scala.collection.mutable.ListBuffer

//noinspection ScalaStyle
class _GameHandler() {

  def loadSettings(): Unit = {}

  def initNewGame(newDeckSize: Int, newPlayerCount: Int): Unit = {}

  def validateNewGame(newDeckSize: Int, newPlayerCount: Int): Unit = {}

  def setNewGameId(): Unit = {}

  def callNextActionByState(): Unit = {}

  def createPlayers(): Unit = {}

  def handoutCards(): Unit = {}

  def findStartingPlayer(): Unit = {}

  def swapCards(): Unit = {}

  def startNewRound(): Unit = {}

  def triggerNextPlay(player: Player): Unit = {}

  def doPlay(player: Player): Unit = {}

  def humanPlaying(pickedCards: ListBuffer[Card]): Option[ListBuffer[Card]] = None // Option(ListBuffer())

  def botPlaying(player: Player): Unit = {}

  def setLeadingValues(player: Player, pickedCards: ListBuffer[Card]): Unit = {}

  def validatePostPlay(player: Player): Unit = {}

  def checkForPlayerFinished(player: Player): Boolean = false

  def checkForEndOfRound(): Boolean = false

  def evaluateRound(): Unit = {}

  def summarizeEndOfGame(): Unit = {}

  // Event Triggers
  def markNextActivePlayer(): Unit = {}

  def setCardLeadingPlayer(player: Player): Unit = {}

  def addCardsToCardsOnTable(pickedCards: ListBuffer[Card]): Unit = {}

  def clearCardsOnTable(): Unit = {}

  // Getter - Setter
  def getLatestCardsOnTable: ListBuffer[Card] = ListBuffer()

  def getPlayerList: ListBuffer[Player] = ListBuffer()

  def getPlayerByIndex(idx: Int): Player = HumanPlayer(0)

  def getPlayerCount: Int = 1
  def setPlayerCount(count: Int): Unit = {}

  def getGamePausedStatus: Boolean = false
  def setGamePausedStatus(bool: Boolean): Unit = {}
}