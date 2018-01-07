package de.htwg.se.awol.controller.gameController

import de.htwg.se.awol.model.playerComponent.{BotPlayer, HumanPlayer, Player}

import scala.collection.mutable.ListBuffer

/**
  * *** States ***
  * NewGame: In diesem Zustand wird das Kartendeck erstellt und die Anfangskarte wird festgelegt
  * HandOut: Die Karten werden an die Spieler verteilt und der Spieler der anfangen darf wird festgelegt
  *
  * Playing: Die Spieler werfen ihre Karten in die Arena, dabei sind natürlich Regeln zu beachten.
  *
  * *** CardConditions ***
  * One: Es wurde eine Karte gelegt, die anderen Spieler müssen mit genau einer höheren Karte den Stich machen oder passen.
  * Two: Es wurden zwei Karten gelegt, die anderen Spieler müssen mit genau zwei höheren, gleichen Karten den Stich machen oder passen.
  * Three: Es wurden drei Karten gelegt, die anderen Spieler müssen mit genau drei höheren, gleichen Karten den Stich machen oder passen.
  * Four: Es wurden vier Karten gelegt, die anderen Spieler müssen mit genau vier höheren, gleichen Karten den Stich machen oder passen.
  * *** End of CardConditions ***
  *
  * Evaluation: Die Auswertung der Runde. Der Spieler der den Stich gewonnen hat, wird die nächste Runde beginnen. Zurück zu [Playing]
  *
  * EndOfGame: Den Spielern werden ihre jeweiligen Ränge zugewiesen. Das Arschloch wird den ersten Zug machen dürfen. Zurück zu [HandOut]
  * CardSwap: Die Spieler tauschen ihre höchsten/niedrigsten Karten mit den jeweiligen Rängen. Zurück zu [Playing] [Erfordert ein Spieler als Arschloch]
  * *** End of States ***
  */
object Game {
  // TODO Vielleicht muss man hier noch sicherstellen, dass die States in gewisser Reihenfolge aufgerufen werden!
  object States extends Enumeration {
    val NewGame, HandOut, Playing, Evaluation, EndOfGame, CardSwap = Value
  }

  var humanPlayer: HumanPlayer = _

  private var deckSize: Int = 32
  private var playerList: ListBuffer[Player] = ListBuffer()
  private var playerCount: Int = _

  private var roundNumber: Int = _

  private var actualGameState: States.Value = States.NewGame
  private var actualCardCount: Int = 0
  private var actualCardValue: Int = 0
  private var activePlayer: Player = _
  private var passCounter: Int = 0
  private var playerTurn: Boolean = false

  def getPlayerTurn: Boolean = playerTurn
  def setPlayerTurn(newPlayerTurn: Boolean): Unit = playerTurn = newPlayerTurn

  def getPassCounter: Int = passCounter
  def setPassCounter(newPassCounter: Int): Unit = passCounter = newPassCounter

  def getActivePlayer: Player = activePlayer
  def setActivePlayer(newActivePlayer: Player): Unit = activePlayer = newActivePlayer

  def getActualCardValue: Int = actualCardValue
  def setActualCardValue(newCardValue: Int): Unit = actualCardValue = newCardValue

  def getDeckSize: Int = deckSize
  def setDeckSize(size: Int): Unit = { deckSize = size }

  def getPlayerList: ListBuffer[Player] = playerList
  def addPlayerToPlayerlist(player: Player): Unit = playerList.append(player)

  def getPlayerCount: Int = playerCount
  def setPlayerCount(count: Int): Unit = { playerCount = count }

  def getActualCardCount: Int = actualCardCount
  def setActualCardCount(newCardCount: Int): Unit = { actualCardCount = newCardCount }

  def getGameState: States.Value = actualGameState
  def setGameState(newState: States.Value): Unit = actualGameState = newState
}
