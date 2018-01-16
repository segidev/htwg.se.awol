package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.environmentComponents.CardEnv

import scala.collection.mutable.ListBuffer

case class HumanPlayer(override protected val playerNumber: Int) extends Player {
  override def pickAndDropCard(pickedCardCount: Int, pickedCardValue: Int): Boolean = {
    println("Amount of card(s): " + pickedCardCount)
    println("Value of card(s): " + pickedCardValue)
    if (pickedCardValue <= Game.getActualCardValue) {
      println("Card value below/equal the actual one")
      false
    } else if (Game.getActualCardCount != 0 && pickedCardCount != Game.getActualCardCount) {
      println("Amount of cards doesn't match actual of " + Game.getActualCardCount)
      false
    } else {
      Game.setActualCardValue(pickedCardValue)
      if (Game.getActualCardCount == 0) {
        Game.setActualCardCount(pickedCardCount)
      }
      true
    }

  }

  override def isHumanPlayer: Boolean = true

  override def pickAndDropCard(suitableCards: Map[Int, ListBuffer[Card]]): Option[(Int, ListBuffer[Card])] = ???
}
