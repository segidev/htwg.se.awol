package de.htwg.se.awol.controller.gameController

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.model.cardComponents.Deck
import de.htwg.se.awol.model.playerComponent.{BotPlayer, HumanPlayer, Player}

import scala.collection.mutable
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

  private val minPlayers: Int = 2
  private val maxPlayers: Int = 8

  private var humanPlayer: HumanPlayer = _
  private var leadingPlayer: Player = _
  private var activePlayer: Player = _

  private var actualGameState: States.Value = _
  private var actualCardCount: Int = 0
  private var actualCardValue: Int = 0
  private var passCounter: Int = 0

  private var defaultDeckSize: Int = Deck.smallCardStackSize
  private var defaultPlayerCount: Int = minPlayers

  def getPassCounter: Int = passCounter
  def addToPassCounter(i: Int): Unit = { passCounter += i }
  def setPassCounter(newPassCounter: Int): Unit = passCounter = newPassCounter

  def getHumanPlayer: Player = humanPlayer
  def setHumanPlayer(player: HumanPlayer): Unit = { humanPlayer = player }

  def getLeadingPlayer: Player = leadingPlayer
  def setLeadingPlayer(newLeadingPlayer: Player): Unit = leadingPlayer = newLeadingPlayer

  def getActivePlayer: Player = activePlayer
  def setActivePlayer(newLeadingPlayer: Player): Unit = activePlayer = newLeadingPlayer

  def getActualCardValue: Int = actualCardValue
  def setActualCardValue(newCardValue: Int): Unit = { actualCardValue = newCardValue }

  def getActualCardCount: Int = actualCardCount
  def setActualCardCount(newCardCount: Int): Unit = {
    actualCardCount = newCardCount
    isPassingAllowed.update(actualCardCount > 0)
  }

  def getGameState: States.Value = actualGameState
  def setGameState(newState: States.Value): Unit = actualGameState = newState

  def getMinPlayers: Int = minPlayers
  def getMaxPlayers: Int = maxPlayers

  def getDefaultDeckSize: Int = defaultDeckSize
  def getDefaultPlayerCount: Int = defaultPlayerCount

  def setGameSettings(newDeckSize: Int, newPlayerCount: Int, doSave: Boolean): Unit = {
    defaultDeckSize = newDeckSize
    defaultPlayerCount = newPlayerCount

    if (doSave) {
      Settings.saveSettingsToJSON()
    }
  }

  def getGameSettings: (Int, Int) = (defaultDeckSize, defaultPlayerCount)
}
