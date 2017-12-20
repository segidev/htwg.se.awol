package de.htwg.se.awol

/*
WAS kann die TUI ?
Start: Erwartet Deckgröße, Spielerzahl und evtl. gewünschte Sprache
Spiel: Karten austeilen, { Spieler machen Zug, Gewinner wird festgelegt, Ränge werden verteilt } loop
Ende: Den Spieler demütigen, egal ob er gewonnen oder verloren hat. Natürlich härter demütigen wenn er verloren hat.
*/

import de.htwg.se.awol.model.cardComponents.{Card, Deck}
import de.htwg.se.awol.model.environmentComponents.CardEnv
import de.htwg.se.awol.model.languageComponents.LanguageGerman
import de.htwg.se.awol.model.playerComponent.Player
import de.htwg.se.awol.view.Tui

import scala.collection.mutable.ListBuffer
import scala.io.StdIn.readLine
import scala.util.Random

/**
  * *** States ***
  * NewGame: In diesem Zustand wird das Kartendeck erstellt und die Anfangskarte wird festgelegt
  * HandOut: Die Karten werden an die Spieler verteilt und der Spieler der anfangen darf wird festgelegt
  *
  * Playing: Die Spieler werfen ihre Karten in die Arena, dabei sind natürlich Regeln zu beachten.
  *
  * *** CardConditions ***
  * One: Es wurde eine Karte gelegt, die anderen Spieler müssen mit genau einer höheren Karte den Stich machen oder passen.
  * Two: Es wurden zwei Karten gelegt, die anderen Spieler müssen mit genau zwei höheren, gleichen Karten den Stich machen oder passen.
  * Three: Es wurden drei Karten gelegt, die anderen Spieler müssen mit genau drei höheren, gleichen Karten den Stich machen oder passen.
  * Four: Es wurden vier Karten gelegt, die anderen Spieler müssen mit genau vier höheren, gleichen Karten den Stich machen oder passen.
  * *** End of CardConditions ***
  *
  * Evaluation: Die Auswertung der Runde. Der Spieler der den Stich gewonnen hat, wird die nächste Runde beginnen. Zurück zu [Playing]
  *
  * EndOfGame: Den Spielern werden ihre jeweiligen Ränge zugewiesen. Das Arschloch wird den ersten Zug machen dürfen. Zurück zu [HandOut]
  * CardSwap: Die Spieler tauschen ihre höchsten/niedrigsten Karten mit den jeweiligen Rängen. Zurück zu [Playing] [Erfordert ein Spieler als Arschloch]
  * *** End of States ***
  */
object Game {
  // TODO Vielleicht muss man hier noch sicherstellen, dass die States in gewisser Reihenfolge aufgerufen werden!
  object States extends Enumeration {
    val NewGame, HandOut, Playing, Evaluation, EndOfGame, CardSwap = Value
  }

  object CardConditions extends Enumeration {
    val One, Two, Three, Four = Value
  }

  private var actualState: States.Value = States.NewGame

  def getActualState: States.Value = actualState
  def setActualState(newState: States.Value): Unit = actualState = newState
}

object Arschloch {
  val tui = new Tui
  var deck = new Deck(Deck.smallCardStackSize)

  //noinspection ScalaStyle
  def main(args: Array[String]): Unit = {
    var deckSize = 32
    var playerCount = 4
    var starterCard = new Card(CardEnv.Values.Seven, CardEnv.Colors.Diamonds)
    var lang = LanguageGerman

    var playerList: Array[Player] = new Array[Player](playerCount)
    for (i <- 0 until playerCount) {
      playerList(i) = new Player(i)
    }

    var activePlayer: Player = playerList(0)

    var input: String = ""
    do {
      if (Game.getActualState == Game.States.NewGame) {
        println("1. New game with " + playerCount + " players\n")
        println(deck)
        println("Starter Card is: " + starterCard)
        print("Enter s to start game: ")

      }
      if (Game.getActualState == Game.States.HandOut) {
        println("\n2. Handing out cards to players...\n")

        // Get the original card stack
        var cardHandOutList: ListBuffer[Card] = deck.getCards

        // Assigned every player a random card as long as cards exist
        var i = 0
        while (cardHandOutList.nonEmpty) {
          val player: Player = playerList(i % playerCount)
          val assignedCard: Card = cardHandOutList.remove(Random.nextInt(cardHandOutList.length))

          player.addCard(assignedCard)

          i += 1
        }

        // Show all players and their cards and find the player with the starter card
        for (i <- playerList.indices) {
          val player: Player = playerList(i)
          println("Player " + player.getPlayerNumber + " holds " + player.cardAmount + " cards with rank " + player)

          if (player.hasCard(starterCard)) {
            activePlayer = player
          }
        }

        println("Player " + activePlayer.getPlayerNumber + " starts the game!")

        Game.setActualState(Game.States.Playing)

      }
      if (Game.getActualState == Game.States.Playing) {
        println("\n3. The game starts.\n")

        var i: Int = activePlayer.getPlayerNumber
        for(c <- 0 until playerCount) {
          val player: Player = playerList(i % playerCount)
          val playerNumber: Int = player.getPlayerNumber

          if (playerNumber == 0) {
            println("It's YOUR turn")
          } else {
            println("It's player " + player.getPlayerNumber + " turn.")
          }

          i += 1
        }
      }

      input = readLine()
      deck = tui.processInputLine(input, deck)
    } while(input != "q")
  }
}
