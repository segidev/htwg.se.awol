package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.environmentComponents.CardEnv

import scala.collection.mutable.ListBuffer

case class HumanPlayer(override val playerNumber: Int) extends Player {
  def throwMyCardsIntoGame(pickedCards: String*): Unit = {
    for (cardNumber <- pickedCards) {
      //new Card(CardEnv.Values.apply(cardNumber.toInt), CardEnv.Colors.)
      //hasCard(cardNumber)
    }
    //Game.humanPlayer.hasCard()
  }

  override def isHumanPlayer: Boolean = true

  override def pickAndDropCard(suitableCards: Map[Int, ListBuffer[Card]]): Option[(Int, Int)] = ???
}
