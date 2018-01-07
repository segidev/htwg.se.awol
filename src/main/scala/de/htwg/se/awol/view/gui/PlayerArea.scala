package de.htwg.se.awol.view.gui

import de.htwg.se.awol.model.environmentComponents.GuiEnv
import de.htwg.se.awol.model.playerComponent.Player

import scalafx.geometry.Pos
import scalafx.scene.control.Label
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout._

class PlayerArea(private val player: Player) extends GridPane {
  alignment = Pos.Center
  style = PlayerArea.stylePlayerArea
  private var direction: GuiEnv.Layout.Value = _
  private var isVertical: Boolean = false

  private val playerImage: ImageView = new ImageView(GuiEnv.getImage(GuiEnv.Images.Image_Player)) {
    preserveRatio = true
    fitWidth = 100
  }

  private val playerLabel: Label = new Label() {
    alignment = Pos.BottomCenter
    style = "-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 2px"
  }

  private var myCardArea: Pane = _

  val playerBox: StackPane = new StackPane() {
    alignment = Pos.Center
    children = List(playerImage, playerLabel)
  }

  // Methods
  def setLayout(direction: GuiEnv.Layout.Value): Unit = {
    this.direction = direction

    myCardArea = new HBox() {
      alignment = Pos.Center
      spacing = 20
    }

    direction match {
      case GuiEnv.Layout.TOP  =>
        add(playerBox, 0, 0)
        add(myCardArea, 0, 1)
      case GuiEnv.Layout.BOTTOM =>
        add(myCardArea, 0, 0)
        add(playerBox, 0, 1)
      case GuiEnv.Layout.RIGHT =>
        isVertical = true

        add(myCardArea, 0, 0)
        add(playerBox, 1, 0)
      case GuiEnv.Layout.LEFT =>
        isVertical = true

        add(playerBox, 0, 0)
        add(myCardArea, 1, 0)
      case _ => throw new MatchError("Layout direction not defined")
    }

    alignImageToDirection(playerImage)
  }

  def alignImageToDirection(imageView: ImageView): Unit = {
    direction match {
      case GuiEnv.Layout.TOP => imageView.scaleY = -1
      case GuiEnv.Layout.RIGHT => imageView.rotate = -90
      case GuiEnv.Layout.LEFT => imageView.rotate = 90
      case _ =>
    }
  }

  def layAssignedCards(): Unit = {
    myCardArea.children.clear()

    val playerCards = player.getCards.groupBy(_.cardValue)

    var translateGroups = 0
    for (key <- playerCards.keys.toSeq.sorted) {
      val cardGroup = playerCards.apply(key)

      val stackPane = new StackPane() {
        alignment = Pos.Center

      }

      var translateCards = 0
      for (card <- cardGroup) {
        val cardImg = new ImageView(GuiEnv.getCardImage(card))
        alignImageToDirection(cardImg)

        stackPane.children.add(cardImg)
        cardImg.setTranslateX(translateCards)

        translateCards += 5
      }

      myCardArea.children.add(stackPane)
    }

    println(playerCards)
    /*for (card <- player.getCards) {
      println(player.getPlayerName)
      val cardImg: Image = GuiEnv.getCardImage(card)
      myCardArea.children.add(new ImageView(cardImg))
    }*/
  }

  def setName(str: String): Unit = playerLabel.setText(str)
}

object PlayerArea {
  val stylePlayerArea = "-fx-border-color: grey; -fx-padding: 5"
}