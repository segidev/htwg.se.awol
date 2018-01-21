package de.htwg.se.awol.controller.gameController.gameBaseImpl

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.controller.gameController._
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.cardComponents.{Card, Deck}
import de.htwg.se.awol.model.environmentComponents.{CardEnv, MessageEnv, PlayerEnv}
import de.htwg.se.awol.model.playerComponent.playerBaseImpl.BotPlayer
import de.htwg.se.awol.model.playerComponent.{HumanPlayer, Player}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.swing.Publisher
import scala.util.{Failure, Random, Success}
import scalafx.application.Platform

//noinspection ScalaStyle
class _GameHandler() extends Publisher {
  private var isGamePaused: Boolean = false
  private var gameId: Double = 0.0

  protected val playerList: ListBuffer[Player] = ListBuffer()
  private var activePlayerList: ListBuffer[Player] = ListBuffer()
  private val rankedList: ListBuffer[Player] = ListBuffer()
  private var deck: Deck = _

  private var swapCardsNeeded: Boolean = false
  private var roundNumber: Int = 1
  private var actualPlayerNum: Int = 0
  private var roundWinnersCount: Int = 0

  private var king: Option[Player] = None
  private var asshole: Option[Player] = None

  // User controlled
  private val starterCard = Card(CardEnv.Values.Jack, CardEnv.Colors.Diamonds)
  private val actualCardsOnTable: mutable.Stack[ListBuffer[Card]] = mutable.Stack()
  private var deckSize: Int = Deck.smallCardStackSize
  protected var totalPlayerCount: Int = _

  def initNewGame(newDeckSize: Int, newPlayerCount: Int): Unit = {
    validateNewGame(newDeckSize, newPlayerCount)

    setNewGameId()

    playerList.clear()
    activePlayerList.clear()
    rankedList.clear()
    roundNumber = 1
    swapCardsNeeded = false
    actualPlayerNum = 0

    king = None
    asshole = None

    deckSize = newDeckSize
    totalPlayerCount = newPlayerCount

    Game.setGameSettings(deckSize, totalPlayerCount, doSave = true)

    Game.setGameState(Game.States.NewGame)
    callNextActionByState()
  }

  def validateNewGame(newDeckSize: Int, newPlayerCount: Int): Unit = {
    if (newPlayerCount < Game.getMinPlayers || newPlayerCount > Game.getMaxPlayers || newPlayerCount % 2 != 0) {
      throw new IllegalArgumentException(LanguageTranslator.translate(MessageEnv.Warnings.PlayerCountMismatch).format(newPlayerCount))
    }
    deck = new Deck(newDeckSize)
  }

  def setNewGameId(): Unit = {
    val idRange: Double = 10.0
    val idAdd: Double = 0.1
    var newId: Double = 0.0

    do {
      newId = idRange * math.random + idAdd
    } while (newId == gameId)

    gameId = newId
  }

  def callNextActionByState(): Unit = {
    Game.getGameState match {
      case Game.States.NewGame =>
        createPlayers()
        publish(PlayersCreated())
      case Game.States.HandOut =>
        handoutCards()
        publish(CardsHandedToPlayers())

        callNextActionByState() // For: FindStartingPlayer
      case Game.States.FindStartingPlayer =>
        findStartingPlayer()
        publish(PlayerStatusChanged())

        callNextActionByState() // For: CardSawp or Playing
      case Game.States.CardSwap =>
        swapCards()
        publish(CardsHandedToPlayers())
      case Game.States.Playing =>
        startNewRound()
      case Game.States.Evaluation =>
        evaluateRound()
      case Game.States.EndOfGame =>
        summarizeEndOfGame()
      case _ => throw new RuntimeException("Illegal Game State!")
    }
  }

  def createPlayers(): Unit = {
    assert(Game.getGameState == Game.States.NewGame, "Creating players in wrong game state!")

    Game.setHumanPlayer(HumanPlayer(0))
    playerList.append(Game.getHumanPlayer)

    (1 until totalPlayerCount).foreach(playerNumber => {
      playerList.append(new BotPlayer(playerNumber))
    })

    Game.setGameState(Game.States.HandOut)
  }

  def handoutCards(): Unit = {
    assert(Game.getGameState == Game.States.HandOut, "Handing out cards in wrong game state!")

    activePlayerList = playerList.clone()

    val deck = new Deck(deckSize)

    // Get the original card stack
    val cardHandOutList: ListBuffer[Card] = deck.getCards

    // Assign every player a random card as long as cards exist
    var i = 0
    while (cardHandOutList.nonEmpty) {
      val player: Player = getPlayerByIndex(i)
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
        case _ => throw new MatchError("No player holds the starter card: " + starterCard)
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
      case Some(k) =>
        val swapList: ListBuffer[(Player, Card, Player)] = k.swapWith(asshole.get)
        Game.setGameState(Game.States.Playing)
        publish(CardsWereSwapped(swapList))
      case _ => throw new RuntimeException("Swap cards operation without ranks to be set")
    }
  }

  def startNewRound(): Unit = {
    assert(Game.getGameState == Game.States.Playing, "Start playing in wrong game state!")

    // Reset some values
    Game.setActualCardValue(0)
    Game.setActualCardCount(0)
    Game.setPassCounter(0)

    // Tell Gui that cards have changed
    actualCardsOnTable.clear()
    publish(new CardsOnTableChanged)

    if (Game.getLeadingPlayer.cardAmount > 0) {
      actualPlayerNum = activePlayerList.indexOf(Game.getLeadingPlayer)
    }

    triggerNextPlay(Game.getLeadingPlayer)
  }

  def triggerNextPlay(player: Player): Unit = {
    publish(CardsRemoveAllEventsAndEffects(playerList))
    markNextActivePlayer()

    val nextPlayer: Player = getPlayerByIndex(actualPlayerNum)

    if (nextPlayer.isHumanPlayer) {
      doPlay(nextPlayer)
    } else {
      val f = Future[Double] {
        val useId = gameId
        Thread.sleep(Settings.getGameSpeed)
        useId
      }

      f.onComplete {
        case Success(id) =>
          if (id == gameId) {
            Platform.runLater(doPlay(nextPlayer))
          }
        case Failure(e) => throw e
      }
    }
  }

  def doPlay(player: Player): Unit = {
    if (!(rankedList.contains(player) || activePlayerList.lengthCompare(1) == 0)) { // rankedList.lengthCompare(playerList.length - 1) == 0
      if (player.isHumanPlayer) {
        val suitableCards: Map[Int, ListBuffer[Card]] = player.findSuitableCards(Game.getActualCardValue, Game.getActualCardCount)

        publish(HumanPlayerPlaying(suitableCards, Game.getActualCardCount))
      } else {
        botPlaying(player)
      }
    } else {
      Game.addToPassCounter(1)
      validatePostPlay(player)
    }
  }

  def humanPlaying(pickedCards: ListBuffer[Card]): Option[ListBuffer[Card]] = { // (Boolean, ListBuffer[Card])
    val player: Player = Game.getHumanPlayer

    player.validatePick(pickedCards, Game.getActualCardCount, Game.getActualCardValue) match {
      case Some(usedCards: ListBuffer[Card]) =>
        if (usedCards.isEmpty) {
          Game.addToPassCounter(1)
          validatePostPlay(player)
          Some(ListBuffer.empty)
        } else {
          Game.setPassCounter(0)
          setLeadingValues(player, usedCards)
          validatePostPlay(player)
          Some(usedCards)
        }
      case _ =>
        None
    }
  }

  def botPlaying(player: Player): Unit = {
    val suitableCards: Map[Int, ListBuffer[Card]] = player.findSuitableCards(Game.getActualCardValue, Game.getActualCardCount)

    val pickedCards: ListBuffer[Card] = player.pickFromSuitableCards(suitableCards, Game.getActualCardCount)

    player.validatePick(pickedCards, Game.getActualCardCount, Game.getActualCardValue) match {
      case Some(pickedCards: ListBuffer[Card]) =>
        if (pickedCards.isEmpty) {
          Game.addToPassCounter(1)
          publish(BotPlayerPlaying(player, ListBuffer.empty))
        } else {
          Game.setPassCounter(0)
          setLeadingValues(player, pickedCards)
          publish(BotPlayerPlaying(player, pickedCards))
        }
      case _ =>
        Game.addToPassCounter(1)
        publish(BotPlayerPlaying(player, ListBuffer.empty))
    }

    validatePostPlay(player)
  }

  def setLeadingValues(player: Player, pickedCards: ListBuffer[Card]): Unit = {
    Game.setActualCardValue(pickedCards.head.cardValue)
    Game.setActualCardCount(pickedCards.length)

    setCardLeadingPlayer(player)
    addCardsToCardsOnTable(pickedCards)
  }

  def validatePostPlay(player: Player): Unit = {
    if (!checkForPlayerFinished(player)) {
      actualPlayerNum = (actualPlayerNum + 1) % activePlayerList.length
    } else {
      roundWinnersCount += 1
    }

    if (checkForEndOfRound()) {
      callNextActionByState()
    } else {
      triggerNextPlay(player)
    }
  }

  def checkForPlayerFinished(player: Player): Boolean = {
    if (player.cardAmount == 0 && !rankedList.contains(player)) {
      rankedList.append(player)
      activePlayerList.-=(player)
      true
    } else {
      false
    }
  }

  def checkForEndOfRound(): Boolean = {
    if (Game.getPassCounter >= activePlayerList.length - 1 + roundWinnersCount || (Game.getActualCardValue == CardEnv.Values.Ace.id && Game.getPassCounter > 0)) {
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

  def evaluateRound(): Unit = {
    assert(Game.getGameState == Game.States.Evaluation, "Evaluating round in wrong game state!")

    publish(PronounceWinnerOfRound(Game.getLeadingPlayer))

    Game.setGameState(Game.States.Playing)
  }

  def summarizeEndOfGame(): Unit = {
    assert(activePlayerList.lengthCompare(1) == 0, "activePlayerList should contain only one player now!")

    val arschloch: Player = activePlayerList.remove(0) //playerList.filter(_.cardAmount != 0).head
    rankedList.append(arschloch)

    playerList.foreach(player => {
      player.resetRank()
      player.clearCards()
    })

    king = rankedList.headOption
    asshole = rankedList.lastOption

    king.get.setRank(PlayerEnv.Rank.King)
    asshole.get.setRank(PlayerEnv.Rank.Asshole)

    rankedList.clear()
    swapCardsNeeded = true

    Game.setGameState(Game.States.HandOut)

    publish(ShowEndOfGame(king.get, asshole.get))
  }

  // Event Triggers
  def markNextActivePlayer(): Unit = {
    val player: Player = getPlayerByIndex(actualPlayerNum)

    Game.setActivePlayer(player)
    publish(PlayerStatusChanged())
  }

  def setCardLeadingPlayer(player: Player): Unit = {
    roundWinnersCount = 0
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

  def loadSettings(): Unit = {
    if (!Settings.loadSettingsFromJSON()) {
      publish(SettingsLoadFailed())
    }
  }

  // Getter - Setter
  def getLatestCardsOnTable: ListBuffer[Card] = {
    actualCardsOnTable.headOption match {
      case Some(c) => c
      case _ => ListBuffer[Card]()
    }
  }

  def getPlayerList: ListBuffer[Player] = playerList

  def getPlayerByIndex(idx: Int): Player = { activePlayerList(idx % activePlayerList.length) }

  def getPlayerCount: Int = totalPlayerCount
  def setPlayerCount(count: Int): Unit = { totalPlayerCount = count }

  def getGamePausedStatus: Boolean = isGamePaused
  def setGamePausedStatus(bool: Boolean): Unit = {
    if (!bool) {
      publish(GameContinuedFromPause())
    }
    isGamePaused = bool
  }

  def setKing(playerOption: Option[Player]): Unit = {
    playerOption match {
      case Some(player) =>
        king = playerOption
        player.setRank(PlayerEnv.Rank.King)
      case _ =>
    }
  }

  def setAsshole(playerOption: Option[Player]): Unit = {
    playerOption match {
      case Some(player) =>
        asshole = playerOption
        player.setRank(PlayerEnv.Rank.Asshole)
      case _ =>
    }
  }

  def appendArschlochToRankedList(): Unit = {
    val arschloch: Player = activePlayerList.remove(0)
    rankedList.append(arschloch)
  }
}
