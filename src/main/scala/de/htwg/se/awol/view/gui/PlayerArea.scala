package de.htwg.se.awol.view.gui

import de.htwg.se.awol.controller.gameController.{Game, _GameHandler}
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.environmentComponents.{GuiEnv, MessageEnv}
import de.htwg.se.awol.model.playerComponent.Player

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scalafx.Includes._
import scalafx.beans.property.{BooleanProperty, StringProperty}
import scalafx.geometry.Pos
import scalafx.scene.control.{Button, Label, Tooltip}
import scalafx.scene.effect.{DropShadow, Effect}
import scalafx.scene.image.ImageView
import scalafx.scene.layout._
import scalafx.scene.paint.Color

class PlayerArea(private val player: Player, controller: _GameHandler) extends GridPane {
  alignment = Pos.Center
  style = PlayerArea.stylePlayerArea

  private val activeEffect: Effect = new DropShadow() {
    color = Color.Red
    spread = 0.4
    radius = 20
  }
  private val inactiveEffect: Effect = null.asInstanceOf[javafx.scene.effect.DropShadow]

  private var direction: GuiEnv.Layout.Value = _
  private var isActive: BooleanProperty = BooleanProperty(false)
  private val cardGroupMap: mutable.HashMap[Int, ListBuffer[ImageView]] = mutable.HashMap()

  val tooltip: Tooltip = new Tooltip() {
    style = PlayerArea.styleTooltip
  }

  private val playerImage: ImageView = new ImageView(GuiEnv.getImage(GuiEnv.Images.Image_Player))

  private val playerLabel: Label = new Label() {
    text <== LanguageTranslator.bindTranslation(player.getPlayerNameObject).getOrElse(StringProperty(player.getPlayerName))
    alignment = Pos.BottomCenter
    style = PlayerArea.stylePlayerLabelInactive
    effect <== when(isActive) choose activeEffect otherwise inactiveEffect
  }

  val passButton: Button = new Button { // TODO: Bei runden start wird er ativ nach Klick auf Karte, Bei passen direkt ausgrauen, nicht warten
    text <== LanguageTranslator.bindTranslation(MessageEnv.Words.Pass).get
    style = PlayerArea.stylePassButton
    onAction = handle { println("You passed!") }
    visible = player.isHumanPlayer
    disable <== !(isActive && Game.isPassingAllowed)
    alignmentInParent = Pos.CenterRight
    onAction = handle {
      controller.humanPlaying(ListBuffer.empty)
    }
  }

  private var playerActionArea: HBox = _

  val playerBox: StackPane = new StackPane() {
    alignment = Pos.Center
    children = List(playerImage, playerLabel, passButton)
  }

  // Methods
  def setAsActive(active: Boolean): Unit = {
    isActive.set(active)
  }

  def setLeadingImageToPlayer(): Unit = {
    val leadingImageView: ImageView = GuiEnv.getLeadingImageView()

    if (!playerBox.children.contains(leadingImageView)) {
      playerBox.children.add(leadingImageView)
      leadingImageView.translateX = playerLabel.getWidth / 2
      leadingImageView.translateY = -playerLabel.getHeight / 2
    }
  }

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
        val cardImageView = card.getMySFXImageView

        if (!cardGroupMap.contains(card.cardValue)) {
          cardGroupMap.put(card.cardValue, ListBuffer[ImageView]())
        }

        cardGroupMap.apply(card.cardValue).append(cardImageView)

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

        stackPane.children.add(cardImageView)
        cardImageView.setTranslateX(translateCards)

        translateCards += 6
      }

      playerActionArea.children.add(stackPane)
    }
  }

  def highlightSuitableCards(suitableCards: Map[Int, ListBuffer[Card]]): Unit = {
    cardGroupMap.foreach(_._2.foreach(_.opacity = 0.2)) //

    for (key <- suitableCards.keys) {
      for (cardViewImage <- cardGroupMap.apply(key)) {
        cardViewImage.opacity = 1

        cardViewImage.onMouseReleased = handle {
          removeCardEventsAndEffects()

          val pickedCards: ListBuffer[Card] = suitableCards.apply(key)

          controller.humanPlaying(pickedCards)
        }
      }
      //cardGroupMap.apply(key).foreach(_.opacity = 1)
    }
  }

  def removeCardEventsAndEffects(): Unit = {
    for (cardImageViewGroup <- cardGroupMap.values) {
      for (cardImageView <- cardImageViewGroup) {
        cardImageView.opacity = 1
        cardImageView.onMouseReleased = handle()
      }
    }
  }

  def toggleHumanPlayerItems(): Unit = playerImage.visible = false

  //def setName(str: String): Unit = playerLabel.text = ""
}

object PlayerArea {
  val stylePlayerArea = "-fx-background-color: rgb(220, 220, 220); -fx-padding: 5px"
  val stylePlayerLabelInactive = "-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 2px"
  val stylePlayerLabelActive = "-fx-background-color: rgb(150, 0, 0); -fx-text-fill: white;  -fx-font-size: 18px; -fx-padding: 2px"
  val styleTooltip = "-fx-font-size: 16px"
  val stylePassButton = "-fx-font-size: 20px"
}