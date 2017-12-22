package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.cardComponents._
import de.htwg.se.awol.model.environmentComponents._

import scala.collection.mutable.ListBuffer
import scala.util.Random

class BotPlayer(override val playerNumber: Int) extends Player {
  def removeCards(myCards: ListBuffer[Card], actualCardCount: Int): Option[(Int, Int)] = {
    if (myCards.nonEmpty) {
      var lastCardValue: Int = myCards.head.cardValue

      var usedCards: ListBuffer[Card] = new ListBuffer

      for (card <- myCards) {
        if (card.cardValue == lastCardValue) {
          usedCards.append(card)
        } else {
          usedCards.clear()
          usedCards.append(card)
          lastCardValue = card.cardValue
        }

        if (usedCards.length == actualCardCount) {
          println("I used these cards: " + usedCards)
          cards.--=(usedCards)
          return Option(lastCardValue, actualCardCount)
        }
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

  def swapWith(player: Player): Unit = {
    getRank match {
      case PlayerEnv.Rank.King =>
        for (_ <- 0 until 1) {
          println(getPlayerNumber + " receives " + player.cards(player.cards.length - 1) + " from " + player.getPlayerNumber)
          println("Asshole receives " + cards(0) + " from King")
          this.cards.append(player.cards.remove(player.cards.length - 1))
          this.cards = this.cards.sortBy(_.cardValue)
          player.cards.append(this.cards.remove(0))
          player.cards = player.cards.sortBy(_.cardValue)
        }
      case _ => throw new MatchError("An illegal swap for a rank was tracked.")
    }
  }
}