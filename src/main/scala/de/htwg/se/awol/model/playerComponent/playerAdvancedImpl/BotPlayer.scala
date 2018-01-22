package de.htwg.se.awol.model.playerComponent.playerAdvancedImpl

import java.util.NoSuchElementException

import de.htwg.se.awol.model.cardComponents._
import de.htwg.se.awol.model.playerComponent.playerBaseImpl.{BotPlayer => BaseBotPlayer}

import scala.collection.mutable.ListBuffer

class BotPlayer(override protected val playerNumber: Int) extends BaseBotPlayer(playerNumber) {
  override def pickFromSuitableCards(suitableCards: Map[Int, ListBuffer[Card]], actualCardCount: Int): ListBuffer[Card] = {
    if (suitableCards.isEmpty) {
      ListBuffer.empty
    } else {
      safeWinScenario(suitableCards, actualCardCount) match {
        case Some(buffer) => buffer
        case _ => ???
      }
      threeStepScenario(suitableCards, actualCardCount) match {
        case Some(buffer) => ???
        case _ => ???
      }

      if (actualCardCount == 0) {
        // Spieler fängt an
        val amount: Int = findHighestPairOccurence(suitableCards, actualCardCount)
        ???
      } else {
        ???
        // Spieler fängt nicht an
      }
    }
  }

  def safeWinScenario(suitableCards: Map[Int, ListBuffer[Card]], actualCardCount: Int) : Option[ListBuffer[Card]] = {
    if (suitableCards.keySet.size == 2) {
      suitableCards.get(suitableCards.keySet.last) match {
        case Some(buffer) => {
          if (buffer.head.cardValue == 14 && buffer.size >= actualCardCount) {
            if (actualCardCount > 0) {
            Some(buffer.take(actualCardCount))
            } else {
              Some(buffer)
            }
          }
        }
        case _ => None
      }
    }
    None
  }


  def threeStepScenario(suitableCards: Map[Int, ListBuffer[Card]], actualCardCount: Int) : Option[ListBuffer[Card]] = {
    if (suitableCards.keySet.size == 3) {
      suitableCards.get(suitableCards.keySet.last) match {
        case Some(buffer1) => {
          if (buffer1.head.cardValue == 14) {
            suitableCards.get(suitableCards.keySet.tail.head) match {
              case Some(buffer2) => {
                if(buffer2.size == actualCardCount) {
                  Some(buffer2.take(actualCardCount))
                } else if (actualCardCount == 0) {
                  Some(buffer2)
                }
              }
              case _ => None
            }
          }
        }
      }
    }
    None
  }

  def findHighestPairOccurence(suitableCards: Map[Int, ListBuffer[Card]], actualCardCount: Int) : Int = {
    2
    // Die Anzahl an Karten spielen, die auf der Hand dominiert. Bei Gleichstand: höhere Anzahl legen
  }
}