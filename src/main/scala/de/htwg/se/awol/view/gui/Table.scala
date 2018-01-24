package de.htwg.se.awol.view.gui

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.controller.gameController._
import de.htwg.se.awol.controller.gameController.handler._TGameHandler
import de.htwg.se.awol.controller.gameController.handler.gameBaseImpl._GameHandler
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.environmentComponents.{GuiEnv, MessageEnv}
import de.htwg.se.awol.model.playerComponent.Player

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.swing.Reactor
import scalafx.Includes._
import scalafx.embed.swing.SFXPanel
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.image.ImageView
import scalafx.scene.layout._
import scalafx.scene.text.TextAlignment

class Table(controller: _TGameHandler) extends SFXPanel with Reactor {
  listenTo(controller)

  // Variables
  private val playerAreaMap: mutable.HashMap[Player, PlayerArea] = mutable.HashMap()
  private var humanPlayerArea: PlayerArea = _
  private val (cardWidth, cardHeight) = GuiEnv.getCardSize

  // Player Layouts
  private val playerTopRowLayout: GridPane = new GridPane {
    minHeight = Table.minSizeBotArea
  }
  private val playerRightRowLayout: GridPane = new GridPane {
    minWidth = Table.minSizeBotArea
  }
  private val playerBottomRowLayout: GridPane = new GridPane {
    minHeight = Table.minSizeHumanArea
  }
  private val playerLeftRightLayout: GridPane = new GridPane {
    minWidth = Table.minSizeBotArea
  }

  // Table Area
  private val tableCardOneLayout: VBox = new VBox() {style=Table.styleEmptyCardArea; minWidth=cardWidth; maxHeight=cardHeight}
  private val tableCardTwoLayout: VBox = new VBox() {style=Table.styleEmptyCardArea; minWidth=cardWidth; maxHeight=cardHeight}
  private val tableCardThreeLayout: VBox = new VBox() {style=Table.styleEmptyCardArea; minWidth=cardWidth; maxHeight=cardHeight}
  private val tableCardFourLayout: VBox = new VBox() {style=Table.styleEmptyCardArea; minWidth=cardWidth; maxHeight=cardHeight}

  private val tableCardsLayout: HBox = new HBox {
    alignment = Pos.Center
    style = Table.styleTableArea
    spacing = Table.spacingGap
    children = List(tableCardOneLayout, tableCardTwoLayout, tableCardThreeLayout, tableCardFourLayout)
  }

  private val tablePlayersLayout: BorderPane = new BorderPane {
    top = playerTopRowLayout
    right = playerRightRowLayout
    bottom = playerBottomRowLayout
    left = playerLeftRightLayout
    center = tableCardsLayout
  }

  private val globalMessage: Label = new Label {
    maxHeight = Double.PositiveInfinity
    maxWidth = Double.PositiveInfinity
    alignment = Pos.Center
    textAlignment = TextAlignment.Center
    style = Table.styleGlobalMessage
    visible = false

    onMouseReleased = handle {
      controller.setGamePausedStatus(false)
      controller.callNextActionByState()
    }
  }

  private val mainPlayLayout: StackPane = new StackPane() {
    children = List(tablePlayersLayout, globalMessage)
    vgrow = Priority.Always
    style= Table.styleBackgroundArea
  }

  private val mainLayout: VBox = new VBox() {
    children = Seq(new MainMenuBar(Table.this), mainPlayLayout)
  }

  scene = new Scene {
    root = mainLayout
  }

  // Methods
  def showGlobalMessage(msg: String): Unit = {
    controller.setGamePausedStatus(true)
    globalMessage.text = msg
    globalMessage.visible = true
  }

  def hideGlobalMessage(globalMessage: Label): Unit = {
    globalMessage.text = ""
    globalMessage.visible = false
  }

  def addPlayerToTop(playerArea: PlayerArea, startIdx: Int): Unit = {
    playerArea.setLayout(GuiEnv.Layout.TOP)
    playerTopRowLayout.add(playerArea, startIdx, 0)
  }

  def addPlayerToRight(playerArea: PlayerArea, startIdx: Int): Unit = {
    playerArea.setLayout(GuiEnv.Layout.RIGHT)
    playerRightRowLayout.add(playerArea, 0, startIdx)
  }

  def addPlayerToBottom(playerArea: PlayerArea, startIdx: Int): Unit = {
    playerArea.setLayout(GuiEnv.Layout.BOTTOM)
    playerBottomRowLayout.add(playerArea, startIdx, 0)
  }

  def addPlayerToLeft(playerArea: PlayerArea, startIdx: Int): Unit = {
    playerArea.setLayout(GuiEnv.Layout.LEFT)
    playerLeftRightLayout.add(playerArea, 0, startIdx)
  }

  def resetLayoutAndVariables(): Unit = {
    playerTopRowLayout.children.clear()
    playerRightRowLayout.children.clear()
    playerBottomRowLayout.children.clear()
    playerLeftRightLayout.children.clear()

    playerAreaMap.clear()
  }

  def createPlayerAreas(): Unit = {
    val playerList = controller.getPlayerList

    var i = 0
    playerList.foreach(player => {
      val playerArea = new PlayerArea(player, controller)

      if (player.isHumanPlayer) {
      humanPlayerArea = playerArea
      humanPlayerArea.hidePlayerImage()
    }

      playerAreaMap.put(player, playerArea)

      playerList.length match {
      case 2 => assign2PlayerPositions(i, playerArea)
      case 4 => assign4PlayerPositions(i, playerArea)
      case 6 => assign6PlayerPositions(i, playerArea)
      case 8 => assign8PlayerPositions(i, playerArea)
      case _ => throw new MatchError("Illegal number of players: " + playerList.length)
    }

      i += 1
    })
  }

  def clearCardsFromTable(): Unit = {
    tableCardOneLayout.children.clear()
    tableCardTwoLayout.children.clear()
    tableCardThreeLayout.children.clear()
    tableCardFourLayout.children.clear()
  }

  def assign2PlayerPositions(idx: Int, playerArea: PlayerArea): Unit = {
    idx match {
      case 0 => addPlayerToBottom(playerArea, 0)
      case 1 => addPlayerToTop(playerArea, 0)
    }
  }

  def assign4PlayerPositions(idx: Int, playerArea: PlayerArea): Unit = {
    idx match {
      case 0 => addPlayerToBottom(playerArea, 0)
      case 1 => addPlayerToLeft(playerArea, 0)
      case 2 => addPlayerToTop(playerArea, 0)
      case 3 => addPlayerToRight(playerArea, 0)
    }
  }

  //noinspection ScalaStyle
  def assign6PlayerPositions(idx: Int, playerArea: PlayerArea): Unit = {
    idx match {
      case 0 => addPlayerToBottom(playerArea, 0)
      case 1 => addPlayerToLeft(playerArea, 1)
      case 2 => addPlayerToLeft(playerArea, 0)
      case 3 => addPlayerToTop(playerArea, 0)
      case 4 => addPlayerToTop(playerArea, 1)
      case 5 => addPlayerToRight(playerArea, 0)
    }
  }

  //noinspection ScalaStyle
  def assign8PlayerPositions(idx: Int, playerArea: PlayerArea): Unit = {
    idx match {
      case 0 => addPlayerToBottom(playerArea, 0)
      case 1 => addPlayerToLeft(playerArea, 1)
      case 2 => addPlayerToLeft(playerArea, 0)
      case 3 => addPlayerToTop(playerArea, 0)
      case 4 => addPlayerToTop(playerArea, 1)
      case 5 => addPlayerToRight(playerArea, 0)
      case 6 => addPlayerToRight(playerArea, 1)
      case 7 => addPlayerToBottom(playerArea, 1)
    }
  }

  def showAndSetGameOptions(): Boolean = {
    val diag = new GameOptionsDialog()

    diag.showAndWait() match {
      case Some(diag.buttonStart) =>
        controller.initNewGame(diag.getDeckSize, diag.getPlayerCount)
        true
      case _ => false
    }
  }

  def showNewGameDialog(): Unit = {
    if (showAndSetGameOptions()) {
      controller.callNextActionByState()
    }
  }

  def exitApplication(): Unit = {
    val buttonYes = new ButtonType(LanguageTranslator.translate(MessageEnv.Words.Yes))
    val buttonNo = new ButtonType(LanguageTranslator.translate(MessageEnv.Words.No))

    val result = new Alert(AlertType.Confirmation) {
      initOwner(scene.getWindow)
      title = LanguageTranslator.translate(MessageEnv.Titles.QuitGame)
      headerText = LanguageTranslator.translate(MessageEnv.Questions.QuitGame)
      buttonTypes = Seq(buttonYes, buttonNo)
    }.showAndWait()

    result match {
      case Some(`buttonYes`) => sys.exit(0)
      case _ =>
    }
  }

  def saveSettings(): Unit = {
    if (!Settings.saveSettingsToJSON(Settings.getSettingsPath)) {
      showSettingsWriteError()
    }
  }

  // Event driven methods
  def updatePlayerView(): Unit = {
    resetLayoutAndVariables()
    createPlayerAreas()
  }

  def updateCardView(): Unit = {
    playerAreaMap.values.foreach(_.updatePlayerLabel())
    humanPlayerArea.showCardsOnTable()
  }

  def updatePlayerHints(): Unit = {
    playerAreaMap.values.foreach(_.setAsActive(false))

    playerAreaMap.get(Game.getActivePlayer) match {
      case Some(p) => p.setAsActive(true)
      case _ => throw new MatchError("Active player seems to not be existent in the game")
    }

    playerAreaMap.get(Game.getLeadingPlayer) match {
      case Some(p) => p.setAsLeading()
      case _ => throw new MatchError("Leading player seems to not be existent in the game")
    }
  }

  def updateTableView(): Unit = {
    clearCardsFromTable()

    var i = 0
    controller.getLatestCardsOnTable.foreach(card => {
      val cardImageView: ImageView = GuiEnv.getCardImageView(card)
      cardImageView.setTranslateX(0)

      i match {
        case 0 => tableCardOneLayout.children.add(cardImageView)
        case 1 => tableCardTwoLayout.children.add(cardImageView)
        case 2 => tableCardThreeLayout.children.add(cardImageView)
        case 3 => tableCardFourLayout.children.add(cardImageView)
      }

      i += 1
    })
  }

  def removeAllEventsAndEffectsFromCards(playerList: ListBuffer[Player]): Unit = {
    playerList.foreach(player => {
      playerAreaMap.get(player) match {
        case Some(playerArea) => playerArea.removeCardEventsAndEffects()
        case _ => throw new MatchError("Couldn't clear cards from player " + player.getPlayerName)
      }
    })
  }

  def showSettingsLoadError(error: String = ""): Unit = {
    new Alert(AlertType.Warning) {
      headerText = LanguageTranslator.translate(MessageEnv.Warnings.LoadSettingsFailed) + "\n%s".format(error)
    }.showAndWait()
  }

  def showSettingsWriteError(): Unit = {
    new Alert(AlertType.Warning) {
      headerText = LanguageTranslator.translate(MessageEnv.Warnings.WriteSettingsFailed)
    }.showAndWait()
  }

  // Listener
  reactions += {
    case _: PlayersCreated => updatePlayerView()

    case _: CardsHandedToPlayers => updateCardView()

    case event: CardsRemoveAllEventsAndEffects => removeAllEventsAndEffectsFromCards(event.playerList)

    case _: PlayerStatusChanged => updatePlayerHints()

    case _: CardsOnTableChanged => updateTableView()

    case event: HumanPlayerPlaying => humanPlayerArea.highlightSuitableCards(event.suitableCards, event.actualCardCount)

    case event: BotPlayerPlaying => playerAreaMap.apply(event.player).updatePlayerLabel()

    case event: CardsWereSwapped =>
      val sb: StringBuilder = new StringBuilder()

      sb.append(MessageEnv.getCardsWereSwappedText(event))
      sb.append(LanguageTranslator.translate(MessageEnv.PhrasesGeneral.ClickAnywhereToContinue))

      showGlobalMessage(sb.toString())

    case event: PronounceWinnerOfRound =>
      val sb: StringBuilder = new StringBuilder()

      sb.append(MessageEnv.getPronounceWinnerOfRoundText(event))
      sb.append(LanguageTranslator.translate(MessageEnv.PhrasesGeneral.ClickAnywhereToContinue))

      showGlobalMessage(sb.toString())

      humanPlayerArea.removeCardEventsAndEffects()

    case event: ShowEndOfGame =>
      val sb: StringBuilder = new StringBuilder()

      sb.append(MessageEnv.getShowEndOfGameText(event))
      sb.append(LanguageTranslator.translate(MessageEnv.PhrasesGeneral.ClickAnywhereToContinue))

      showGlobalMessage(sb.toString())

    case _: GameContinuedFromPause => hideGlobalMessage(globalMessage)

    case event: SettingsLoadFailed => showSettingsLoadError(event.error)

  }
}

object Table {
  val styleTableArea = "-fx-background-image: url('file:assets/table/table.jpg'); -fx-background-size: 102%; -fx-border-color: grey; -fx-padding: 25px"
  val styleBackgroundArea = "-fx-background-image: url('file:assets/table/floor.jpg');"
  val styleEmptyCardArea = "-fx-border-color: grey; -fx-border-style: segments(10, 10, 10, 10) line-cap round"
  val styleGlobalMessage = "-fx-background-color: rgba(0, 0, 0, 0.8); -fx-text-fill: white; -fx-font-size: 32px"

  val spacingGap = 5
  val minSizeBotArea = 150
  val minSizeHumanArea = 250
}
