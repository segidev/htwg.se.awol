package de.htwg.se.awol.controller.gameController

import de.htwg.se.awol.model.cardComponents.{Card, Deck}
import de.htwg.se.awol.model.environmentComponents.{CardEnv, PlayerEnv}
import de.htwg.se.awol.model.playerComponent.{BotPlayer, HumanPlayer, Player}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.swing.Publisher
import scala.util.{Failure, Random, Success}
import scalafx.application.Platform

class _GameHandler(private var playerCount: Int) extends Publisher {
  private var playerList: ListBuffer[Player] = ListBuffer()
  private var rankedList: ListBuffer[Player] = ListBuffer()

  private var swapCardsNeeded: Boolean = false
  private var roundNumber: Int = 1
  private var actualPlayerNum: Int = 0
  private val timeBetweenPlayerAction: Int = 1000

  private var king: Option[Player] = None
  private var viceroy: Option[Player] = None
  private var viceasshole: Option[Player] = None
  private var asshole: Option[Player] = None

  // User controlled
  private var starterCard = Card(CardEnv.Values.Jack, CardEnv.Colors.Diamonds)
  private var actualCardsOnTable: mutable.Stack[ListBuffer[Card]] = mutable.Stack()
  private var deckSize: Int = 32

  // Game Handling
  def callNextActionByState(): Unit = {
    Game.getGameState match {
      case Game.States.NewGame =>
        createPlayers()
        publish(new PlayersChanged)
      case Game.States.HandOut =>
        handoutCards()
        publish(new CardsChanged)

        callNextActionByState()
      case Game.States.FindStartingPlayer =>
        findStartingPlayer()
        publish(new ActivePlayerChanged)

        callNextActionByState()
      case Game.States.CardSwap =>
        swapCards()
        publish(new CardsChanged)
      case Game.States.Playing =>
        newPlay()
      case Game.States.Evaluation =>
        evaluateRound()
      case Game.States.EndOfGame =>
        summarizeEndOfGame()
      case _ => throw new RuntimeException("Illegal Game State!")
    }
  }

  def initNewGame(newDeckSize: Int, newPlayerCount: Int): Unit = {
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
    callNextActionByState()
  }

  def createPlayers(): Unit = {
    assert(Game.getGameState == Game.States.NewGame, "Creating players in wrong game state!")

    Game.humanPlayer = HumanPlayer(0)
    playerList.append(Game.humanPlayer)

    for (i <- 1 until playerCount) {
      playerList.append(new BotPlayer(i))
    }

    //Game.setActivePlayer(playerList.head) // TODO: weg?

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

    Game.setGameState(Game.States.FindStartingPlayer)
  }

  def findStartingPlayer(): Unit = {
    assert(Game.getGameState == Game.States.FindStartingPlayer, "Find starting player in wrong game state!")

    playerList.find(_.getRank == PlayerEnv.Rank.Asshole) match {
      case Some(p1) => setCardLeadingPlayer(p1)
      case _ => playerList.find(_.hasCard(starterCard)) match {
        case Some(p2) => setCardLeadingPlayer(p2)
        case _ => throw new MatchError("No player fulfills the given condition to be the starting player")
      }
    }

    if (swapCardsNeeded) {
      Game.setGameState(Game.States.CardSwap)
      //Game.setActualCardValue(0) // TODO: Ist das nötig?
    } else {
      Game.setGameState(Game.States.Playing)
    }
  }

  def swapCards(): Unit = {
    assert(Game.getGameState == Game.States.CardSwap, "Swapping cards in wrong game state!")

    king match {
      case Some(k) => k.swapWith(asshole.get)
      case _ => throw new RuntimeException("Swap cards operation without ranks to be set")
    }
  }

  def newPlay(): Unit = {
    assert(Game.getGameState == Game.States.Playing, "Start playing in wrong game state!")

    Game.setActualCardValue(0)
    Game.setActualCardCount(0)
    Game.setPassCounter(0)

    actualPlayerNum = Game.getActivePlayer.getPlayerNumber

    triggerPlay()
  }

  def triggerPlay(): Unit = {
    if (!Platform.isFxApplicationThread) {
      // Call tiggerPlay() in main thread!
      Platform.runLater(triggerPlay())
    } else {
      doPlay()
    }
  }

  def checkPlayerStatus(player: Player): Boolean = {
    if (player.cardAmount == 0) {
      // Remove player from actual playing ones and add to winner list
      rankedList.append(player)
    }

    if (Game.getPassCounter >= playerCount - 1) {
      println("REACHED END OF ROUND!")
      roundNumber += 1

      if (rankedList.lengthCompare(playerList.length - 1) >= 0) {
        Game.setGameState(Game.States.EndOfGame)
      } else {
        Game.setGameState(Game.States.Evaluation)
      }

      true
    } else {
      false
    }
  }

  def doPlay(): Unit = {
    val player: Player = playerList(actualPlayerNum % playerCount)

    if (!(rankedList.contains(player) || rankedList.lengthCompare(playerList.length - 1) == 0)) {
      actualPlayerNum += 1

      if (player.isHumanPlayer) {
        println("You are playing now!")
        Game.setPlayerTurn(true)

        val suitableCards: Map[Int, ListBuffer[Card]] = player.findSuitableCards(Game.getActualCardValue, Game.getActualCardCount)

        publish(new HumanPlayerPlaying(suitableCards))
      } else {
        println(player.getPlayerName + " is playing now")
        botPlaying(player)
      }
    } else {
      Game.setPassCounter(Game.getPassCounter + 1)
    }
  }

  def humanPlaying(pickedCards: ListBuffer[Card]): Boolean = {
    val pickedCardValue = pickedCards.head.cardValue
    val pickedCardCount = pickedCards.length

    println(pickedCardValue, pickedCardCount)
    println(Game.getActualCardValue, Game.getActualCardCount)

    if (pickedCardValue <= Game.getActualCardValue) { // TODO: Das muss alles in den HumanPlayer rein
      println("Card value below/equal the actual one")
      false
    } else if (Game.getActualCardCount != 0 && pickedCardCount < Game.getActualCardCount) {
      println("Amount of cards doesn't match actual of " + Game.getActualCardCount)
      false
    } else {
      // TODO: Passen ermöglichen

      val player: Player = Game.getActivePlayer
      val usedCards = pickedCards.slice(0, Game.getActualCardCount)

      player.removeCardsFromMyStack(usedCards)

      Game.setActualCardValue(pickedCardValue)
      if (Game.getActualCardCount == 0) {
        Game.setActualCardCount(pickedCardCount)
      }

      setCardLeadingPlayer(player)
      addCardsToCardsOnTable(usedCards)

      Game.setPlayerTurn(false)

      checkForEndOfRound(player)

      true
    }
    //Game.humanPlayer.throwMyCardsIntoGame(cardCount, Game.getActualCardValue, count, value)
  }

  def botPlaying(player: Player): Unit = {
    val suitableCards: Map[Int, ListBuffer[Card]] = player.findSuitableCards(Game.getActualCardValue, Game.getActualCardCount)

    player.pickAndDropCard(suitableCards) match {
      case Some(o) =>
        val newCardValue: Int = o._1
        val pickedCards: ListBuffer[Card] = o._2

        Game.setActualCardValue(newCardValue)
        Game.setActualCardCount(pickedCards.length)

        setCardLeadingPlayer(player)
        addCardsToCardsOnTable(pickedCards)

        println("He picked " + Game.getActualCardCount + " card(s) with value: " + Game.getActualCardValue + "\n")

        Game.setPassCounter(0)
      case _ =>
        Game.setPassCounter(Game.getPassCounter + 1)
        println("He passed...\n")
    }

    checkForEndOfRound(player)
  }

  def checkForEndOfRound(player: Player): Unit = {
    if (checkPlayerStatus(player)) {
      callNextActionByState()
    } else {
      triggerPauseBeforePlay()
    }
  }

  def triggerPauseBeforePlay(): Unit = {
    val f = Future[Unit] { Thread.sleep(timeBetweenPlayerAction) }
    f.onComplete {
      case Success(v) => triggerPlay()
      case Failure(e) => throw e
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

      if(!(rankedList.contains(player) || rankedList.lengthCompare(playerList.length - 1) == 0)) {

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
              val newCardValue: Int = o._1
              val pickedCards: ListBuffer[Card] = o._2

              Game.setActualCardValue(newCardValue)
              Game.setActualCardCount(pickedCards.length)

              setCardLeadingPlayer(player)
              addCardsToCardsOnTable(pickedCards)

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

  // Event Triggers
  def setCardLeadingPlayer(player: Player): Unit = {
    Game.setActivePlayer(player)
    publish(new ActivePlayerChanged)
  }

  def addCardsToCardsOnTable(pickedCards: ListBuffer[Card]): Unit = {
    actualCardsOnTable.push(pickedCards)
    publish(new CardsOnTableChanged)
  }

  // Getter - Setter
  def getLatestCardsOnTable: ListBuffer[Card] = {
    println(actualCardsOnTable)
    println(actualCardsOnTable.head)
    actualCardsOnTable.headOption match {
      case Some(c) => c
      case _ => ListBuffer[Card]()
    }
  }

  def getPlayerList: ListBuffer[Player] = playerList

  def getPlayerCount: Int = playerCount
  def setPlayerCount(count: Int): Unit = { playerCount = count }
}
