package de.htwg.se.awol.view

import de.htwg.se.awol.controller.gameController._
import de.htwg.se.awol.model.cardComponents.{Card, Deck}

import scala.collection.mutable.ListBuffer
import scala.swing.Reactor
import scala.io.StdIn.readLine
import scalafx.application.Platform

class Tui(controller: _GameHandler) extends Reactor {
  listenTo(controller)

  /*reactions += {
    case ev:
  }*/

  val newGameWithAmount = "n\\s*(\\d+)".r

  def processInputLine(input: String): Unit = {
    val setMyCards = ("(\\d{1}) (\\d{1,2})").trim.r
    println("Processing Input right now...")
    input match {
      case "q" => return
      case "s" =>
        println("Initializing new game")
        Platform.runLater(controller.initNewGame(32, 2))
        Platform.runLater(controller.callNextActionByState())
        return
      case "" =>
        if (controller.getGamePausedStatus) {
          Platform.runLater(controller.setGamePausedStatus(false))
          Platform.runLater(controller.callNextActionByState())
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
    case event: PlayersCreated => println("The game has started")
    case event: CardsHandedToPlayers => println("Everyone received their cards")
    case event: HumanPlayerPlaying =>
      println("You are playing now!")
      println("These are your suitable cards: \n" + event.suitableCards)
    case event: BotPlayerPlaying =>
      println(event.player.getPlayerName + " is playing now")
      println("I (Bot) used these cards: " + event.pickedCards)
      println("I have " + event.player.cardAmount + " cards left.")
    /*case event: PronounceWinnerOfRound =>
      showWinnerOfRound(event.player)
      humanPlayerArea.removeCardEventsAndEffects()
    case event: ShowEndOfGame => showGlobalMessage(String.format(LanguageTranslator.translate(MessageEnv.Phrases.EndOfGameText),
      event.king.getPlayerName, event.king.getRankName, event.asshole.getPlayerName, event.asshole.getRankName))*/
  }
}