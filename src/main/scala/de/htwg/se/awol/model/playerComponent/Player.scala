package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.environmentComponents.PlayerEnv

import scala.collection.mutable.ListBuffer

trait Player  {
  protected val playerNumber: Int = 0

  private var rank: PlayerEnv.Rank.Value = PlayerEnv.Rank.Mob
  private var cards: ListBuffer[Card] = ListBuffer()

  def addCard(assignedCard: Card): Unit = {
    cards.append(assignedCard)
    cards = cards.sortBy(_.cardValue)
  }

  def hasCard(findCard: Card): Boolean = cards.contains(findCard)

  def cardAmount: Int = cards.length

  def clearCards(): Unit = cards.clear()

  def getCards: ListBuffer[Card] = cards

  def getCardGroups: Set[Int] = cards.groupBy(_.cardValue).keySet

  def getPlayerNumber: Int = playerNumber

  def getPlayerNameObject: PlayerEnv.BotNames.Value = PlayerEnv.BotNames.apply(playerNumber)

  def getPlayerName: String = {
    val nameObj = PlayerEnv.BotNames.apply(playerNumber)

    LanguageTranslator.translateWithOption(nameObj) match {
      case Some(c) => c
      case _ => nameObj.toString
    }
  }

  def getRank: PlayerEnv.Rank.Value = rank

  def resetRank(): Unit = rank = PlayerEnv.Rank.Mob

  def getRankName: String = LanguageTranslator.translate(rank)

  def setRank(newRank: PlayerEnv.Rank.Value): Unit = { rank = newRank }

  def swapWith(player: Player): ListBuffer[(Player, Card, Player)] = {
    val swappedList: ListBuffer[(Player, Card, Player)] = ListBuffer()

    getRank match {
      case PlayerEnv.Rank.King =>
        (0 until 1).foreach(_ => {
          swappedList.append((this, player.cards.last, player))
          swappedList.append((player, this.cards.head, this))

          this.cards.append(player.cards.remove(player.cards.length - 1))
          this.cards = this.cards.sortBy(_.cardValue)
          player.cards.append(this.cards.remove(0))
          player.cards = player.cards.sortBy(_.cardValue)
        })
      case _ => throw new MatchError("An illegal swap for a rank was tracked.")
    }

    swappedList
  }

  def findSuitableCards(actualCardVal: Int, actualCardCount: Int): Map[Int, ListBuffer[Card]] = {
    if (actualCardCount > 0) {
      val myCards: Map[Int, ListBuffer[Card]] = cards.filter(_.cardValue > actualCardVal).groupBy(_.cardValue).filter(_._2.lengthCompare(actualCardCount) >= 0)
      myCards
    } else {
      val myCards: Map[Int, ListBuffer[Card]] = cards.groupBy(_.cardValue)
      myCards
    }
  }

  def removeCardsFromMyStack(removingCards: ListBuffer[Card]): Unit = {
    cards.--=(removingCards)
  }

  def pickFromSuitableCards(suitableCards: Map[Int, ListBuffer[Card]], actualCardCount: Int): ListBuffer[Card]
  def validatePick(pickedCards: ListBuffer[Card], actualCardCount: Int, actualCardValue: Int): Option[ListBuffer[Card]]

  def isHumanPlayer: Boolean

  override def toString: String = s"$getPlayerName [$cardAmount card(s) left]"
}
