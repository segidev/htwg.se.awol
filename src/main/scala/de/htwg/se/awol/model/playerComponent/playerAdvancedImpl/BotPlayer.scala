package de.htwg.se.awol.model.playerComponent.playerAdvancedImpl

import de.htwg.se.awol.model.cardComponents._
import de.htwg.se.awol.model.playerComponent.playerBaseImpl.{BotPlayer => BaseBotPlayer}

import scala.collection.mutable.ListBuffer

class BotPlayer(override protected val playerNumber: Int) extends BaseBotPlayer(playerNumber) {
  override def pickFromSuitableCards(suitableCards: Map[Int, ListBuffer[Card]], actualCardCount: Int): ListBuffer[Card] = ???
}