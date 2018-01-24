package de.htwg.se.awol.model.playerComponent.bot.advancedImpl

import de.htwg.se.awol.model.cardComponents._
import de.htwg.se.awol.model.environmentComponents.CardEnv
import de.htwg.se.awol.model.playerComponent.bot.baseImpl.{BotPlayer => BaseBotPlayer}

import scala.collection.mutable.ListBuffer

class BotPlayer(override protected val playerNumber: Int) extends BaseBotPlayer(playerNumber) {
  override def pickFromSuitableCards(suitableCards: Map[Int, ListBuffer[Card]], actualCardCount: Int): ListBuffer[Card] = {
    if (suitableCards.isEmpty) {
      ListBuffer.empty
    } else {
      safeWinScenario(suitableCards, actualCardCount) match {
        case Some(buffer) => return buffer
        case _ =>
      }
      threeStepScenario(suitableCards, actualCardCount) match {
        case Some(buffer) => return buffer
        case _ =>
      }
      if (actualCardCount == 0) {
        val amount: Int = findHighestTupleOccurence(suitableCards, actualCardCount)
        suitableCards.filter(_._2.size == amount).toSeq.minBy(_._1)._2
      } else {
        val pickedCards = suitableCards.toSeq.minBy(_._1)._2
        pickedCards.take(actualCardCount)
      }
    }
  }

  def safeWinScenario(suitableCards: Map[Int, ListBuffer[Card]], actualCardCount: Int) : Option[ListBuffer[Card]] = {
    if (getCardGroups.size == 2) {
      val buffer: ListBuffer[Card] = suitableCards.toSeq.maxBy(_._1)._2
      if (buffer.head.cardValue == CardEnv.Values.Ace.id && buffer.size >= actualCardCount) {
        if (actualCardCount > 0) {
        return Some(buffer.take(actualCardCount))
        } else {
          return Some(buffer)
        }
      }
    }
    None
  }

  def threeStepScenario(suitableCards: Map[Int, ListBuffer[Card]], actualCardCount: Int) : Option[ListBuffer[Card]] = {
    if (getCardGroups.size == 3) {
      val buffer1: ListBuffer[Card] = suitableCards.toSeq.maxBy(_._1)._2
      if (buffer1.head.cardValue == CardEnv.Values.Ace.id) {
        val buffer2: ListBuffer[Card] = suitableCards.toSeq.sortBy(_._1).tail.head._2
        if(buffer2.size == actualCardCount) {
          Some(buffer2.take(actualCardCount))
        } else if (actualCardCount == 0) {
          Some(buffer2)
        } else {
          None
        }
      } else {
        None
      }
    } else {
      None
    }
  }

  def findHighestTupleOccurence(suitableCards: Map[Int, ListBuffer[Card]], actualCardCount: Int) : Int = {
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
    index + 1
  }
}