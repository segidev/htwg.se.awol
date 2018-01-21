package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.model.cardComponents.Card

import scala.collection.mutable.ListBuffer

case class HumanPlayer(override protected val playerNumber: Int) extends Player {
  override def getCardsToDrop(pickedCards: ListBuffer[Card], actualCardCount: Int, actualCardValue: Int): Option[ListBuffer[Card]] = {
    if (pickedCards.isEmpty) {
      if (actualCardCount == 0) {
        None
      } else {
        Some(ListBuffer.empty)
      }
    } else {
      val usedCards: ListBuffer[Card] = pickedCards

      if (actualCardCount != 0 && usedCards.length != actualCardCount) {
        None
      } else if (usedCards.head.cardValue <= actualCardValue) {
        None
      } else {
        removeCardsFromMyStack(usedCards)
        Some(usedCards)
      }
    }
  }

  override def isHumanPlayer: Boolean = true

  override def pickAndDropCard(suitableCards: Map[Int, ListBuffer[Card]]): Option[ListBuffer[Card]] = ???
}
