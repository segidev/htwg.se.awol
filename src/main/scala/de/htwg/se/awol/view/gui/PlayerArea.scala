package de.htwg.se.awol.view.gui

import javafx.scene.effect.Glow

import de.htwg.se.awol.model.environmentComponents.GuiEnv
import de.htwg.se.awol.model.playerComponent.Player

import scalafx.Includes._
import scalafx.animation.Timeline
import scalafx.beans.property.BooleanProperty
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
  var isActive: BooleanProperty = new BooleanProperty()

  val tooltip: Tooltip = new Tooltip() {
    style = PlayerArea.styleTooltip
  }

  private val playerImage: ImageView = new ImageView(GuiEnv.getImage(GuiEnv.Images.Image_Player))

  private val playerLabel: Label = new Label() {
    alignment = Pos.BottomCenter
    style <== when(isActive) choose PlayerArea.stylePlayerLabelActive otherwise PlayerArea.stylePlayerLabelInactive
  }

  private var playerActionArea: HBox = _

  val playerBox: StackPane = new StackPane() {
    alignment = Pos.Center
    children = List(playerImage, playerLabel)
  }

  // Methods
  def setAsActive(active: Boolean): Unit = isActive.set(active)

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
        add(playerActionArea, 0, 0)
        add(playerBox, 1, 0)
      case GuiEnv.Layout.LEFT =>
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
        val cardImg = new ImageView(card.getMySFXImage)

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

  def hideHumanPlayerItems(): Unit = {playerImage.visible = false}

  def setName(str: String): Unit = playerLabel.setText(str)
}

object PlayerArea {
  val stylePlayerArea = "-fx-background-color: rgb(220, 220, 220); -fx-padding: 5px"
  val stylePlayerLabelInactive = "-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 2px"
  val stylePlayerLabelActive = "-fx-background-color: rgb(150, 0, 0); -fx-text-fill: white; -fx-font-size: 22px; -fx-padding: 16px"
  val styleTooltip = "-fx-font-size: 16px"
}