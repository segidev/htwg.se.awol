package de.htwg.se.awol.model.environmentComponents

import de.htwg.se.awol.controller.gameController.{BotPlayerPlaying, CardsWereSwapped, PronounceWinnerOfRound, ShowEndOfGame}
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.environmentComponents.MessageEnv.Questions
import de.htwg.se.awol.model.playerComponent.Player

object MessageEnv {
  object Warnings extends Enumeration {
    val DividableByFour, MaxAmountOfCards, MissingLanguage, MissingTranslation, PlayerCountMismatch,
    LoadSettingsFailed, WriteSettingsFailed = Value
  }

  object Questions extends Enumeration {
    val QuitGame: Questions.Value = Value
  }

  object PhrasesGeneral extends Enumeration {
    val WelcomeToTheGame, ClickAnywhereToContinue, HitEnterToContinue, GameHasStarted, CardsHandedOutToPlayers = Value
  }

  object PhrasesBot extends Enumeration {
    val WonTheRound, IsTheWinner, IsTheLooser, ReceivesCardFrom, ReceivesCardFromHuman, IsPlayingNow,
    Passed, UsedTheseCards, HasCardsLeft = Value
  }

  object PhrasesHuman extends Enumeration {
    val WonTheRound, IsTheWinner, IsTheLooser, ReceivesCardFrom, SuitableCards, NoSuitableCards, IsPlayingNow,
    Passed, PassForbidden, YouPlayedThoseCards, HowManyCardsToPlay, CommandNotAvailable, CardCommandNotAvailable = Value
  }

  object Titles extends Enumeration {
    val GameTitle, QuitGame, GameOptions = Value
  }

  object Words extends Enumeration {
    val Yes, No, Pass, CardSwap, EndOfGame = Value
  }

  object Menues extends Enumeration {
    val File, NewGame, Quit, Options, Players_2, Players_4, Players_6, Players_8, DeckSize,
    StartGame, Cancel, GameSpeed, Fast, Normal, Slow, GameLanguage = Value
  }

  def getCardsWereSwappedText(event: CardsWereSwapped): String = {
    val sb: StringBuilder = new StringBuilder()

    sb.append(LanguageTranslator.translate(MessageEnv.Words.CardSwap))
    event.swappedCards.foreach(eventData => {
      val fromPlayer: Player = eventData._1
      val card: Card = eventData._2
      val toPlayer: Player = eventData._3

      if (fromPlayer.isHumanPlayer) {
        sb.append(LanguageTranslator.translate(MessageEnv.PhrasesHuman.ReceivesCardFrom).format(card, toPlayer.getPlayerName))
      } else if (toPlayer.isHumanPlayer) {
        sb.append(LanguageTranslator.translate(MessageEnv.PhrasesBot.ReceivesCardFromHuman).format(fromPlayer.getPlayerName, card))
      } else {
        sb.append(LanguageTranslator.translate(MessageEnv.PhrasesBot.ReceivesCardFrom).format(fromPlayer.getPlayerName, card, toPlayer.getPlayerName))
      }
    })

    sb.toString()
  }

  def getPronounceWinnerOfRoundText(event: PronounceWinnerOfRound): String = {
    val player: Player = event.player

    if (player.isHumanPlayer) {
      LanguageTranslator.translate(MessageEnv.PhrasesHuman.WonTheRound)
    } else {
      LanguageTranslator.translate(MessageEnv.PhrasesBot.WonTheRound).format(player.getPlayerName)
    }
  }

  def getShowEndOfGameText(event: ShowEndOfGame): String = {
    val king: Player = event.king
    val asshole: Player = event.asshole

    val sb: StringBuilder = new StringBuilder()
    sb.append(LanguageTranslator.translate(MessageEnv.Words.EndOfGame))

    if (king.isHumanPlayer) {
      sb.append(LanguageTranslator.translate(MessageEnv.PhrasesHuman.IsTheWinner).format(king.getRankName))
    } else {
      sb.append(LanguageTranslator.translate(MessageEnv.PhrasesBot.IsTheWinner).format(king.getPlayerName, king.getRankName))
    }

    if (asshole.isHumanPlayer) {
      sb.append(LanguageTranslator.translate(MessageEnv.PhrasesHuman.IsTheLooser).format(asshole.getRankName))
    } else {
      sb.append(LanguageTranslator.translate(MessageEnv.PhrasesBot.IsTheLooser).format(asshole.getPlayerName, asshole.getRankName))
    }

    sb.toString()
  }

  def getBotPlayerPlaying(event: BotPlayerPlaying): String = {
    val sb: StringBuilder = new StringBuilder()

    sb.append(LanguageTranslator.translate(MessageEnv.PhrasesBot.IsPlayingNow).format(event.player.getPlayerName))

    if (event.pickedCards.isEmpty) {
      sb.append(LanguageTranslator.translate(MessageEnv.PhrasesBot.Passed).format(event.player.getPlayerName))
    } else {
      sb.append(LanguageTranslator.translate(MessageEnv.PhrasesBot.UsedTheseCards).format(event.player.getPlayerName,
        "%dx %s".format(event.pickedCards.length, event.pickedCards.head.cardName)))
      sb.append(LanguageTranslator.translate(MessageEnv.PhrasesBot.HasCardsLeft).format(event.player.getPlayerName,
        event.player.cardAmount))
    }

    sb.toString()
  }
}