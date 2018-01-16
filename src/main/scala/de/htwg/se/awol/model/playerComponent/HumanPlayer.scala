package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.environmentComponents.CardEnv

import scala.collection.mutable.ListBuffer

case class HumanPlayer(override protected val playerNumber: Int) extends Player {
  def throwMyCardsIntoGame(cardCount: Int, cardValue: Int, pickedCardCount: Int ,pickedCardValue: Int): Unit = {
    println("Amount of card(s): " + pickedCardCount)
    println("Value of card(s): " + pickedCardValue)
    val suitableCards: Map[Int, ListBuffer[Card]] = this.findSuitableCards(cardValue ,cardCount)

    suitableCards.get(pickedCardValue) match {
      case Some(buffer) => if (buffer.size < pickedCardCount) {
        println("You don't have enough card of the value " + pickedCardValue + " on your hand.")
      } else {
        removeCardsFromMyStack(buffer.take(pickedCardCount))
        Game.setActualCardValue(pickedCardValue)
        if (cardCount == 0) {
          Game.setActualCardCount(pickedCardCount)
        }
        Game.setLeadingPlayer(this)
        Game.setPlayerTurn(false)
      }
      case _ => println("You don't have any cards with the given value")
    }
  }

  override def isHumanPlayer: Boolean = true

  override def pickAndDropCard(suitableCards: Map[Int, ListBuffer[Card]]): Option[(Int, ListBuffer[Card])] = ???
}
