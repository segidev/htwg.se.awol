package de.htwg.se.awol.model.playerComponent.bot.advancedImpl

import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.environmentComponents.CardEnv

import scala.collection.mutable.ListBuffer

case class StrategyContext() {

  def getContext(suitableCards: Map[Int, ListBuffer[Card]], cardGroupsHand: Int, actualCardCount: Int): BotStrategy = {

    val highestCards: ListBuffer[Card] = suitableCards.toSeq.maxBy(_._1)._2

    if (
      cardGroupsHand == 2
      && highestCards.head.cardValue == CardEnv.Values.Ace.id
      && highestCards.size >= actualCardCount
    ) {
      new WinStrategy(suitableCards, actualCardCount)
    } else if (
      cardGroupsHand == 3
      && highestCards.head.cardValue == CardEnv.Values.Ace.id
      && (suitableCards.toSeq.sortBy(_._1).tail.head._2.size == actualCardCount
          || actualCardCount == 0)
    ) {
      new ThreeStepStrategy(suitableCards, actualCardCount)
    } else if (actualCardCount == 0) {
      new AdvancedDefaultStrategy(suitableCards, actualCardCount)
    } else {
      new DefaultStrategy(suitableCards, actualCardCount)
    }

  }

}

