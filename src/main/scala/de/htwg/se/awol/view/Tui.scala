package de.htwg.se.awol.view

import de.htwg.se.awol.controller.gameController._
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.cardComponents.{Card, Deck}
import de.htwg.se.awol.model.environmentComponents.MessageEnv
import de.htwg.se.awol.model.playerComponent.Player

import scala.collection.mutable.ListBuffer
import scala.swing.Reactor
import scala.io.StdIn.readLine
import scalafx.application.Platform

class Tui(controller: _GameHandler) extends Reactor {
  listenTo(controller)

  val newGameWithAmount = "n\\s*(\\d+)".r

  def processInputLine(input: String): Unit = {
    val setMyCards = ("(\\d{1}) (\\d{1,2})").trim.r
    println("Processing Input right now...")
    input match {
      case "q" => return
      case "s" =>
        println("Initializing new game")
        controller.initNewGame(32, 2)
        controller.callNextActionByState()
        return
      case "" =>
        if (controller.getGamePausedStatus) {
          controller.setGamePausedStatus(false)
          controller.callNextActionByState()
          return
        }
      case _ =>
        println("Not quitting, not starting")
        None
    }

    Game.getGameState match {
      case Game.States.Playing => input match {
        case "p" =>
            val pickedCards: ListBuffer[Card] = ListBuffer()
            println("Player has passed, passCounter incresed by 1.")
            controller.humanPlaying(pickedCards)

        case setMyCards(a, b) =>
          val cardCount: Int = Game.getActualCardCount
          val count: Int = a.toInt
          val value: Int = b.toInt
          println("Count: " + count + " Value: " + value)
          // TODO: Magic numbers entfernen. 2 ist nicht immer die niedrigste Karte im Spiel. Eventuell Game.getLowestCard
          // TODO: implementieren
          if (cardCount == 0 && (count > 4 || count < 1) || cardCount != 0 && count < cardCount ) { // von != auf < geändert wegen GUI
            println("Amount of cards has to be between 1 and 4 while matching the amount of cards before")
          } else if (value > 14 || value < 2 || value <= Game.getActualCardValue) {
            println("Value of cards has to be between 2 and 14 and higher than the last player card(s)")
          } else {
            //Game.humanPlayer.pickAndDropCard(cardCount, Game.getActualCardValue, count, value)
            val suitableCards: Map[Int, ListBuffer[Card]] = Game.humanPlayer.findSuitableCards(Game.getActualCardValue, cardCount)
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
        case _ =>
          System.out.println("Command for cards \"" + input + "\" doesn't exist")
      }
      case _ => throw new RuntimeException("Illegal Game State!")
    }
  }

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