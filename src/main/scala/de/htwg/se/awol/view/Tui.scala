package de.htwg.se.awol.view

import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.model.cardComponents.Deck

class Tui {
  val newGameWithAmount = "n\\s*(\\d+)".r

  def processInputLine(input: String) = {
    val setMyCards = ("(\\d{1,2}) " * Game.getActualCardCount).trim.r
    println(setMyCards)

    input match {
      case "q" => return _
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
          throw new MatchError("PASSED!")
        case setMyCards(a) =>
          Game.humanPlayer.throwMyCardsIntoGame(a)
        case setMyCards(a, b) =>
          Game.humanPlayer.throwMyCardsIntoGame(a, b)
        case setMyCards(a, b, c) =>
          Game.humanPlayer.throwMyCardsIntoGame(a, b, c)
        case setMyCards(a, b, c, d) =>
          Game.humanPlayer.throwMyCardsIntoGame(a, b, c, d)
        case _ =>
          System.err.println("Command \"" + input + "\" doesn't exist")
      }

      case _ => throw new RuntimeException("Illegal Game State!")
    }
  }
}