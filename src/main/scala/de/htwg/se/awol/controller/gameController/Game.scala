package de.htwg.se.awol.controller.gameController

import de.htwg.se.awol.model.playerComponent.{BotPlayer, HumanPlayer, Player}

import scala.collection.mutable.ListBuffer
import scalafx.beans.property.BooleanProperty

/**
  * *** States ***
  * NewGame: In diesem Zustand wird das Kartendeck erstellt und die Anfangskarte wird festgelegt
  *
  * HandOut: Die Karten werden an die Spieler verteilt und der Spieler der anfangen darf wird festgelegt
  *
  * Playing: Die Spieler werfen ihre Karten in die Arena, dabei sind natürlich Regeln zu beachten.
  *
  * Evaluation: Die Auswertung der Runde. Der Spieler der den Stich gewonnen hat, wird die nächste Runde beginnen. Zurück zu [Playing]
  *
  * EndOfGame: Den Spielern werden ihre jeweiligen Ränge zugewiesen. Das Arschloch wird den ersten Zug machen dürfen. Zurück zu [HandOut]
  *
  * CardSwap: Die Spieler tauschen ihre höchsten/niedrigsten Karten mit den jeweiligen Rängen. Zurück zu [Playing] [Erfordert ein Spieler als Arschloch]
  * *** End of States ***
  */
object Game {

  val isPassingAllowed: BooleanProperty = BooleanProperty(false)

  object States extends Enumeration {
    val NewGame, HandOut, FindStartingPlayer, Playing, Evaluation, EndOfGame, CardSwap = Value
  }

  var humanPlayer: HumanPlayer = _

  private var deckSize: Int = 32
  private var playerList: ListBuffer[Player] = ListBuffer()
  private var playerCount: Int = _

  private var roundNumber: Int = _

  private var actualGameState: States.Value = _
  private var actualCardCount: Int = 0
  private var actualCardValue: Int = 0
  private var leadingPlayer: Player = _
  private var activePlayer: Player = _
  private var passCounter: Int = 0
  private var playerTurn: Boolean = false

  def getPlayerTurn: Boolean = playerTurn
  def setPlayerTurn(newPlayerTurn: Boolean): Unit = playerTurn = newPlayerTurn

  def getPassCounter: Int = passCounter
  def addToPassCounter(i: Int): Unit = { passCounter += i }
  def setPassCounter(newPassCounter: Int): Unit = passCounter = newPassCounter

  def getHumanPlayer: Player = humanPlayer

  def getLeadingPlayer: Player = leadingPlayer
  def setLeadingPlayer(newLeadingPlayer: Player): Unit = leadingPlayer = newLeadingPlayer

  def getActivePlayer: Player = activePlayer
  def setActivePlayer(newLeadingPlayer: Player): Unit = activePlayer = newLeadingPlayer

  def getActualCardValue: Int = actualCardValue
  def setActualCardValue(newCardValue: Int): Unit = actualCardValue = newCardValue

  def getDeckSize: Int = deckSize
  def setDeckSize(size: Int): Unit = { deckSize = size }

  def getPlayerList: ListBuffer[Player] = playerList
  def addPlayerToPlayerlist(player: Player): Unit = playerList.append(player)

  def getPlayerCount: Int = playerCount
  def setPlayerCount(count: Int): Unit = { playerCount = count }

  def getActualCardCount: Int = actualCardCount
  def setActualCardCount(newCardCount: Int): Unit = {
    actualCardCount = newCardCount
    isPassingAllowed.update(actualCardCount > 0)
  }

  def getGameState: States.Value = actualGameState
  def setGameState(newState: States.Value): Unit = actualGameState = newState
}
