package de.htwg.se.awol.view.gui

import de.htwg.se.awol.controller.gameController.{CardsChanged, Game, _GameHandler}
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.cardComponents.Deck
import de.htwg.se.awol.model.environmentComponents.{GuiEnv, MessageEnv}

import scala.collection.mutable.ListBuffer
import scala.swing.Reactor
import scalafx.Includes._
import scalafx.embed.swing.SFXPanel
import scalafx.event.ActionEvent
import scalafx.geometry.{HPos, Pos, VPos}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.input.KeyCombination
import scalafx.scene.layout._

class Table(controller: _GameHandler) extends SFXPanel with Reactor {
  listenTo(controller)

  val playerAreaList: ListBuffer[PlayerArea] = new ListBuffer()
  var humanPlayerArea: PlayerArea = _

  val topRow: GridPane = new GridPane {
    alignment = Pos.Center
    vgap = Table.spacingGap
  }
  val rightRow: GridPane = new GridPane {
    alignment = Pos.Center
    vgap = Table.spacingGap
  }
  val bottomRow: GridPane = new GridPane {
    alignment = Pos.Center
    vgap = Table.spacingGap
  }
  val leftRow: GridPane = new GridPane {
    alignment = Pos.Center
    vgap = Table.spacingGap
  }
  val tableArea: GridPane = new GridPane {
    alignment = Pos.Center
    style = Table.styleTableArea
    hgap = Table.spacingGap
    vgap = Table.spacingGap
  }

  val mainPane: VBox = new VBox() {
    alignment = Pos.Center
  }

  val contentPane: StackPane = new StackPane() {
    vgrow = Priority.Always
  }

  val playPane: BorderPane = new BorderPane {
    top = topRow
    right = rightRow
    bottom = bottomRow
    left = leftRow
    center = tableArea
  }

  val globalMessage: Label = new Label {
    maxHeight = Double.PositiveInfinity
    maxWidth = Double.PositiveInfinity
    alignment = Pos.Center
    style = "-fx-background-color: rgba(0, 0, 0, 0.8); -fx-text-fill: white; -fx-font-size: 32px"
  }
  globalMessage.visible = false

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

  def resetPlayerAreas(): Unit = {
    topRow.children.clear()
    rightRow.children.clear()
    bottomRow.children.clear()
    leftRow.children.clear()
    playerAreaList.clear()
    //humanPlayerArea = null
  }

  def createPlayerAreas(): Unit = {
    val playerList = controller.getPlayerList

    var i = 0
    for (player <- playerList) {
      val playerArea = new PlayerArea(player)
      playerArea.setName(player.getPlayerName)

      if (player.isHumanPlayer) {
        humanPlayerArea = playerArea
      }

      playerAreaList.append(playerArea)

      assignPlayerPosition(i, playerArea)

      i += 1
    }
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
        controller.resetHandler(diag.getDeckSize, diag.getPlayerCount) // TODO: Sehr wichtige Funktion, hier ok?

        true
      case _ => false
    }
  }

  def startNewGame(): Unit = {
    if (showAndSetGameOptions()) {
      resetPlayerAreas()
      createPlayerAreas()
      controller.handoutCards()
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

  def updateGameWindow(): Unit = {
    humanPlayerArea.layAssignedCards()
  }

  // Listener
  reactions += {
    case event: CardsChanged => updateGameWindow()
    //case event: CellChanged     => redraw
    //case event: CandidatesChanged => redraw
  }

  // Initializations
  tableArea.add(new Label("Card 1"), 0, 0)
  tableArea.add(new Label("Card 2"), 1, 0)
  tableArea.add(new Label("Card 3"), 2, 0)

  mainPane.children = List(createMenuBar(), contentPane)

  contentPane.children = List(playPane, globalMessage)

  scene = new Scene {
    root = mainPane
  }
}

object Table {
  val styleTableArea = "-fx-border-color: green; -fx-padding: 25px" // -fx-background-color: green;

  val spacingGap = 5
}
