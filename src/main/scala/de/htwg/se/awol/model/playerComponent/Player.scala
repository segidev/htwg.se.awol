package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.cardComponents.{Card, Deck}
import de.htwg.se.awol.model.environmentComponents.PlayerEnv

import scala.collection.mutable.ListBuffer

trait Player {
  val playerNumber: Int
  var rank: PlayerEnv.Rank.Value = PlayerEnv.Rank.Mob
  var cards: ListBuffer[Card] = ListBuffer()

  def addCard(assignedCard: Card): Unit = {
    cards.append(assignedCard)
    cards = cards.sortBy(_.cardValue)
  }

  def hasCard(findCard: Card): Boolean = cards.contains(findCard)

  def cardAmount: Int = cards.length

  def clearCards(): Unit = cards.clear()

  def getPlayerNumber: Int = playerNumber

  def getRank: PlayerEnv.Rank.Value = rank

  def resetRank(): Unit = rank = PlayerEnv.Rank.Mob

  def getRankName: String = LanguageTranslator.translate(rank)

  def setRank(newRank: PlayerEnv.Rank.Value): Unit = { rank = newRank }

  def swapWith(player: Player): Unit = {
    getRank match {
      case PlayerEnv.Rank.King =>
        for (_ <- 0 until 1) {
          println(getRankName + " receives " + player.cards.last + " from " + player.getRankName)
          println(player.getRankName + " receives " + cards.head + " from " + getRankName)

          this.cards.append(player.cards.remove(player.cards.length - 1))
          this.cards = this.cards.sortBy(_.cardValue)
          player.cards.append(this.cards.remove(0))
          player.cards = player.cards.sortBy(_.cardValue)
        }
      case _ => throw new MatchError("An illegal swap for a rank was tracked.")
    }
  }

  def findSuitableCards(actualCardVal: Int, actualCardCount: Int): Map[Int, ListBuffer[Card]] = {
    if (actualCardCount > 0) {
      val myCards: Map[Int, ListBuffer[Card]] = cards.filter(_.cardValue > actualCardVal).groupBy(_.cardValue).filter(_._2.lengthCompare(actualCardCount) >= 0)
      myCards
    } else {
      println("PICK ANY CARD YOU WANT")
      val myCards: Map[Int, ListBuffer[Card]] = cards.groupBy(_.cardValue)
      myCards
    }
  }

  def removeCardsFromMyStack(removingCards: ListBuffer[Card]): Unit = {
    cards.--=(removingCards)
  }

  def isHumanPlayer: Boolean

  def pickAndDropCard(suitableCards: Map[Int, ListBuffer[Card]]): Option[(Int, Int)]

  override def toString: String = {
    var sb: StringBuilder = new StringBuilder

    sb.append(getRankName)

    if (cards.nonEmpty) {
      sb.append("\n")

      var maxLength = 0
      for(c <- cards) {
        if (c.toString.length > maxLength) {
          maxLength = c.toString.length
        }
      }

      var i: Int = 0
      for (card <- cards) {
        sb.append(card).append(String.format("%" + (maxLength - card.toString.length + 5) + "s", ""))
        if (i % Deck.amountOfColoredEquals == 3) {
          sb.append("\n")
        }

        i += 1
      }
    }

    sb.toString
  }
}
