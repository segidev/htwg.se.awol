package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.model.cardComponents.Card

import scala.collection.mutable.ListBuffer

case class HumanPlayer(override protected val playerNumber: Int) extends Player {

  override def validatePick(pickedCards: ListBuffer[Card], actualCardCount: Int, actualCardValue: Int): Option[ListBuffer[Card]] = {
    if (pickedCards.isEmpty) {
      if (actualCardCount == 0) {
        None
      } else {
        Some(ListBuffer.empty)
      }
    } else {
      val usedCards: ListBuffer[Card] = pickedCards

      if (actualCardCount != 0 && usedCards.lengthCompare(actualCardCount) != 0) {
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

  override def pickFromSuitableCards(suitableCards: Map[Int, ListBuffer[Card]], actualCardCount: Int): ListBuffer[Card] = ListBuffer.empty
}
