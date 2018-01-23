package de.htwg.se.awol.controller.gameController.handler

import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.playerComponent.Player

import scala.collection.mutable.ListBuffer
import scala.swing.Publisher

trait _TGameHandler extends Publisher {
  def getImplType: String

  def loadSettings(): Unit

  def initNewGame(newDeckSize: Int, newPlayerCount: Int): Unit

  def validateNewGame(newDeckSize: Int, newPlayerCount: Int): Unit

  def setNewGameId(): Unit

  def callNextActionByState(): Unit

  def createPlayers(): Unit

  def handoutCards(): Unit

  def findStartingPlayer(): Unit

  def swapCards(): Unit

  def startNewRound(): Unit

  def triggerNextPlay(player: Player): Unit

  def doPlay(player: Player): Unit

  def humanPlaying(pickedCards: ListBuffer[Card]): Option[ListBuffer[Card]]

  def botPlaying(player: Player): Unit

  def setLeadingValues(player: Player, pickedCards: ListBuffer[Card]): Unit

  def validatePostPlay(player: Player): Unit

  def checkForPlayerFinished(player: Player): Boolean

  def checkForEndOfRound(): Boolean

  def evaluateRound(): Unit

  def summarizeEndOfGame(): Unit

  // Event Triggers
  def markNextActivePlayer(): Unit

  def setCardLeadingPlayer(player: Player): Unit

  def addCardsToCardsOnTable(pickedCards: ListBuffer[Card]): Unit

  def clearCardsOnTable(): Unit

  // Getter - Setter
  def getLatestCardsOnTable: ListBuffer[Card]

  def getPlayerList: ListBuffer[Player]

  def getPlayerByIndex(idx: Int): Player

  def getPlayerCount: Int
  def setPlayerCount(count: Int): Unit

  def getGamePausedStatus: Boolean
  def setGamePausedStatus(bool: Boolean): Unit
}
