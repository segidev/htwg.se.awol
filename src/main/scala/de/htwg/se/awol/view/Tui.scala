package de.htwg.se.awol.view

import de.htwg.se.awol.controller.gameController._
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.cardComponents.{Card, Deck}
import de.htwg.se.awol.model.environmentComponents.MessageEnv
import de.htwg.se.awol.model.playerComponent.Player

import scala.collection.mutable.ListBuffer
import scala.swing.Reactor
import scala.io.StdIn.readLine
import scala.util.matching.Regex
import scalafx.application.Platform

class Tui(controller: _GameHandler) extends Reactor {
  listenTo(controller)

  val newGameWithAmount: Regex = "s\\s+(\\d+)\\s+(\\d+)".r
  val setMyCards: Regex = "([1-4]) ([1-9]{1,2})".trim.r

  def processInputLine(input: String): Unit = {
    if (processDefaultInput(input)) {
      Game.getGameState match {
        case Game.States.Playing => processPlayingInput(input)
        case _ => println("Command not possible, please try another one.") // TODO: Übersetzung
      }
    }
  }

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
          case e: Exception => println(e.getMessage)
        }
        false
      case "" =>
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
            println(LanguageTranslator.translate(MessageEnv.PhrasesHuman.Passed))
          case _ =>
            println(LanguageTranslator.translate(MessageEnv.PhrasesHuman.PassForbidden))
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
              case Some(usedCards: ListBuffer[Card]) =>
                println(LanguageTranslator.translate(MessageEnv.PhrasesHuman.YouPlayedThoseCards).format(
                  "%dx %s [%d]".format(usedCards.length, usedCards.head.cardName, usedCards.head.cardValue)))
              case _ =>
                println(LanguageTranslator.translate(MessageEnv.PhrasesHuman.NoSuitableCards))
            }
          case _ =>
            println(LanguageTranslator.translate(MessageEnv.PhrasesHuman.NoSuitableCards))
            println(LanguageTranslator.translate(MessageEnv.PhrasesHuman.SuitableCards).format(
            suitableCards.toSeq.sortBy(_._1).map(f => "%dx %s [%d]".format(f._2.length, f._2.head.cardName, f._1)).mkString(", ")))
        }

      case _ => System.out.println("Command for cards \"" + input + "\" doesn't exist")
    }
  }

  /*// TODO: Magic numbers entfernen. 2 ist nicht immer die niedrigste Karte im Spiel. Eventuell Game.getLowestCard
  // TODO: implementieren
  if (cardCount == 0 && (count > 4 || count < 1) || cardCount != 0 && count < cardCount ) { // von != auf < geändert wegen GUI
    println("Amount of cards has to be between 1 and 4 while matching the amount of cards before")
  } else if (value > 14 || value < 2 || value <= Game.getActualCardValue) {
    println("Value of cards has to be between 2 and 14 and higher than the last player card(s)")
  } else {
    val suitableCards: Map[Int, ListBuffer[Card]] = Game.getHumanPlayer.findSuitableCards(Game.getActualCardValue, cardCount)
    suitableCards.get(value) match {
      case Some(buffer) =>
        if (buffer.size < count) {
          println("You don't have enough card of the value " + value + " on your hand.")
        } else {
          println("Calling humanPlaying Function")
          controller.humanPlaying(buffer.take(count))
        }
      case _ => println("You don't have any cards with the given value " + value)
    }
  }*/
  /*
  val cardCount: Int = Game.getActualCardCount
        val count: Int = a.toInt
        val value: Int = b.toInt

        // TODO: Magic numbers entfernen. 2 ist nicht immer die niedrigste Karte im Spiel. Eventuell Game.getLowestCard
        // TODO: implementieren
        if (cardCount == 0 && (count > 4 || count < 1) || cardCount != 0 && count < cardCount ) { // von != auf < geändert wegen GUI
          println("Amount of cards has to be between 1 and 4 while matching the amount of cards before")
        } else if (value > 14 || value < 2 || value <= Game.getActualCardValue) {
          println("Value of cards has to be between 2 and 14 and higher than the last player card(s)")
        } else {
          val suitableCards: Map[Int, ListBuffer[Card]] = Game.getHumanPlayer.findSuitableCards(Game.getActualCardValue, cardCount)
          suitableCards.get(value) match {
            case Some(buffer) =>
              if (buffer.size < count) {
                println("You don't have enough card of the value " + value + " on your hand.")
              } else {
                println("Calling humanPlaying Function")
                controller.humanPlaying(buffer.take(count))
              }
            case _ => println("You don't have any cards with the given value " + value)
          }
        }
   */

  reactions += {
    case event: PlayersCreated =>
      println(LanguageTranslator.translate(MessageEnv.PhrasesGeneral.GameHasStarted))

    case event: CardsHandedToPlayers =>
      println(LanguageTranslator.translate(MessageEnv.PhrasesGeneral.CardsHandedOutToPlayers))

    case event: HumanPlayerPlaying =>
      println(LanguageTranslator.translate(MessageEnv.PhrasesHuman.IsPlayingNow))
      println(LanguageTranslator.translate(MessageEnv.PhrasesHuman.SuitableCards).format(
        event.suitableCards.toSeq.sortBy(_._1).map(f => "%dx %s [%d]".format(f._2.length, f._2.head.cardName, f._1)).mkString(", ")))

    case event: BotPlayerPlaying =>
      println(MessageEnv.getBotPlayerPlaying(event))

    case event: CardsWereSwapped =>
      val sb: StringBuilder = new StringBuilder()

      sb.append(MessageEnv.getCardsWereSwappedText(event))
      sb.append(LanguageTranslator.translate(MessageEnv.PhrasesGeneral.HitEnterToContinue))

      println(sb.toString())

    case event: PronounceWinnerOfRound =>
      println(MessageEnv.getPronounceWinnerOfRoundText(event))

    case event: ShowEndOfGame =>
      val sb: StringBuilder = new StringBuilder()

      sb.append(MessageEnv.getShowEndOfGameText(event))
      sb.append(LanguageTranslator.translate(MessageEnv.PhrasesGeneral.HitEnterToContinue))

      println(sb.toString())
  }
}