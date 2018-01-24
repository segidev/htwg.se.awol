package de.htwg.se.awol.controller.gameController

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.model.cardComponents.Deck
import de.htwg.se.awol.model.playerComponent.Player
import de.htwg.se.awol.model.playerComponent.human.HumanPlayer

import scalafx.beans.property.BooleanProperty

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
