package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.environmentComponents.CardEnv
import de.htwg.se.awol.model.playerComponent.bot.advancedImpl.BotPlayer
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

import scala.collection.mutable.ListBuffer

@RunWith(classOf[JUnitRunner])
class AdvancedBotPlayerSpec extends WordSpec with Matchers {
  "An Advanced Bot Player" should {
    val lowCard: Card = new Card(CardEnv.Values.Eight, CardEnv.Colors.Clubs)
    val middleCard: Card = new Card(CardEnv.Values.Ten, CardEnv.Colors.Clubs)
    val middleCard2: Card = new Card(CardEnv.Values.Jack, CardEnv.Colors.Diamonds)
    val middleCard3: Card = new Card(CardEnv.Values.Jack, CardEnv.Colors.Hearts)
    val highCard: Card = new Card(CardEnv.Values.Ace, CardEnv.Colors.Clubs)
    val highCard2: Card = new Card(CardEnv.Values.King, CardEnv.Colors.Clubs)
    "not pick any cards if there are no suitable cards" in {
      val bot: BotPlayer = new BotPlayer(0)
      val noSuitableCards: Map[Int, ListBuffer[Card]] = Map[Int, ListBuffer[Card]]()
      bot.pickFromSuitableCards(noSuitableCards, 0) should be(ListBuffer.empty)
    }
    "pick the winning strategy if he is able to win" in {
      val bot: BotPlayer = new BotPlayer(0)
      bot.addCard(lowCard)
      bot.addCard(highCard)
      val suitableCards: Map[Int, ListBuffer[Card]] = Map(
        8 -> ListBuffer(lowCard),
        14 -> ListBuffer(highCard)
      )
      Game.setActualCardCount(0)
      bot.pickFromSuitableCards(suitableCards, Game.getActualCardCount) should be(ListBuffer(highCard))
      Game.setActualCardCount(1)
      bot.pickFromSuitableCards(suitableCards, Game.getActualCardCount) should be(ListBuffer(highCard))
    }
    "pick the three step strategy if he is able to" in {
      val bot: BotPlayer = new BotPlayer(0)
      bot.addCard(lowCard)
      bot.addCard(middleCard)
      bot.addCard(highCard)
      val suitableCards: Map[Int, ListBuffer[Card]] = Map(
        8 -> ListBuffer(lowCard),
        10 -> ListBuffer(middleCard),
        14 -> ListBuffer(highCard)
      )
      Game.setActualCardCount(0)
      bot.pickFromSuitableCards(suitableCards, Game.getActualCardCount) should be(ListBuffer(middleCard))
      Game.setActualCardCount(1)
      bot.pickFromSuitableCards(suitableCards, Game.getActualCardCount) should be(ListBuffer(middleCard))
    }
    "play a card tuple which he has the most tuple occurences of" in {
      val bot: BotPlayer = new BotPlayer(0)
      bot.addCard(lowCard)
      bot.addCard(middleCard)
      bot.addCard(middleCard2)
      bot.addCard(middleCard3)
      bot.addCard(highCard)
      val suitableCards: Map[Int, ListBuffer[Card]] = Map(
        8 -> ListBuffer(lowCard),
        10 -> ListBuffer(middleCard),
        11 -> ListBuffer(middleCard2, middleCard3),
        14 -> ListBuffer(highCard)
      )
      Game.setActualCardCount(0)
      bot.pickFromSuitableCards(suitableCards, Game.getActualCardCount) should be(ListBuffer(lowCard))
      Game.setActualCardCount(1)
      bot.pickFromSuitableCards(suitableCards, Game.getActualCardCount) should be(ListBuffer(lowCard))
      val suitableCards2: Map[Int, ListBuffer[Card]] =  Map(
        8 -> ListBuffer(lowCard),
        10 -> ListBuffer(middleCard, middleCard2),
        14 -> ListBuffer(highCard)
      )
      bot.pickFromSuitableCards(suitableCards2, Game.getActualCardCount) should be(ListBuffer(lowCard))
    }
    "play normally if the middle Card is missing" in {
      val bot: BotPlayer = new BotPlayer(0)
      bot.addCard(lowCard)
      bot.addCard(highCard)
      bot.addCard(middleCard)
      val suitableCards: Map[Int, ListBuffer[Card]] = Map(
        8 -> ListBuffer(lowCard),
        10 -> ListBuffer.empty,
        14 -> ListBuffer(highCard)
      )
      Game.setActualCardCount(1)
      bot.pickFromSuitableCards(suitableCards, Game.getActualCardCount) should be(ListBuffer(lowCard))
    }
    "play normally if the high Card is not an Ace" in {
      val bot: BotPlayer = new BotPlayer(0)
      bot.addCard(lowCard)
      bot.addCard(middleCard)
      bot.addCard(highCard2)
      val suitableCards: Map[Int, ListBuffer[Card]] = Map(
        8 -> ListBuffer(lowCard),
        10 -> ListBuffer(middleCard),
        13 -> ListBuffer(highCard2)
      )
      Game.setActualCardCount(1)
      bot.pickFromSuitableCards(suitableCards, Game.getActualCardCount) should be(ListBuffer(lowCard))
    }
  }

}
