package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.controller.gameController.Game

case class HumanPlayer(override val playerNumber: Int) extends Player {
  def throwMyCardsIntoGame(pickedCards: String*): Unit = {
    for (cardNumber <- pickedCards.foreach(_.toInt)) {
      hasCard(cardNumber)
    }
    //Game.humanPlayer.hasCard()
  }
}
