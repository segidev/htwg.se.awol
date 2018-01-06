package de.htwg.se.awol.controller.gameController

import de.htwg.se.awol.model.cardComponents.{Card, Deck}
import de.htwg.se.awol.model.environmentComponents.CardEnv
import de.htwg.se.awol.model.playerComponent.Player

import scala.collection.mutable.ListBuffer
import scala.swing.Publisher
import scala.util.Random

class _GameHandler() extends Publisher {
  var playerList: ListBuffer[Player] = ListBuffer()
  var playerCount: Int = 2
  var deckSize: Int = 32
  var roundNumber: Int = 1
  var starterCard = Card(CardEnv.Values.Jack, CardEnv.Colors.Diamonds)

  var deck: Deck = _

  var king: Player = _
  var viceroy: Player = _
  var viceasshole: Player = _

  var asshole: Player = _

  def handoutCards(): Unit = {
    var deck = new Deck(Game.getDeckSize)

    // Get the original card stack
    var cardHandOutList: ListBuffer[Card] = deck.getCards

    // Assign every player a random card as long as cards exist
    var i = 0
    while (cardHandOutList.nonEmpty) {
      val player: Player = playerList(i % playerCount)
      val assignedCard: Card = cardHandOutList.remove(Random.nextInt(cardHandOutList.length))

      player.addCard(assignedCard)

      i += 1
    }
  }
}
