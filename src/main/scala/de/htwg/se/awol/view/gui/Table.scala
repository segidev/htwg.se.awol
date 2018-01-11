package de.htwg.se.awol.view.gui

import javafx.scene.effect.Glow

import de.htwg.se.awol.controller.gameController._
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.environmentComponents.{GuiEnv, MessageEnv}
import de.htwg.se.awol.model.playerComponent.Player

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.swing.Reactor
import scalafx.Includes._
import scalafx.embed.swing.SFXPanel
import scalafx.event.ActionEvent
import scalafx.geometry.{HPos, Pos, VPos}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.image.ImageView
import scalafx.scene.input.KeyCombination
import scalafx.scene.layout._
import scalafx.scene.text.TextAlignment

class Table(controller: _GameHandler) extends SFXPanel with Reactor {
  listenTo(controller)

  val playerAreaMap: mutable.HashMap[Player, PlayerArea] = mutable.HashMap()
  var humanPlayerArea: PlayerArea = _

  val globalMessage: Label = new Label {
    maxHeight = Double.PositiveInfinity
    maxWidth = Double.PositiveInfinity
    alignment = Pos.Center
    textAlignment = TextAlignment.Center
    style = Table.styleGlobalMessage
    visible = false

    onMouseReleased = handle {
      hideGlobalMessage(globalMessage)
      controller.callNextActionByState()
    }
  }

  // Layout
  val mainPane: VBox = new VBox() {
    alignment = Pos.Center
  }

  val contentPane: StackPane = new StackPane() {
    vgrow = Priority.Always
  }

  // Space for PlayerAreas
  val topRow: GridPane = new GridPane {
    alignment = Pos.Center
  }
  val rightRow: GridPane = new GridPane {
    alignment = Pos.Center
  }
  val bottomRow: GridPane = new GridPane {
    alignment = Pos.Center
  }
  val leftRow: GridPane = new GridPane {
    alignment = Pos.Center
  }

  // Table Area
  val tableArea: HBox = new HBox {
    alignment = Pos.Center
    style = Table.styleTableArea
    spacing = Table.spacingGap
  }

  val cardSize: (Double, Double) = GuiEnv.getCardSize
  val tableCard_1: VBox = new VBox() {style=Table.styleEmptyCardArea; minWidth=cardSize._1; maxHeight=cardSize._2}
  val tableCard_2: VBox = new VBox() {style=Table.styleEmptyCardArea; minWidth=cardSize._1; maxHeight=cardSize._2}
  val tableCard_3: VBox = new VBox() {style=Table.styleEmptyCardArea; minWidth=cardSize._1; maxHeight=cardSize._2}
  val tableCard_4: VBox = new VBox() {style=Table.styleEmptyCardArea; minWidth=cardSize._1; maxHeight=cardSize._2}

  val playPane: BorderPane = new BorderPane {
    top = topRow
    right = rightRow
    bottom = bottomRow
    left = leftRow
    center = tableArea
  }

  def showGlobalMessage(msg: String): Unit = {
    globalMessage.text = msg
    globalMessage.visible = true
  }

  def hideGlobalMessage(globalMessage: Label): Unit = {
    globalMessage.text = ""
    globalMessage.visible = false
  }

  def createMenuBar(): MenuBar = {
    val playerOptionsGroup = new ToggleGroup()

    new MenuBar {
      menus = List(
        new Menu(LanguageTranslator.translate(MessageEnv.Menues.File)) {
          items = List(
            new MenuItem(LanguageTranslator.translate(MessageEnv.Menues.NewGame)) {
              accelerator = KeyCombination.keyCombination("Ctrl + N")
              onAction = {
                e: ActionEvent => startNewGame()
              }
            },
            new SeparatorMenuItem(),
            new MenuItem(LanguageTranslator.translate(MessageEnv.Menues.Quit)) {
              accelerator = KeyCombination.keyCombination("Ctrl + Q")
              onAction = {
                e: ActionEvent => exitApplication()
              }
            }
          )
        }/*,
        new Menu(LanguageTranslator.translate(MessageEnv.Menues.Options)) {
          items = List(
            new RadioMenuItem(LanguageTranslator.translate(MessageEnv.Menues.Players_2)) {
              toggleGroup = playerOptionsGroup
              selected = true
            },
            new RadioMenuItem(LanguageTranslator.translate(MessageEnv.Menues.Players_4)) {
              toggleGroup = playerOptionsGroup
            },
            new RadioMenuItem(LanguageTranslator.translate(MessageEnv.Menues.Players_6)) {
              toggleGroup = playerOptionsGroup
            },
            new RadioMenuItem(LanguageTranslator.translate(MessageEnv.Menues.Players_8)) {
              toggleGroup = playerOptionsGroup
            },
            new SeparatorMenuItem()

          )
        }*/
      )
    }
  }

  def addPlayerToTop(playerArea: PlayerArea, startIdx: Int): Unit = {
    playerArea.setLayout(GuiEnv.Layout.TOP)
    topRow.add(playerArea, startIdx, 0)
    GridPane.setHgrow(playerArea, Priority.Always)
    GridPane.setHalignment(playerArea, HPos.Center)
  }

  def addPlayerToRight(playerArea: PlayerArea, startIdx: Int): Unit = {
    playerArea.setLayout(GuiEnv.Layout.RIGHT)
    rightRow.add(playerArea, 0, startIdx)
    GridPane.setVgrow(playerArea, Priority.Always)
    GridPane.setValignment(playerArea, VPos.Center)
  }

  def addPlayerToBottom(playerArea: PlayerArea, startIdx: Int): Unit = {
    playerArea.setLayout(GuiEnv.Layout.BOTTOM)
    bottomRow.add(playerArea, startIdx, 0)
    GridPane.setHgrow(playerArea, Priority.Always)
    GridPane.setHalignment(playerArea, HPos.Center)
  }

  def addPlayerToLeft(playerArea: PlayerArea, startIdx: Int): Unit = {
    playerArea.setLayout(GuiEnv.Layout.LEFT)
    leftRow.add(playerArea, 0, startIdx)
    GridPane.setVgrow(playerArea, Priority.Always)
    GridPane.setValignment(playerArea, VPos.Center)
  }

  def resetLayoutAndVariables(): Unit = {
    topRow.children.clear()
    rightRow.children.clear()
    bottomRow.children.clear()
    leftRow.children.clear()
    tableArea.children.clear()

    playerAreaMap.clear()

    //humanPlayerArea = null
  }

  def createPlayerAreas(): Unit = {
    val playerList = controller.getPlayerList

    var i = 0
    for (player <- playerList) {
      val playerArea = new PlayerArea(player, controller)
      playerArea.setName(player.getPlayerName)

      if (player.isHumanPlayer) {
        humanPlayerArea = playerArea
        humanPlayerArea.toggleHumanPlayerItems()
      }

      playerAreaMap.put(player, playerArea)

      assignPlayerPosition(i, playerArea)

      i += 1
    }
  }

  def clearCardsFromTable(): Unit = {
    tableCard_1.children.clear()
    tableCard_2.children.clear()
    tableCard_3.children.clear()
    tableCard_4.children.clear()
  }

  def assignPlayerPosition(idx: Int, playerArea: PlayerArea): Unit = {
    idx match {
      case 0 => addPlayerToBottom(playerArea, 0)
      case 1 => addPlayerToTop(playerArea, 0)
      case 2 => addPlayerToLeft(playerArea, 0)
      case 3 => addPlayerToRight(playerArea, 0)
      case 4 => addPlayerToLeft(playerArea, 1)
      case 5 => addPlayerToRight(playerArea, 1)
      case 6 => addPlayerToTop(playerArea, 2)
      case 7 => addPlayerToTop(playerArea, 1)
    }
  }

  def showAndSetGameOptions(): Boolean = {
    val diag = new GameOptionsDialog()

    diag.showAndWait() match {
      case Some(diag.buttonStart) =>
        Game.setDeckSize(diag.getDeckSize)
        Game.setPlayerCount(diag.getPlayerCount)
        controller.initNewGame(diag.getDeckSize, diag.getPlayerCount) // TODO: Sehr wichtige Funktion, hier ok?

        true
      case _ => false
    }
  }

  def startNewGame(): Unit = {
    if (showAndSetGameOptions()) {
      showGlobalMessage("Handing out cards\nClick anywhere to start...")
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

  def updatePlayerView(): Unit = {
    resetLayoutAndVariables()

    tableArea.children.add(tableCard_1)
    tableArea.children.add(tableCard_2)
    tableArea.children.add(tableCard_3)
    tableArea.children.add(tableCard_4)

    createPlayerAreas()
  }

  def updateCardView(): Unit = {
    humanPlayerArea.showCardsOnTable()
  }

  def updatePlayerHints(): Unit = {
    playerAreaMap.values.foreach(_.setAsActive(false))

    playerAreaMap.get(Game.getActivePlayer) match {
      case Some(p) => p.setAsActive(true)
      case _ => throw new MatchError("Active player seems to not be existent in the game")
    }
  }

  def updateTableView(): Unit = {
    clearCardsFromTable()

    var i = 0
    for (card <- controller.getLatestCardsOnTable) {
      i match {
        case 0 => tableCard_1.children.add(card.getMySFXImageView)
        case 1 => tableCard_2.children.add(card.getMySFXImageView)
        case 2 => tableCard_3.children.add(card.getMySFXImageView)
        case 3 => tableCard_4.children.add(card.getMySFXImageView)
      }

      i += 1
    }
  }

  def updateHumanView(suitableCards: Map[Int, ListBuffer[Card]]): Unit = {
    if (suitableCards.isEmpty) {
      showGlobalMessage("No suitable cards found!")
    } else {
      humanPlayerArea.highlightSuitableCards(suitableCards)
      println(suitableCards)
    }
  }

  // Listener
  reactions += {
    case event: CardsChanged => updateCardView()
    case event: PlayersChanged => updatePlayerView()
    case event: ActivePlayerChanged => updatePlayerHints()
    case event: CardsOnTableChanged => updateTableView()
    case event: HumanPlayerPlaying => updateHumanView(event.suitableCards)
    //case event: CellChanged     => redraw
    //case event: CandidatesChanged => redraw
  }

  // Initializations
  mainPane.children = List(createMenuBar(), contentPane)

  contentPane.children = List(playPane, globalMessage)

  scene = new Scene {
    root = mainPane
  }
}

object Table {
  val styleTableArea = "-fx-background-color: green; -fx-padding: 25px"
  val styleEmptyCardArea = "-fx-border-color: grey; -fx-border-style: segments(10, 10, 10, 10) line-cap round"
  val styleGlobalMessage = "-fx-background-color: rgba(0, 0, 0, 0.8); -fx-text-fill: white; -fx-font-size: 32px"

  val spacingGap = 5
}
