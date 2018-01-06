package de.htwg.se.awol

/*
WAS kann die TUI ?
Start: Erwartet Deckgröße, Spielerzahl und evtl. gewünschte Sprache
Spiel: Karten austeilen, { Spieler machen Zug, Gewinner wird festgelegt, Ränge werden verteilt } loop
Ende: Den Spieler demütigen, egal ob er gewonnen oder verloren hat. Natürlich härter demütigen wenn er verloren hat.
*/

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.controller.gameController.{Game, _GameHandler}
import de.htwg.se.awol.model.cardComponents.{Card, Deck}
import de.htwg.se.awol.model.environmentComponents.{CardEnv, PlayerEnv}
import de.htwg.se.awol.model.languageComponents.{LanguageEnglish, LanguageGerman, LanguageYouth}
import de.htwg.se.awol.model.playerComponent.{BotPlayer, HumanPlayer, Player}
import de.htwg.se.awol.view.Tui

import scala.collection.mutable.ListBuffer
import scala.io.StdIn.readLine
import scala.util.Random

object Arschloch {
  val controller = new _GameHandler()
  val tui = new Tui(controller)


  var king: Player = _
  var viceroy: Player = _
  var viceasshole: Player = _
  var asshole: Player = _

  //noinspection ScalaStyle
  def main(args: Array[String]): Unit = {
    var swapCardsNeeded: Boolean = false
    var deckSize: Int = 32
    var playerCount: Int = 2 // Dwarf nur 2, 4, 6 oder 8 sign
    var starterCard = Card(CardEnv.Values.Jack, CardEnv.Colors.Diamonds)
    Settings.setLanguage(LanguageGerman)

    //TODO: Abfragen Anzahl der Karten durch Anzahl der Spieler gleichmässig verteilt wird
    /*while(deckSize % playerCount != 0) {
      deckSize = readLine("How big is your deck? ").toInt
      playerCount = readLine("How many players should play? ").toInt
    }*/

    var deck = new Deck(deckSize)

    var roundNumber: Int = 1

    var rankedList: ListBuffer[Player] = new ListBuffer
    var playerList: ListBuffer[Player] = new ListBuffer[Player]

    // Set Player first
    //Game.humanPlayer = HumanPlayer(0)
    //playerList.append(Game.humanPlayer)

    for (i <- 0 until playerCount) { // TODO: Switch 0 to 1 when bringing back human player
      playerList.append(new BotPlayer(i))
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
        tui.processInputLine(input)
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
        activePlayer = playerList.find(_.getRank == PlayerEnv.Rank.Asshole) match {
          case Some(p1) => p1
          case _ => playerList.find(_.hasCard(starterCard)) match {
            case Some(p2) => p2
            case _ => throw new MatchError("No player fulfills the given conditions")
          }
        }

        // Who got which cards
        for (i <- playerList.indices) {
          val player: Player = playerList(i)
          println("Player " + player.getPlayerNumber + " holds " + player.cardAmount + " cards with rank " + player)
        }

        if (swapCardsNeeded) {
          println("Players swapping cards...")
          Game.setGameState(Game.States.CardSwap)
        } else {
          println("Player " + activePlayer.getPlayerNumber + " starts the game!")
          println("\n3. The game starts.\n")
          Game.setGameState(Game.States.Playing)
        }

      }

      if (Game.getGameState == Game.States.Playing) {
        println("Round " + roundNumber + " starts")
        var cardsOnTable: ListBuffer[Card] = new ListBuffer
        var actualCardVal: Int = 0
        Game.setActualCardCount(0)
        var passCounter: Int = 0

        var i: Int = activePlayer.getPlayerNumber
        while(passCounter < playerCount - 1) {
          val player: Player = playerList(i % playerCount)

          if(!(rankedList.contains(player) ||  rankedList.lengthCompare(playerList.length - 1) == 0)) {

            if (player.isHumanPlayer) {
              println("It's YOUR turn. Please pick " + Game.getActualCardCount + " cards or type p to pass")

              println("Found cards: ", player.findSuitableCards(actualCardVal, Game.getActualCardCount))

              input = readLine()
              tui.processInputLine(input)
            } else {
              println("It's player " + player.getPlayerNumber + " turn.")

              val suitableCards: Map[Int, ListBuffer[Card]] = player.findSuitableCards(actualCardVal, Game.getActualCardCount)
              println("Found cards: " + suitableCards)

              player.pickAndDropCard(suitableCards) match {
                case Some(o) =>
                  actualCardVal = o._1
                  Game.setActualCardCount(o._2)
                  activePlayer = player

                  println("He picked " + Game.getActualCardCount + " card(s) with value: " + actualCardVal + "\n")

                  if (player.cardAmount == 0) {
                    // Remove player from actual playing ones and add to winner list
                    rankedList.append(player)
                  }
                  passCounter = 0
                case _ =>
                  passCounter += 1
                  println("He passed...\n")
              }
              println(player + "\n")
            }
          } else {
            passCounter += 1
          }

          i += 1
        }

        roundNumber += 1

        if (rankedList.lengthCompare(playerList.length - 1) >= 0) {
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
        val arschloch: Player = playerList.filter(_.cardAmount != 0).head
        rankedList.append(arschloch)

        playerList.foreach(_.resetRank())


        king = rankedList.head
        asshole = rankedList.last

        king.setRank(PlayerEnv.Rank.King)
        asshole.setRank(PlayerEnv.Rank.Asshole)
        rankedList.length match {
          case 4 | 6 | 8 =>
            viceroy = rankedList(1)
            viceasshole = rankedList(rankedList.length-2)

            viceroy.setRank(PlayerEnv.Rank.Viceroy)
            viceasshole.setRank(PlayerEnv.Rank.Viceasshole)
          case _ =>
        }

        for (i <- rankedList.indices) {
          val player: Player = rankedList(i)
          player.clearCards()
          println("\nPlayer " + player.getPlayerNumber + " ranked at place " + (i + 1) + " and is now the " + player.getRankName)
        }

        rankedList.clear()
        swapCardsNeeded = true

        Game.setGameState(Game.States.HandOut)

        println("\nPress Enter to initialize a new game")
        input = readLine()
      }

      if (Game.getGameState == Game.States.CardSwap) {
        king.swapWith(asshole)

        println("\nPress Enter to start playing")
        input = readLine()

        Game.setGameState(Game.States.Playing)
      }
    } while(input != "q")
  }
}
