package de.htwg.se.awol.view

import java.util
import javafx.scene.Node

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.effect.DropShadow
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout._
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.{Font, Text}

object ScalaFXHelloWorld extends JFXApp {
  val imagePlayer: Image = new Image("file:///D:/Desktop/htwg.se.awol/assets/player/player.png", 120, 100, true, true)

  val fontWelcome = new Font(24)

  val labelWelcome: Label = new Label {
    text = "Welcome to the game"
    font = fontWelcome
  }

  val innerPane = new BorderPane(labelWelcome, new ImageView(imagePlayer), new ImageView(imagePlayer), new ImageView(imagePlayer), new ImageView(imagePlayer))
  innerPane.autosize()

  val mainPane = new VBox {
    spacing = 5
    fillWidth = true
    hgrow = Priority.Always
    alignment = Pos.Center
    children = List(innerPane)
  }

  // Initialize Stage
  stage = new PrimaryStage {
    title = "ScalaFX Hello World"
    width = 1200
    height = 768

    scene = new Scene {
      fill = Green

      content = mainPane
    }
  }

  mainPane.prefWidth <== stage.scene().widthProperty()
  mainPane.prefHeight <== stage.scene().heightProperty()

  def getStageCenter: Double = stage.getWidth / 2

  def centerLabel(lb: Label): Unit = lb.setTranslateX(getStageCenter - lb.getWidth)

  def centerImageHorizontally(iv: ImageView): Unit = iv.setX(stage.getWidth / 2 - iv.getImage.getWidth / 2)

  def addPlayerImages(): Unit = {
    val pl: ImageView = new ImageView(imagePlayer)
    centerImageHorizontally(pl)
  }

  println(stage.getWidth / 2)
}
