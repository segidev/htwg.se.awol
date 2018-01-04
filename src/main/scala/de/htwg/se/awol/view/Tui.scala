package de.htwg.se.awol.view

import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.model.cardComponents.Deck

class Tui {
  val newGameWithAmount = "n\\s*(\\d+)".r

  def processInputLine(input: String): Unit = {
    //val setMyCards = ("(\\d{1,2}) " * Game.getActualCardCount).trim.r
    val setMyCards = ("(\\d{1}) (\\d{1,2})").trim.r
    println(setMyCards)

    input match {
      case "q" => return
      case _ => None
    }

    Game.getGameState match {
      case Game.States.NewGame => input match {
        case "s" =>
          Game.setGameState(Game.States.HandOut)
        case _ =>
          System.err.println("Command \"" + input + "\" doesn't exist")
      }

      case Game.States.HandOut => input match {
        case "c" =>
        case _ =>
          System.err.println("Command \"" + input + "\" doesn't exist")
      }

      case Game.States.Playing => input match {
        case "p" =>
          if (Game.getActualCardCount == 0) {
            println("Player is not allowed to pass if there are no cards in play.")
          } else {
            Game.setPassCounter(Game.getPassCounter + 1)
            Game.setPlayerTurn(false)
            println("Player has passed, passCounter incresed by 1.")
          }
        case setMyCards(a, b) =>
          val cardCount: Int = Game.getActualCardCount
          val count: Int = a.toInt
          val value: Int = b.toInt
          // TODO: Magic numbers entfernen. 2 ist nicht immer die niedrigste Karte im Spiel. Eventuell Game.getLowestCard
          // TODO: implementieren
          if (cardCount == 0 && (count > 4 || count < 1) || cardCount != 0 && count != cardCount ) {
            println("Amount of cards has to be between 1 and 4 while matching the amount of cards before")
          } else if (value > 14 || value < 2 || value <= Game.getActualCardValue) {
            println("Value of cards has to be between 2 and 14 and higher than the last player card(s)")
          } else {
            Game.humanPlayer.throwMyCardsIntoGame(cardCount, Game.getActualCardValue, count, value)
          }
        case _ =>
          System.err.println("Command for cards \"" + input + "\" doesn't exist")
      }
      case _ => throw new RuntimeException("Illegal Game State!")
    }
  }
}