package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.environmentComponents.CardEnv

import scala.collection.mutable.ListBuffer

case class HumanPlayer(override protected val playerNumber: Int) extends Player {
  override def pickAndDropCard(pickedCards: ListBuffer[Card]): Option[ListBuffer[Card]] = {
    if (pickedCards.isEmpty) {
      if (Game.getActualCardCount == 0) {
        None
      } else {
        Some(ListBuffer.empty)
      }
    } else {
      val usedCards: ListBuffer[Card] = pickedCards

      if (Game.getActualCardCount > 0) {
        //usedCards = pickedCards.slice(0, Game.getActualCardCount)
      }

      if (Game.getActualCardCount != 0 && usedCards.length != Game.getActualCardCount) {
        None
      } else if (usedCards.head.cardValue <= Game.getActualCardValue) {
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
