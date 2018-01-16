package de.htwg.se.awol.controller.gameController

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.model.cardComponents.{Card, Deck}
import de.htwg.se.awol.model.environmentComponents.{CardEnv, GuiEnv, PlayerEnv}
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

  private var isGamePaused: Boolean = false //hehehe

  private var playerList: ListBuffer[Player] = ListBuffer()
  private var rankedList: ListBuffer[Player] = ListBuffer()

  private var swapCardsNeeded: Boolean = false
  private var roundNumber: Int = 1
  private var actualPlayerNum: Int = 0

  private var king: Option[Player] = None
  private var viceroy: Option[Player] = None
  private var viceasshole: Option[Player] = None
  private var asshole: Option[Player] = None

  // User controlled
  private var starterCard = Card(CardEnv.Values.Jack, CardEnv.Colors.Diamonds)
  private var actualCardsOnTable: mutable.Stack[ListBuffer[Card]] = mutable.Stack()
  private var deckSize: Int = Deck.smallCardStackSize

  // Game Handling
  def callNextActionByState(): Unit = {
    Game.getGameState match {
      case Game.States.NewGame =>
        createPlayers()
        publish(PlayersCreated())
      case Game.States.HandOut =>
        handoutCards()
        publish(CardsHandedToPlayers())

        callNextActionByState()
      case Game.States.FindStartingPlayer =>
        findStartingPlayer()
        publish(PlayerStatusChanged())

        callNextActionByState()
      case Game.States.CardSwap =>
        swapCards()
        publish(CardsHandedToPlayers())
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
    Platform.runLater(callNextActionByState)
    //callNextActionByState
  }

  def createPlayers(): Unit = {
    assert(Game.getGameState == Game.States.NewGame, "Creating players in wrong game state!")

    Game.humanPlayer = HumanPlayer(0)
    playerList.append(Game.humanPlayer)

    for (i <- 1 until playerCount) {
      playerList.append(new BotPlayer(i))
    }

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

    //publish
  }

  def newPlay(): Unit = {
    assert(Game.getGameState == Game.States.Playing, "Start playing in wrong game state!")

    // Reset some values
    Game.setActualCardValue(0)
    Game.setActualCardCount(0)
    Game.setPassCounter(0)
    clearCardsOnTable()

    actualPlayerNum = Game.getLeadingPlayer.getPlayerNumber
    markNextActivePlayer()

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
    if (player.cardAmount == 0 && !rankedList.contains(player)) {
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

      println("New game state: " + Game.getGameState.toString)

      true
    } else {
      false
    }
  }

  def doPlay(): Unit = {
    val player: Player = playerList(actualPlayerNum % playerCount)

    //println("\nPLAYING AGAIN!\n")
    if (!(rankedList.contains(player) || rankedList.lengthCompare(playerList.length - 1) == 0)) {
      markNextActivePlayer()
      actualPlayerNum += 1

      if (player.isHumanPlayer) {
        Game.setPlayerTurn(true)

        val suitableCards: Map[Int, ListBuffer[Card]] = player.findSuitableCards(Game.getActualCardValue, Game.getActualCardCount)

        publish(HumanPlayerPlaying(suitableCards))
      } else {
        botPlaying(player)
      }
    } else {
      Game.setPassCounter(Game.getPassCounter + 1)
      actualPlayerNum += 1
      checkForEndOfRound(player)
    }
  }

  def humanPlaying(pickedCards: ListBuffer[Card]): Unit = {
    val player: Player = Game.getHumanPlayer

    if (pickedCards.isEmpty) { // Passed
      if (Game.getActualCardCount == 0) {
        println("You are not allowed to pass when no card is on the table")
      } else {
        Game.setPassCounter(Game.getPassCounter + 1)
        Game.setPlayerTurn(false)

        Platform.runLater(checkForEndOfRound(player))
      }
    } else {
      val pickedCardValue = pickedCards.head.cardValue
      val pickedCardCount = pickedCards.length

      println(pickedCardValue, pickedCardCount)
      println(Game.getActualCardValue, Game.getActualCardCount)

      var usedCards: ListBuffer[Card] = ListBuffer()
      if (Game.getActualCardCount == 0) {
        usedCards = pickedCards.slice(0, pickedCardCount)
      } else {
        usedCards = pickedCards.slice(0, Game.getActualCardCount)
      }
      println("My pick (human): " + usedCards)

      if (player.pickAndDropCard(usedCards.length, pickedCardValue)) {

        player.removeCardsFromMyStack(usedCards)
        println("Setting new leading player")
        Platform.runLater(setCardLeadingPlayer(player))
        println("adding cards to table")
        Platform.runLater(addCardsToCardsOnTable(usedCards))
        println("set playerturn false")
        Game.setPlayerTurn(false)
        println("checking for end of round")
        checkForEndOfRound(player)
      }

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

        publish(BotPlayerPlaying(player, pickedCards))

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
      continuePlaying(player)
    }
  }

  def continuePlaying(player: Player): Unit = {
    if (false && player.isHumanPlayer) { // TODO: Entferne false
      triggerPlay()
    } else {
      val f = Future[Unit] { Thread.sleep(Settings.getTimeBetweenPlayerAction) }

      f.onComplete {
        case Success(v) => triggerPlay()
        case Failure(e) => throw e
      }
    }
  }

  def evaluateRound(): Unit = {
    assert(Game.getGameState == Game.States.Evaluation, "Evaluating round in wrong game state!")

    Game.setGameState(Game.States.Playing)

    publish(PronounceWinnerOfRound(Game.getLeadingPlayer))
  }

  def summarizeEndOfGame(): Unit = {
    val arschloch: Player = playerList.filter(_.cardAmount != 0).head
    rankedList.append(arschloch)

    for (player <- rankedList) {
      println(player)
    }

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

    publish(ShowEndOfGame(king.get, asshole.get))

    Game.setGameState(Game.States.HandOut)
  }

  // Event Triggers
  def markNextActivePlayer(): Unit = {
    val player: Player = playerList(actualPlayerNum % playerCount)

    Game.setActivePlayer(player)
    publish(PlayerStatusChanged())
  }

  def setCardLeadingPlayer(player: Player): Unit = {
    Game.setActivePlayer(player)
    Game.setLeadingPlayer(player)
    publish(PlayerStatusChanged())
  }

  def addCardsToCardsOnTable(pickedCards: ListBuffer[Card]): Unit = {
    actualCardsOnTable.push(pickedCards)
    publish(CardsOnTableChanged())
  }

  def clearCardsOnTable(): Unit = {
    actualCardsOnTable.clear()
    publish(CardsOnTableChanged())
  }

  // Getter - Setter
  def getLatestCardsOnTable: ListBuffer[Card] = {
    actualCardsOnTable.headOption match {
      case Some(c) => c
      case _ => ListBuffer[Card]()
    }
  }

  def getPlayerList: ListBuffer[Player] = playerList

  def getPlayerCount: Int = playerCount
  def setPlayerCount(count: Int): Unit = { playerCount = count }

  def setGamePausedStatus(bool: Boolean) = {
    if (!bool) {
      publish(GameContinuedFromPause())
    }
    isGamePaused = bool
  }
  def getGamePausedStatus: Boolean = isGamePaused
}
