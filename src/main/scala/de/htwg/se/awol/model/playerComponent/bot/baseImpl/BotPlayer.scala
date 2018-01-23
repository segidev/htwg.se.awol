package de.htwg.se.awol.model.playerComponent.bot.baseImpl

import de.htwg.se.awol.model.cardComponents._
import de.htwg.se.awol.model.playerComponent.Player

import scala.collection.mutable.ListBuffer

class BotPlayer(override protected val playerNumber: Int) extends Player {
  override def pickFromSuitableCards(suitableCards: Map[Int, ListBuffer[Card]], actualCardCount: Int): ListBuffer[Card] = {
    if (suitableCards.isEmpty) {
      ListBuffer.empty
    } else {
      var pickedCards = suitableCards.toSeq.minBy(_._1)._2

      if (actualCardCount > 0) {
        pickedCards = pickedCards.take(actualCardCount)
      }

      pickedCards
    }
  }

  override def validatePick(pickedCards: ListBuffer[Card], actualCardCount: Int, actualCardValue: Int): Option[ListBuffer[Card]] = {
    if (pickedCards.isEmpty) {
      if (actualCardCount == 0) {
        None
      } else {
        Some(ListBuffer.empty)
      }
    } else {
      if (actualCardCount != 0 && pickedCards.lengthCompare(actualCardCount) != 0) {
        None
      } else if (pickedCards.head.cardValue <= actualCardValue) {
        None
      } else {
        removeCardsFromMyStack(pickedCards)
        Some(pickedCards)
      }
    }
  }

  override def isHumanPlayer: Boolean = false
}