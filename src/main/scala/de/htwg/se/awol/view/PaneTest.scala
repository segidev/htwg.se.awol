package de.htwg.se.awol.view

import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.cardComponents.Deck
import de.htwg.se.awol.model.environmentComponents.{GuiEnv, MessageEnv}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.geometry.{HPos, Insets, Pos, VPos}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.input.KeyCombination
import scalafx.scene.layout._

object PaneTest extends JFXApp {
  val playerCount = 2
  val rowDiv = 4

  val spacingPlayerToCards = 10
  val spacingBetweenCards = 5
  val stylePlayerPane = "-fx-padding: 10"
  val stylePlayerArea = "-fx-border-color: grey; -fx-padding: 5"
  val styleTableArea = "-fx-background-color: green"

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
  val tableArea: GridPane = new GridPane {
    alignment = Pos.Center
    style = styleTableArea
    hgap = spacingBetweenCards
  }

  tableArea.add(new Label("Card 1"), 0, 0)
  tableArea.add(new Label("Card 2"), 1, 0)
  tableArea.add(new Label("Card 3"), 2, 0)

  val mainPane: VBox = new VBox() {
    alignment = Pos.Center
  }

  val contentPane = new StackPane()

  val playerPane: BorderPane = new BorderPane {
    top = topRow
    right = rightRow
    bottom = bottomRow
    left = leftRow
    center = tableArea
    style = stylePlayerPane
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
        },
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
        }
      )
    }
  }

  // Player Init
  def addPlayerToTop(player: Button, startIdx: Int): Unit = {
    var playerCardArea = new HBox {
      alignment = Pos.Center
      spacing = spacingBetweenCards
      children = List(new Label("Card 1"), new Label("Card 2"))
    }
    val playerArea = new VBox {
      alignment = Pos.Center
      spacing = spacingPlayerToCards
      children = List(player, playerCardArea)
      style = stylePlayerArea
    }

    topRow.add(playerArea, startIdx, 0)
    GridPane.setHgrow(playerArea, Priority.Always)
    GridPane.setHalignment(playerArea, HPos.Center)
  }

  def addPlayerToRight(player: Button, startIdx: Int): Unit = {
    var playerCardArea = new VBox {
      alignment = Pos.Center
      spacing = spacingBetweenCards
      children = List(new Label("Card 1"), new Label("Card 2"))
    }
    val playerArea = new HBox() {
      alignment = Pos.Center
      spacing = spacingPlayerToCards
      children = List(playerCardArea, player)
      style = stylePlayerArea
    }

    rightRow.add(playerArea, 0, startIdx)
    GridPane.setVgrow(playerArea, Priority.Always)
    GridPane.setValignment(playerArea, VPos.Center)
  }

  def addPlayerToBottom(player: Button, startIdx: Int): Unit = {
    var playerCardArea = new HBox {
      alignment = Pos.Center
      spacing = spacingBetweenCards
      children = List(new Label("Card 1"), new Label("Card 2"))
    }
    val playerArea = new VBox {
      alignment = Pos.Center
      spacing = spacingPlayerToCards
      children = List(playerCardArea, player)
      style = stylePlayerArea
    }

    bottomRow.add(playerArea, startIdx, 0)
    GridPane.setHgrow(playerArea, Priority.Always)
    GridPane.setHalignment(playerArea, HPos.Center)
  }

  def addPlayerToLeft(player: Button, startIdx: Int): Unit = {
    var playerCardArea = new VBox {
      alignment = Pos.Center
      spacing = spacingBetweenCards
      children = List(new Label("Card 1"), new Label("Card 2"))
    }
    val playerArea = new HBox() {
      alignment = Pos.Center
      spacing = spacingPlayerToCards
      children = List(player, playerCardArea)
      style = stylePlayerArea
    }

    leftRow.add(playerArea, 0, startIdx)
    GridPane.setVgrow(playerArea, Priority.Always)
    GridPane.setValignment(playerArea, VPos.Center)
  }

  def resetPlayers(): Unit = {
    topRow.children.clear()
    rightRow.children.clear()
    bottomRow.children.clear()
    leftRow.children.clear()
  }

  def createPlayers(): Unit = {
    var startIdx = 1
    val playerCount = Game.getPlayerCount

    for (i <- 0 until playerCount) {
      val player = new Button("YOU")

      if (i != 0) {
        player.setText("Player " + (i + 1).toString)
      }

      playerCount match {
        case 2 =>
          i % rowDiv match {
            case 0 =>
              addPlayerToBottom(player, startIdx)
            case 1 =>
              addPlayerToTop(player, startIdx)
          }
        case 6 =>
          i match {
            case 0 | 4 =>
              addPlayerToBottom(player, startIdx)
            case 1 =>
              addPlayerToRight(player, startIdx)
            case 2 | 5 =>
              addPlayerToTop(player, startIdx)
            case 3 =>
              addPlayerToLeft(player, startIdx)
              startIdx -= 1
          }
        case _ =>
          i % rowDiv match {
            case 0 =>
              addPlayerToBottom(player, startIdx)
            case 1 =>
              addPlayerToRight(player, startIdx)
            case 2 =>
              addPlayerToTop(player, startIdx)
            case 3 =>
              addPlayerToLeft(player, startIdx)
              startIdx -= 1
          }
      }
    }
  }

  def showGameOptions(): Boolean = {
    val gameOptions: Dialog[Any] = new Dialog()
    gameOptions.title = LanguageTranslator.translate(MessageEnv.Titles.GameOptions)
    val dialogLayout = new VBox()

    // Player Options
    val playerOptions = new HBox {
      spacing = 5
    }

    val toggleGrp: ToggleGroup = new ToggleGroup()

    val radioButton2Players: RadioButton = new RadioButton(LanguageTranslator.translate(MessageEnv.Menues.Players_2)) {
      toggleGroup = toggleGrp
      userData = "2"
      selected = true
    }
    val radioButton4Players: RadioButton = new RadioButton(LanguageTranslator.translate(MessageEnv.Menues.Players_4)) {
      userData = "4"
      toggleGroup = toggleGrp
    }
    val radioButton6Players: RadioButton = new RadioButton(LanguageTranslator.translate(MessageEnv.Menues.Players_6)) {
      userData = "6"
      toggleGroup = toggleGrp
    }
    val radioButton8Players: RadioButton = new RadioButton(LanguageTranslator.translate(MessageEnv.Menues.Players_8)) {
      userData = "8"
      toggleGroup = toggleGrp
    }

    playerOptions.children = List(
      radioButton2Players,
      radioButton4Players,
      radioButton6Players,
      radioButton8Players
    )

    // Deck Options
    val deckOptions = new HBox {
      spacing = 5
      alignment = Pos.CenterLeft
      style = "-fx-padding: 10 0 0 0"
    }

    val labelDeckSize: Label = new Label(LanguageTranslator.translate(MessageEnv.Menues.DeckSize))
    val spinnerDeckSize: Spinner[Int] = new Spinner(Deck.smallCardStackSize, Deck.bigCardStackSize, Deck.smallCardStackSize, 4)

    radioButton6Players.disable <== (spinnerDeckSize.value.get() % 6).isEqualTo(0)

    deckOptions.children = List(
      labelDeckSize,
      spinnerDeckSize
    )

    dialogLayout.children = List(
      playerOptions,
      deckOptions
    )

    val buttonStart = new ButtonType(LanguageTranslator.translate(MessageEnv.Menues.StartGame))
    val buttonCancel = new ButtonType(LanguageTranslator.translate(MessageEnv.Menues.Cancel))

    gameOptions.getDialogPane.setContent(dialogLayout)
    gameOptions.getDialogPane.buttonTypes = Seq (
      buttonStart,
      buttonCancel
    )
    //gameOptions.dialogPane.value.children()
    val result = gameOptions.showAndWait()

    result match {
      case Some(`buttonStart`) =>
        Game.setPlayerCount(toggleGrp.selectedToggle.value.getUserData.toString.toInt)
        true
      case _ => false
    }
  }

  def startNewGame(): Unit = {
    if (showGameOptions()) {
      resetPlayers()
      createPlayers()
    }
  }

  def exitApplication(): Unit = {
    val buttonYes = new ButtonType(LanguageTranslator.translate(MessageEnv.Words.Yes))
    val buttonNo = new ButtonType(LanguageTranslator.translate(MessageEnv.Words.No))

    val result = new Alert(AlertType.Confirmation) {
      initOwner(stage)
      title = LanguageTranslator.translate(MessageEnv.Titles.QuitGame)
      headerText = LanguageTranslator.translate(MessageEnv.Questions.QuitGame)
      buttonTypes = Seq(buttonYes, buttonNo)
    }.showAndWait()

    result match {
      case Some(`buttonYes`) => sys.exit(0)
      case _ =>
    }
  }

  // Initializations
  mainPane.children = List(createMenuBar(), contentPane)
  VBox.setVgrow(contentPane, Priority.Always)
  contentPane.children = List(playerPane, globalMessage)

  stage = new PrimaryStage {
    title = "Pane Test"
    width = 1200
    height = 768

    scene = new Scene {
      root = mainPane
    }
  }
}
