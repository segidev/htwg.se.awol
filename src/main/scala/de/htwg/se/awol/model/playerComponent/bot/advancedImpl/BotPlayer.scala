package de.htwg.se.awol.model.playerComponent.bot.advancedImpl

import de.htwg.se.awol.model.cardComponents._
import de.htwg.se.awol.model.playerComponent.bot.baseImpl.{BotPlayer => BaseBotPlayer}

import scala.collection.mutable.ListBuffer

class BotPlayer(override protected val playerNumber: Int) extends BaseBotPlayer(playerNumber) {
  override def pickFromSuitableCards(suitableCards: Map[Int, ListBuffer[Card]], actualCardCount: Int): ListBuffer[Card] = {
    if (suitableCards.isEmpty) {
      ListBuffer.empty
    } else {
      StrategyContext().getContext(suitableCards, getCardGroups.size, actualCardCount).executeStrategy()
    }
  }
}