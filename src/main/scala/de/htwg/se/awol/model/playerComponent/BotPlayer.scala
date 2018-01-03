package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.cardComponents._
import de.htwg.se.awol.model.environmentComponents._

import scala.collection.mutable.ListBuffer
import scala.util.Random

class BotPlayer(override val playerNumber: Int) extends Player {
  // Playing
  override def pickAndDropCard(suitableCards: Map[Int, ListBuffer[Card]]): Option[(Int, Int)] = {
    if (suitableCards.isEmpty) {
      None
    } else {
      // Sort by key and pick the lowest beating cards
      var (myValuePick, myPickedCards) = suitableCards.toSeq.sortBy(_._1).head

      // Pick only the necessary amount of cards if needed
      if (Game.getActualCardCount > 0) {
        myPickedCards = myPickedCards.slice(0, Game.getActualCardCount)
      }
      println("I (Bot) used these cards: " + myPickedCards)

      // Remove cards from my stack
      removeCardsFromMyStack(myPickedCards)

      Option(myValuePick, myPickedCards.length)
    }
  }

  override def isHumanPlayer: Boolean = false

}