package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.controller.environmentController.Game
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.cardComponents._
import de.htwg.se.awol.model.environmentComponents._

import scala.collection.mutable.ListBuffer
import scala.util.Random

class Player(private val playerNumber: Int) {
  private var rank: PlayerEnv.Rank.Value = PlayerEnv.Rank.Mob
  private var cards: ListBuffer[Card] = ListBuffer[Card]()

  def addCard(assignedCard: Card): Unit = {
    cards.append(assignedCard)
    cards = cards.sortBy(_.cardValue)
  }

  def hasCard(findCard: Card): Boolean = cards.contains(findCard)

  def cardAmount: Int = cards.length

  def removeCards(myCards: ListBuffer[Card], actualCardCount: Int): Option[(Int, Int)] = {
    if (myCards.nonEmpty) {
      var i: Int = 0
      var lastCardValue: Int = myCards.head.cardValue
      var n: Int = 1

      for (card <- myCards) {
        if (card.cardValue == lastCardValue) {
          i += 1
        } else {
          i = 1
          lastCardValue = card.cardValue
        }

        if (i == actualCardCount) {
          // Hier alle Karten von Index n-i+1 bis Index n entfernen

          println("Removing card: " + cards.apply(n-i))
          cards.remove(n-i, i)
          return Option(lastCardValue, actualCardCount)
        }
        n += 1
      }
    }
    None
  }

  // Playing
  def pickCard(actualCardVal: Int, actualCardCount: Int): Option[(Int, Int)] = {
    if (actualCardCount > 0) {
      val myCards: ListBuffer[Card] = cards.filter(_.cardValue > actualCardVal)
      removeCards(myCards, actualCardCount)
    } else {
      val pickedCard: Card = cards.head
      removeCards(cards, cards.count(_.cardValue == pickedCard.cardValue))
    }
  }

  def getPlayerNumber: Int = playerNumber

  def getRank: PlayerEnv.Rank.Value = rank

  def getRankName: String = LanguageTranslator.translate(rank)

  def setRank(newRank: PlayerEnv.Rank.Value): Unit = { rank = newRank }

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