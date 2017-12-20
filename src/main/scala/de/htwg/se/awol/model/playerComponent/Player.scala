package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.cardComponents.{Card, Deck}
import de.htwg.se.awol.model.environmentComponents.PlayerEnv

import scala.collection.mutable.ListBuffer

class Player(private val playerNumber: Int) {
  private var rank: PlayerEnv.Rank.Value = PlayerEnv.Rank.Mob
  private var cards: ListBuffer[Card] = ListBuffer[Card]()

  def addCard(assignedCard: Card): Unit = cards.append(assignedCard)

  def hasCard(findCard: Card): Boolean = cards.contains(findCard)

  def cardAmount: Int = cards.length

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

      for (i <- cards.indices) {
        sb.append(cards(i)).append(String.format("%" + (maxLength - cards(i).toString.length + 5) + "s", ""))
        if (i % Deck.amountOfColoredEquals == 3) {
          sb.append("\n")
        }
      }
    }

    sb.toString
  }
}