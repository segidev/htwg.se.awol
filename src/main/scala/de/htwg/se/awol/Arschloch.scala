package de.htwg.se.awol

/*
WAS kann die TUI ?
Start: Erwartet Deckgröße, Spielerzahl und evtl. gewünschte Sprache
Spiel: Karten austeilen, { Spieler machen Zug, Gewinner wird festgelegt, Ränge werden verteilt } loop
Ende: Den Spieler demütigen, egal ob er gewonnen oder verloren hat. Natürlich härter demütigen wenn er verloren hat.
*/

import de.htwg.se.awol.controller.environmentController.Game
import de.htwg.se.awol.model.cardComponents.{Card, Deck}
import de.htwg.se.awol.model.environmentComponents.CardEnv
import de.htwg.se.awol.model.languageComponents.LanguageGerman
import de.htwg.se.awol.model.playerComponent.Player
import de.htwg.se.awol.view.Tui

import scala.collection.mutable.ListBuffer
import scala.io.StdIn.readLine
import scala.util.Random

object Arschloch {
  val tui = new Tui
  var deck = new Deck(Deck.smallCardStackSize)

  //noinspection ScalaStyle
  def main(args: Array[String]): Unit = {
    var deckSize = 32
    var playerCount = 2
    var starterCard = new Card(CardEnv.Values.Jack, CardEnv.Colors.Diamonds)
    var lang = LanguageGerman

    var roundNumber: Int = 0

    var winnerList: ListBuffer[Player] = new ListBuffer
    var playerList: ListBuffer[Player] = new ListBuffer[Player]
    for (i <- 0 until playerCount) {
      playerList.append(new Player(i))
    }

    var activePlayer: Player = playerList.head
    var winningPlayer: Player = playerList.head

    var input: String = ""
    do {
      if (Game.getGameState == Game.States.NewGame) {
        println("1. New game with " + playerCount + " players\n")
        println(deck)
        println("Starter Card is: " + starterCard)
        print("Enter s to start game: ")

        input = readLine()
        deck = tui.processInputLine(input, deck)
      }

      if (Game.getGameState == Game.States.HandOut) {
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

        println("\n3. The game starts.\n")
        Game.setGameState(Game.States.Playing)
      }

      if (Game.getGameState == Game.States.Playing) {
        println("Round " + roundNumber + " starts")
        var cardsOnTable: ListBuffer[Card] = new ListBuffer
        var actualCardVal: Int = 0
        var actualCardCount: Int = 0

        var i: Int = activePlayer.getPlayerNumber
        for(c <- 0 until playerCount) {
          val player: Player = playerList(i % playerCount)

          if(!winnerList.contains(player)) {
            val playerNumber: Int = player.getPlayerNumber

            if (playerNumber == -1) {
              println("It's YOUR turn")
              input = readLine()
              deck = tui.processInputLine(input, deck)
            } else {
              // TODO Prüfe für KI ob Anzahl der gelegten Karten (1 - 4) vom selben Wert vorhanden und ob höher
              println("It's player " + player.getPlayerNumber + " turn.")

              player.pickCard(actualCardVal, actualCardCount) match {
                case Some(o) =>
                  actualCardVal = o._1; actualCardCount = o._2
                  activePlayer = player

                  println("He picked " + actualCardCount + " card(s) with value: " + actualCardVal + "\n")

                  if (player.cardAmount == 0) {
                    // Remove player from actual playing ones and add to winner list
                    winnerList.append(player)
                  }
                case _ => println("He passed...\n")
              }
            }
          }

          i += 1
        }

        roundNumber += 1

        if (winnerList.length == playerList.length - 1) {
          Game.setGameState(Game.States.EndOfGame)
        } else {
          Game.setGameState(Game.States.Evaluation)
        }
      }

      if (Game.getGameState == Game.States.Evaluation) {
        println("\n == Player " + activePlayer.getPlayerNumber + " has won the round! == \n")

        Game.setGameState(Game.States.Playing)
      }

      if (Game.getGameState == Game.States.EndOfGame) {
        for (i <- winnerList.indices) {
          val player: Player = winnerList(i)
          println("\nPlayer " + player.getPlayerNumber + " ranked at place " + (i + 1))
          //player.setRank()
        }

        winnerList.clear()

        Game.setGameState(Game.States.HandOut)

        input = readLine()

        /*println("Resetting the game...\n")
        for (player <- winnerList) {
          playerList.append(player)
        }

        winnerList.clear()*/
      }
    } while(input != "q")
  }
}
