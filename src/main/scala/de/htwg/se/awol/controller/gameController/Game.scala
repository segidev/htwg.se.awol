package de.htwg.se.awol.controller.gameController

import de.htwg.se.awol.model.playerComponent.{BotPlayer, HumanPlayer, Player}

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

  object CardStates extends Enumeration {
    val Undefined = Value(0)
    val One = Value(1)
    val Two = Value(2)
    val Three = Value(3)
    val Four = Value(4)
  }

  var humanPlayer: Player = _
  private var actualGameState: States.Value = States.NewGame
  private var actualCardState: CardStates.Value = CardStates.Undefined
  private var actualCardCount: Int = 0

  def getActualCardCount: Int = actualCardCount
  def setActualCardCount(newCardCount: Int): Unit = { actualCardCount = newCardCount }

  def getGameState: States.Value = actualGameState
  def setGameState(newState: States.Value): Unit = actualGameState = newState

  def getCardState: CardStates.Value = actualCardState
  def setCardState(newState: CardStates.Value): Unit = actualCardState = newState
}
