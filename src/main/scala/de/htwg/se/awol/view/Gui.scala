package de.htwg.se.awol.view

import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.environmentComponents.{GuiEnv, MessageEnv}

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.effect.DropShadow
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.{KeyCombination, MouseEvent}
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.layout._
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.{Font, Text}

object Gui extends JFXApp {
  val fontWelcome = new Font(24)

  val labelWelcome: Label = new Label {
    text = LanguageTranslator.translate(MessageEnv.Phrases.WelcomeToTheGame)
    font = fontWelcome
  }

  val innerPane = new BorderPane()
  val tablePane = new BorderPane()
  val playerCardPane = new HBox {
    spacing = 10
  }

  val menuBar: MenuBar = new MenuBar {
    menus = List(
      new Menu(LanguageTranslator.translate(MessageEnv.Menues.File)) {

        items = List(
          new MenuItem(LanguageTranslator.translate(MessageEnv.Menues.NewGame)) {
            accelerator = KeyCombination.keyCombination("Ctrl + N")
            onAction = {
              e: ActionEvent => {
                playerCardPane.children = List(
                  new ImageView(GuiEnv.getImage(GuiEnv.Images.Image_Club_2)),
                  new ImageView(GuiEnv.getImage(GuiEnv.Images.Image_Spade_King))
                )
                playerCardPane.alignment = Pos.Center
              }
            }
          },
          new SeparatorMenuItem(),
          new MenuItem(LanguageTranslator.translate(MessageEnv.Menues.Quit)) {
            accelerator = KeyCombination.keyCombination("Ctrl + Q")
            onAction = {
              e: ActionEvent => askForExit()
            }
          }
        )
      }
    )
  }

  val mainPane: VBox = new VBox {
    spacing = 5
    fillWidth = true
    hgrow = Priority.Always
    alignment = Pos.Center
    children = List(menuBar, innerPane)
  }

  // Methods
  def askForExit(): Unit = {
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

  // Initialize Stage
  stage = new PrimaryStage {
    title = LanguageTranslator.translate(MessageEnv.Titles.GameTitle)
    width = 1200
    height = 768

    scene = new Scene {
      fill = Green

      content = mainPane
    }
  }

  val topPlayer: ImageView = new ImageView {
    image = GuiEnv.getImage(GuiEnv.Images.Image_Player)
    alignmentInParent = Pos.Center
    scaleY = -1
  }


  val rightPlayer: ImageView = new ImageView {
    image = GuiEnv.getImage(GuiEnv.Images.Image_Player)
    alignmentInParent = Pos.Center
    rotate = -90
  }

  val bottomPlayer: ImageView = new ImageView {
    image = GuiEnv.getImage(GuiEnv.Images.Image_Player)
    alignmentInParent = Pos.Center
  }

  val leftPlayer: ImageView = new ImageView {
    image = GuiEnv.getImage(GuiEnv.Images.Image_Player)
    alignmentInParent = Pos.Center
    rotate = 90
  }

  innerPane.center = tablePane
  innerPane.top = topPlayer
  innerPane.right = rightPlayer
  innerPane.bottom = bottomPlayer
  innerPane.left = leftPlayer
  innerPane.prefWidth <== stage.scene().widthProperty()
  innerPane.prefHeight <== stage.scene().heightProperty()

  tablePane.center = labelWelcome
  tablePane.bottom = playerCardPane

  topPlayer.handleEvent(MouseEvent.MouseClicked) {
    ev: MouseEvent => {
      println(ev.source)
    }
  }

  println(stage.getHeight)

  mainPane.prefWidth <== stage.scene().widthProperty()
  mainPane.prefHeight <== stage.scene().heightProperty()

  def getStageCenter: Double = stage.getWidth / 2

  def centerLabel(lb: Label): Unit = lb.setTranslateX(getStageCenter - lb.getWidth)

  def centerImageHorizontally(iv: ImageView): Unit = iv.setX(stage.getWidth / 2 - iv.getImage.getWidth / 2)

  /*def addPlayerImages(): Unit = {
    val pl: ImageView = new ImageView(imagePlayer)
    centerImageHorizontally(pl)
  }*/

  println(stage.getWidth / 2)
}
