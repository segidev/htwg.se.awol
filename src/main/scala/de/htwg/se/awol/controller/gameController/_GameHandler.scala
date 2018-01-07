package de.htwg.se.awol.controller.gameController

import de.htwg.se.awol.model.cardComponents.{Card, Deck}
import de.htwg.se.awol.model.environmentComponents.{CardEnv, PlayerEnv}
import de.htwg.se.awol.model.playerComponent.{BotPlayer, HumanPlayer, Player}

import scala.collection.mutable.ListBuffer
import scala.swing.Publisher
import scala.util.Random

class _GameHandler(private var playerCount: Int) extends Publisher {
  private var playerList: ListBuffer[Player] = ListBuffer()
  private var rankedList: ListBuffer[Player] = ListBuffer()

  private var swapCardsNeeded: Boolean = false
  private var roundNumber: Int = 1

  private var king: Option[Player] = None
  private var viceroy: Option[Player] = None
  private var viceasshole: Option[Player] = None
  private var asshole: Option[Player] = None

  // User controlled
  private var starterCard = Card(CardEnv.Values.Jack, CardEnv.Colors.Diamonds)
  private var deckSize: Int = 32

  // Game Handling
  def resetHandler(newDeckSize: Int, newPlayerCount: Int): Unit = {
    playerList.clear()
    rankedList.clear()
    swapCardsNeeded = false
    roundNumber = 1
    king = None
    viceroy = None
    viceasshole = None
    asshole = None

    deckSize = newDeckSize
    playerCount = newPlayerCount

    Game.setGameState(Game.States.NewGame)
    createPlayers()
  }

  def createPlayers(): Unit = {
    assert(Game.getGameState == Game.States.NewGame, "Creating players in wrong game state!")

    Game.humanPlayer = HumanPlayer(0)
    playerList.append(Game.humanPlayer)

    for (i <- 1 until playerCount) {
      playerList.append(new BotPlayer(i))
    }

    Game.setActivePlayer(playerList.head)

    Game.setGameState(Game.States.HandOut)
  }

  def handoutCards(): Unit = {
    assert(Game.getGameState == Game.States.HandOut, "Handing out cards in wrong game state!")

    var deck = new Deck(deckSize)

    // Get the original card stack
    var cardHandOutList: ListBuffer[Card] = deck.getCards

    // Assign every player a random card as long as cards exist
    var i = 0
    while (cardHandOutList.nonEmpty) {
      val player: Player = playerList(i % playerCount)
      val assignedCard: Card = cardHandOutList.remove(Random.nextInt(cardHandOutList.length))

      player.addCard(assignedCard)

      i += 1
    }

    publish(new CardsChanged)

    setStartingPlayer()

    if (swapCardsNeeded) {
      Game.setGameState(Game.States.CardSwap)
      //Game.setActualCardValue(0) // TODO: Ist das nötig?
    } else {
      Game.setGameState(Game.States.Playing)
    }
  }

  def setStartingPlayer(): Unit = {
    playerList.find(_.getRank == PlayerEnv.Rank.Asshole) match {
      case Some(p1) => Game.setActivePlayer(p1)
      case _ => playerList.find(_.hasCard(starterCard)) match {
        case Some(p2) => Game.setActivePlayer(p2)
        case _ => throw new MatchError("No player fulfills the given conditions")
      }
    }
  }

  def swapCards(): Unit = {
    assert(Game.getGameState == Game.States.CardSwap, "Swapping cards in wrong game state!")

    king match {
      case Some(k) => k.swapWith(asshole.get)
      case _ => throw new RuntimeException("Swap cards operation without ranks to be set")
    }
  }

  def play(): Unit = {
    assert(Game.getGameState == Game.States.Playing, "Start playing in wrong game state!")

    Game.setActualCardValue(0)
    Game.setActualCardCount(0)
    Game.setPassCounter(0)

    var i: Int = Game.getActivePlayer.getPlayerNumber
    while(Game.getPassCounter < playerCount - 1) {
      val player: Player = playerList(i % playerCount)

      if(!(rankedList.contains(player) ||  rankedList.lengthCompare(playerList.length - 1) == 0)) {

        if (player.isHumanPlayer) {
          println("It's YOUR turn.")
          Game.setPlayerTurn(true)
          while(Game.getPlayerTurn) {
            if (Game.getActualCardCount == 0) {
              println("Please choose any amount of cards of the same value by typing" +
                " \"number of cards\" \"value\".")
            }
            else {
              println("Please pick " + Game.getActualCardCount + " cards by typing \"number of cards\" \"value\"" +
                " or type p to pass.")
            }
            if (Game.getActualCardValue == 0) { println("Choose any card value.") }
            else { println("Following card value was placed on the stack: " + Game.getActualCardValue) }
            println("Found cards: ", player.findSuitableCards(Game.getActualCardValue, Game.getActualCardCount))

            //input = readLine()
            //tui.processInputLine(input)
          }
        } else {
          println("It's player " + player.getPlayerNumber + " turn.")

          val suitableCards: Map[Int, ListBuffer[Card]] = player.findSuitableCards(Game.getActualCardValue, Game.getActualCardCount)
          println("Found cards: " + suitableCards)

          player.pickAndDropCard(suitableCards) match {
            case Some(o) =>
              Game.setActualCardValue(o._1)
              Game.setActualCardCount(o._2)
              Game.setActivePlayer(player)

              println("He picked " + Game.getActualCardCount + " card(s) with value: " + Game.getActualCardValue + "\n")

              Game.setPassCounter(0)
            case _ =>
              Game.setPassCounter(Game.getPassCounter + 1)
              println("He passed...\n")
          }
        }
        if (player.cardAmount == 0) {
          // Remove player from actual playing ones and add to winner list
          rankedList.append(player)
        }
        println(player + "\n")
      } else {
        Game.setPassCounter(Game.getPassCounter + 1)
      }

      i += 1
    }

    roundNumber += 1

    if (rankedList.lengthCompare(playerList.length - 1) >= 0) {
      Game.setGameState(Game.States.EndOfGame)
    } else {
      Game.setGameState(Game.States.Evaluation)
    }

    def evaluateRound(): Unit = {
      assert(Game.getGameState == Game.States.Evaluation, "Evaluating round in wrong game state!")

      println("\n == Player " + Game.getActivePlayer.getPlayerNumber + " has won the round! == \n")
      Game.setActualCardValue(0)
      Game.setGameState(Game.States.Playing)
    }

    def summarizeEndOfGame(): Unit = {
      val arschloch: Player = playerList.filter(_.cardAmount != 0).head
      rankedList.append(arschloch)

      playerList.foreach(_.resetRank())

      king = rankedList.headOption
      asshole = rankedList.lastOption

      king.get.setRank(PlayerEnv.Rank.King)
      asshole.get.setRank(PlayerEnv.Rank.Asshole)

      rankedList.length match {
        case 4 | 6 | 8 =>
          viceroy = Option(rankedList(1))
          viceasshole = Option(rankedList(rankedList.length - 2))

          viceroy.get.setRank(PlayerEnv.Rank.Viceroy)
          viceasshole.get.setRank(PlayerEnv.Rank.Viceasshole)
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
    }
  }

  // Getter - Setter
  def getPlayerList: ListBuffer[Player] = playerList

  def getPlayerCount: Int = playerCount
  def setPlayerCount(count: Int): Unit = { playerCount = count }
}
