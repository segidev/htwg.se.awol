package de.htwg.se.awol.view

import de.htwg.se.awol.Game
import de.htwg.se.awol.model.cardComponents.Deck

class Tui {
  val newGameWithAmount = "n\\s*(\\d+)".r

  def processInputLine(input: String, deck: Deck): Deck = {
    input match {
      case "q" => deck
      case _ => None
    }

    Game.getActualState match {
      case Game.States.NewGame => input match {
        case "s" =>
          Game.setActualState(Game.States.HandOut)
          deck
        case _ =>
          System.err.println("Command \"" + input + "\" doesn't exist")
          deck
      }
      case Game.States.HandOut => input match {
        case "c" => deck
        case _ =>
          System.err.println("Command \"" + input + "\" doesn't exist")
          deck
      }

      case _ => throw new RuntimeException("Illegal Game State!")
    }
    /*input match {
      case "n" => new Deck(32)
      case newGameWithAmount(a) =>
        try {
          val deck = new Deck(a.toInt)
          Game.setActualState(Game.States.NewGame)
          deck
        } catch {
          case iob: IndexOutOfBoundsException => {
            System.err.println(iob.getMessage)
            deck
          }
          case iae: IllegalArgumentException => {
            System.err.println(iae.getMessage)
            deck
          }
        }
      case _ =>
        System.err.println("Command \"" + input + "\" doesn't exist")
        deck
    }*/
  }
}