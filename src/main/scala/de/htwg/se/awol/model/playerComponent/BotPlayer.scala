package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.cardComponents._
import de.htwg.se.awol.model.environmentComponents._

import scala.collection.mutable.ListBuffer
import scala.util.Random

class BotPlayer(override protected val playerNumber: Int) extends Player {
  // Playing
  override def pickAndDropCard(suitableCards: Map[Int, ListBuffer[Card]]): Option[ListBuffer[Card]] = {
    if (suitableCards.isEmpty) {
      None
    } else {
      // Sort by key and pick the lowest beating cards
      var myPickedCards = suitableCards.toSeq.minBy(_._1)._2

      // Pick only the necessary amount of cards if needed
      if (Game.getActualCardCount > 0) {
        myPickedCards = myPickedCards.slice(0, Game.getActualCardCount)
      }

      // Remove cards from my stack
      removeCardsFromMyStack(myPickedCards)

      Option(myPickedCards)
    }
  }

  override def isHumanPlayer: Boolean = false

  override def pickAndDropCard(pickedCards: ListBuffer[Card]): Option[ListBuffer[Card]] = { None }
}