package de.htwg.se.awol.view.gui

import de.htwg.se.awol.model.environmentComponents.GuiEnv
import de.htwg.se.awol.model.playerComponent.Player

import scalafx.Includes._
import scalafx.animation.Timeline
import scalafx.geometry.Pos
import scalafx.scene.control.{Label, Tooltip}
import scalafx.scene.effect.DropShadow
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout._

class PlayerArea(private val player: Player) extends GridPane {
  alignment = Pos.Center
  style = PlayerArea.stylePlayerArea
  private var direction: GuiEnv.Layout.Value = _
  private var isVertical: Boolean = false

  val tooltip = new Tooltip() {
    style = PlayerArea.styleTooltip
  }

  private val playerImage: ImageView = new ImageView(GuiEnv.getImage(GuiEnv.Images.Image_Player)) {
    preserveRatio = true
    fitWidth = 100
  }

  private val playerLabel: Label = new Label() {
    alignment = Pos.BottomCenter
    style = "-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 2px"
  }

  private var playerActionArea: HBox = _

  val playerBox: StackPane = new StackPane() {
    alignment = Pos.Center
    children = List(playerImage, playerLabel)
  }

  // Methods
  def setLayout(direction: GuiEnv.Layout.Value): Unit = {
    this.direction = direction

    playerActionArea = new HBox() {
      alignment = Pos.Center
      spacing = -25
    }

    direction match {
      case GuiEnv.Layout.TOP  =>
        add(playerBox, 0, 0)
        add(playerActionArea, 0, 1)
      case GuiEnv.Layout.BOTTOM =>
        add(playerActionArea, 0, 0)
        add(playerBox, 0, 1)
      case GuiEnv.Layout.RIGHT =>
        isVertical = true

        add(playerActionArea, 0, 0)
        add(playerBox, 1, 0)
      case GuiEnv.Layout.LEFT =>
        isVertical = true

        add(playerBox, 0, 0)
        add(playerActionArea, 1, 0)
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

  def showCardsOnTable(): Unit = {
    playerActionArea.children.clear()

    val playerCards = player.getCards.groupBy(_.cardValue)

    for (key <- playerCards.keys.toSeq.sorted) {
      val cardGroup = playerCards.apply(key)

      val stackPane = new StackPane() {
        alignment = Pos.Center
      }

      var translateCards = 0
      for (card <- cardGroup) {
        val cardImg = new ImageView(GuiEnv.getCardImage(card))

        stackPane.onMouseEntered = handle {
          tooltip.setText(cardGroup.length + "x " + card.cardName)
          val pos = stackPane.localToScreen(stackPane.getBoundsInLocal)
          tooltip.show(stackPane, pos.getMinX, pos.getMaxY)
          stackPane.setEffect(new DropShadow())
        }
        stackPane.onMouseExited = handle {
          tooltip.hide()
          stackPane.setEffect(null)
        }
        stackPane.onMouseReleased = handle {

        }

        stackPane.children.add(cardImg)
        cardImg.setTranslateX(translateCards)

        translateCards += 6
      }

      playerActionArea.children.add(stackPane)
    }
  }

  def setName(str: String): Unit = playerLabel.setText(str)
}

object PlayerArea {
  val stylePlayerArea = "-fx-border-color: grey; -fx-padding: 5"
  val styleTooltip = "-fx-font-size: 16px"
}