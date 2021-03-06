package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.environmentComponents.{CardEnv, PlayerEnv}
import de.htwg.se.awol.model.playerComponent.bot.baseImpl.BotPlayer
import de.htwg.se.awol.model.playerComponent.human.HumanPlayer
import de.htwg.se.awol.model.languageComponents.LanguageGerman
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

import scala.collection.mutable.ListBuffer

@RunWith(classOf[JUnitRunner])
class HumanPlayerSpec extends WordSpec with Matchers {
  val human: HumanPlayer = new HumanPlayer(0)
  "A Human" can {
    val empty: ListBuffer[Card] = new ListBuffer()
    val highcard: Card = new Card(CardEnv.Values.Ace, CardEnv.Colors.Diamonds)
    val lowcard: Card = new Card(CardEnv.Values.Two, CardEnv.Colors.Diamonds)
    val oneCard: ListBuffer[Card] = new ListBuffer()
    oneCard.append(highcard)
    "not use the bot equivalent of choosing cards" in {
      human.pickFromSuitableCards(Map[Int, ListBuffer[Card]](), 5) should be(ListBuffer.empty)
    }
    "choose no cards if he has to play" in {
      Game.setActualCardCount(0)
      human.validatePick(empty, Game.getActualCardCount, Game.getActualCardValue) should be(None)
    }
    "choose to pass" in {
      Game.setActualCardCount(1)
      human.validatePick(empty, Game.getActualCardCount, Game.getActualCardValue) should be(Some(ListBuffer.empty))
    }
    "choose a wrong amount of cards" in {
      Game.setActualCardCount(2)
      human.validatePick(oneCard, Game.getActualCardCount, Game.getActualCardValue)
    }
    "choose valid cards" in {
      Game.setActualCardValue(13)
      Game.setActualCardCount(1)
      human.validatePick(oneCard, Game.getActualCardCount, Game.getActualCardValue)
    }
    "choose a value below the last played card value" in {
      Game.setActualCardValue(10)
      oneCard.clear()
      oneCard.append(lowcard)
      human.validatePick(oneCard, Game.getActualCardCount, Game.getActualCardValue)
    }
    "not swap cards without being king or asshole" in {
      val bot: BotPlayer = new BotPlayer(1)
      an [MatchError] should be thrownBy human.swapWith(bot)
    }
  }
  "A Human Player" should {
    "be human" in {
      human.isHumanPlayer should be(true)
    }
    "have a toString method" in {
      Settings.setLanguage(LanguageGerman)
      val cardsLeft: Int = human.getCards.size
      val string: String = "Ich [%d card(s) left]".format(cardsLeft)
      human.toString should be(string)
    }
    "have player number 0" in {
      human.getPlayerNumber should be(0)
    }
  }

  "PlayerEnv" should {
    "be callable" in {
      PlayerEnv
    }
  }

}
