package de.htwg.se.awol.view

import com.google.inject.Inject
import com.typesafe.scalalogging.LazyLogging
import de.htwg.se.awol.controller.gameController._
import de.htwg.se.awol.controller.gameController.handler._TGameHandler
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.environmentComponents.MessageEnv

import scala.collection.mutable.ListBuffer
import scala.swing.Reactor
import scala.util.matching.Regex

class Tui @Inject()(controller: _TGameHandler) extends Reactor with LazyLogging {
  listenTo(controller)

  val newGameWithAmount: Regex = "s\\s+(\\d+)\\s+(\\d+)".r
  val setMyCards: Regex = "([1-4]) ([2-9]|1[0-4])".trim.r
  val emptyCommand: Regex = "^\\s*$".r

  //noinspection ScalaStyle
  def processInputLine(input: String): Unit = {
    logger.debug(s"Input: $input")
    if (processDefaultInput(input)) {
      Game.getGameState match {
        case Game.States.Playing => processPlayingInput(input)
        case _ => logger.warn(LanguageTranslator.translate(MessageEnv.PhrasesHuman.CommandNotAvailable))
      }
    }
  }

  //noinspection ScalaStyle
  def processDefaultInput(input: String): Boolean = {
    input match {
      case "q" => false
      case newGameWithAmount(a, b) =>
        val cardCount = a.toInt
        val playerCount = b.toInt

        try {
          controller.initNewGame(cardCount, playerCount)
          controller.callNextActionByState()
        } catch {
          case e: Exception => logger.error(e.getMessage)
        }
        false
      case emptyCommand() =>
        if (controller.getGamePausedStatus) {
          controller.setGamePausedStatus(false)
          controller.callNextActionByState()
          false
        } else {
          true
        }
      case _ => true
    }
  }

  //noinspection ScalaStyle
  def processPlayingInput(input: String): Unit = {
    input match {
      case "p" =>
        controller.humanPlaying(ListBuffer.empty) match {
          case Some(_) =>
            logger.debug(LanguageTranslator.translate(MessageEnv.PhrasesHuman.Passed))
          case _ =>
            logger.debug(LanguageTranslator.translate(MessageEnv.PhrasesHuman.PassForbidden))
        }

      case setMyCards(a, b) =>
        var count: Int = a.toInt
        val value: Int = b.toInt

        if (Game.getActualCardCount > 0) {
          count = Game.getActualCardCount
        }

        val suitableCards: Map[Int, ListBuffer[Card]] = Game.getHumanPlayer.findSuitableCards(Game.getActualCardValue, Game.getActualCardCount)

        suitableCards.get(value) match {
          case Some(pickedCards) =>
            controller.humanPlaying(pickedCards.take(count)) match {
              case Some(usedCards) =>
                logger.debug(LanguageTranslator.translate(MessageEnv.PhrasesHuman.YouPlayedThoseCards).format(
                  "%dx %s [%d]".format(usedCards.length, usedCards.head.cardValueName, usedCards.head.cardValue)))
              case _ =>
                logger.debug(LanguageTranslator.translate(MessageEnv.PhrasesHuman.NoSuitableCards))
            }
          case _ =>
            logger.debug(LanguageTranslator.translate(MessageEnv.PhrasesHuman.NoSuitableCards))
            logger.debug(LanguageTranslator.translate(MessageEnv.PhrasesHuman.SuitableCards).format(
            suitableCards.toSeq.sortBy(_._1).map(f => "%dx %s [%d]".format(f._2.length, f._2.head.cardValueName, f._1)).mkString(", ")))
        }

      case _ => logger.warn(LanguageTranslator.translate(MessageEnv.PhrasesHuman.CardCommandNotAvailable).format(input))
    }
  }

  //noinspection ScalaStyle
  reactions += {
    case _: PlayersCreated =>
      logger.debug(LanguageTranslator.translate(MessageEnv.PhrasesGeneral.GameHasStarted))

    case _: CardsHandedToPlayers =>
      logger.debug(LanguageTranslator.translate(MessageEnv.PhrasesGeneral.CardsHandedOutToPlayers))

    case event: HumanPlayerPlaying =>
      logger.debug(LanguageTranslator.translate(MessageEnv.PhrasesHuman.IsPlayingNow))
      if (event.suitableCards.isEmpty) {
        logger.debug(LanguageTranslator.translate(MessageEnv.PhrasesHuman.NoSuitableCards))
      } else {
        logger.debug(LanguageTranslator.translate(MessageEnv.PhrasesHuman.SuitableCards).format(
          event.suitableCards.toSeq.sortBy(_._1).map(f => "%dx %s [%d]".format(f._2.length, f._2.head.cardValueName, f._1)).mkString(", ")))
      }

    case event: BotPlayerPlaying =>
      logger.debug(MessageEnv.getBotPlayerPlaying(event))

    case event: CardsWereSwapped =>
      val sb: StringBuilder = new StringBuilder()

      sb.append(MessageEnv.getCardsWereSwappedText(event))
      sb.append(LanguageTranslator.translate(MessageEnv.PhrasesGeneral.HitEnterToContinue))

      logger.debug(sb.toString())

    case event: PronounceWinnerOfRound =>
      val sb: StringBuilder = new StringBuilder()

      sb.append(MessageEnv.getPronounceWinnerOfRoundText(event))
      sb.append(LanguageTranslator.translate(MessageEnv.PhrasesGeneral.HitEnterToContinue))

      logger.debug(sb.toString())

    case event: ShowEndOfGame =>
      val sb: StringBuilder = new StringBuilder()

      sb.append(MessageEnv.getShowEndOfGameText(event))
      sb.append(LanguageTranslator.translate(MessageEnv.PhrasesGeneral.HitEnterToContinue))

      logger.debug(sb.toString())

    case event: SettingsLoadFailed => logger.debug(LanguageTranslator.translate(MessageEnv.Warnings.LoadSettingsFailed) + "\n%s".format(event.error))
  }
}