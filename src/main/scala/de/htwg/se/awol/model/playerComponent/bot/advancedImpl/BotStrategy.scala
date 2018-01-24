package de.htwg.se.awol.model.playerComponent.bot.advancedImpl

import de.htwg.se.awol.model.cardComponents.Card

import scala.collection.mutable.ListBuffer

trait BotStrategy {
  def executeStrategy() : ListBuffer[Card]
}

class WinStrategy(suitableCards: Map[Int, ListBuffer[Card]], actualCardCount: Int) extends BotStrategy {
  override def executeStrategy(): ListBuffer[Card] = {
    val buffer: ListBuffer[Card] = suitableCards.toSeq.maxBy(_._1)._2
    if (actualCardCount > 0) {
      buffer.take(actualCardCount)
    } else {
      buffer
    }
  }
}

class ThreeStepStrategy(suitableCards: Map[Int, ListBuffer[Card]], actualCardCount: Int) extends BotStrategy {
  override def executeStrategy(): ListBuffer[Card] = {
    val buffer: ListBuffer[Card] = suitableCards.toSeq.sortBy(_._1).tail.head._2
    buffer
  }
}

class AdvancedDefaultStrategy(suitableCards: Map[Int, ListBuffer[Card]], actualCardCount: Int) extends BotStrategy {
  override def executeStrategy(): ListBuffer[Card] = {
    var max: Int = 0
    var index: Int = 0
    val count:Array[Int] = new Array[Int](4)
    suitableCards.valuesIterator.foreach( buffer => {
      count(buffer.size - 1) += 1
    })
    for (i <- 0 until 4) {
      if (count(i) >= max) {
        max = count(i)
        index = i
      }
    }
    val amount: Int = index + 1
    suitableCards.filter(_._2.size == amount).toSeq.minBy(_._1)._2
  }
}

class DefaultStrategy(suitableCards: Map[Int, ListBuffer[Card]], actualCardCount: Int) extends BotStrategy {
  override def executeStrategy(): ListBuffer[Card] = {
    val pickedCards = suitableCards.toSeq.minBy(_._1)._2
    pickedCards.take(actualCardCount)
  }
}
