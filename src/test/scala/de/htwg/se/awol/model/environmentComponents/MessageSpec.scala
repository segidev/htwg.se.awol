package de.htwg.se.awol.model.environmentComponents

import de.htwg.se.awol.controller.gameController.{BotPlayerPlaying, CardsWereSwapped, PronounceWinnerOfRound, ShowEndOfGame}
import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.playerComponent.{BotPlayer, HumanPlayer, Player}
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner
import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.model.languageComponents.{LanguageEnglish, LanguageGerman}

import scala.collection.mutable.ListBuffer
import scala.swing.Publisher

@RunWith(classOf[JUnitRunner])
class MessageSpec extends WordSpec with Matchers {
  "Swapping cards" should {
    "work between two Bots" in {
      Settings.setLanguage(LanguageEnglish)

      val fromPlayer: Player = new BotPlayer(0)
      val toPlayer: Player = new BotPlayer(1)
      val card: Card = new Card(CardEnv.Values.Ace, CardEnv.Colors.Diamonds)
      val cardSwap: ListBuffer[(Player, Card, Player)] = new ListBuffer()
      cardSwap.append((fromPlayer, card, toPlayer))
      val message: String = MessageEnv.getCardsWereSwappedText(CardsWereSwapped(cardSwap))
      message should be("Card swap time!\n\nYou receives Ace [Diamond] from Jan\n")
    }
    "work between Human and Bot in both directions" in {
      Settings.setLanguage(LanguageEnglish)

      val fromPlayer: Player = new HumanPlayer(0)
      val toPlayer: Player = new BotPlayer(1)
      val card: Card = new Card(CardEnv.Values.Ace, CardEnv.Colors.Diamonds)
      val cardSwap: ListBuffer[(Player, Card, Player)] = new ListBuffer()
      cardSwap.append((fromPlayer, card, toPlayer))
      val messageToBot: String = MessageEnv.getCardsWereSwappedText(CardsWereSwapped(cardSwap))
      messageToBot should be("Card swap time!\n\nYou receive Ace [Diamond] from Jan\n")
      cardSwap.clear()
      cardSwap.append((toPlayer, card, fromPlayer))
      val messageFromBot: String = MessageEnv.getCardsWereSwappedText(CardsWereSwapped(cardSwap))
      messageFromBot should be("Card swap time!\n\nJan receives Ace [Diamond] from you\n")
    }
  }

  "Pronouncing a winner" should {
    "work for humanPlayer and BotPlayer" in {
      Settings.setLanguage(LanguageEnglish)

      val humanWinner: Player = new HumanPlayer(0)
      val botWinner: Player = new BotPlayer(1)
      val humanMessage: String = MessageEnv.getPronounceWinnerOfRoundText(PronounceWinnerOfRound(humanWinner))
      val botMessage: String = MessageEnv.getPronounceWinnerOfRoundText(PronounceWinnerOfRound(botWinner))
      humanMessage should be("You have won the round! Nice :)")
      botMessage should be("Jan has won the round!")
    }
  }

  "EndOfGame text" should {
    "work if the humanPlayer won" in {
      Settings.setLanguage(LanguageEnglish)

      val human: Player = new HumanPlayer(0)
      human.setRank(PlayerEnv.Rank.King)
      val bot: Player = new BotPlayer(1)
      bot.setRank(PlayerEnv.Rank.Asshole)
      val humanKingMessage: String = MessageEnv.getShowEndOfGameText(ShowEndOfGame(human, bot))
      humanKingMessage should be("End of game\n\nYou are the winner and you rank as King\n"
        + "Jan lost and is the new Asshole\n")
    }
    "work if the botPlayer won" in {
      val human: Player = new HumanPlayer(0)
      human.setRank(PlayerEnv.Rank.Asshole)
      val bot: Player = new BotPlayer(1)
      bot.setRank(PlayerEnv.Rank.King)
      val botKingMessage: String = MessageEnv.getShowEndOfGameText(ShowEndOfGame(bot, human))
      botKingMessage should be ("End of game\n\nJan ist the winner and ranks as King\n"
        + "You have lost, that makes you the Asshole\n")
    }
  }

  "The BotPlaying text which prints the bots turn" should {
    "tell if he passed" in {
      Settings.setLanguage(LanguageEnglish)

      val bot: Player = new BotPlayer(1)
      val noCards: ListBuffer[Card] = new ListBuffer()
      val passMessage: String = MessageEnv.getBotPlayerPlaying(BotPlayerPlaying(bot, noCards))
      passMessage should be("\n== Jan is playing now! ==\n\nJan passed")
    }
    "show which cards the bot used" in {
      Settings.setLanguage(LanguageEnglish)

      val bot: Player = new BotPlayer(1)
      val pickedCards: ListBuffer[Card] = new ListBuffer()
      pickedCards.append(new Card(CardEnv.Values.Ace, CardEnv.Colors.Diamonds))
      val passMessage: String = MessageEnv.getBotPlayerPlaying(BotPlayerPlaying(bot, pickedCards))
      passMessage should be("\n== Jan is playing now! ==\n\nJan played those cards: 1x Ace\nJan has 0 card(s) left")
    }
  }
}
