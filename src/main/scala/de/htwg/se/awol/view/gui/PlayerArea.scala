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
  hgrow = Priority.Always
  vgrow = Priority.Always
  style = PlayerArea.stylePlayerArea

  private val activeEffect: Effect = new DropShadow() {
    color = Color.Red
    spread = 0.4
    radius = 20
  }
  private val inactiveEffect: Effect = null.asInstanceOf[javafx.scene.effect.DropShadow]

  private var direction: GuiEnv.Layout.Value = _
  private var isActive: BooleanProperty = BooleanProperty(false)
  private val cardGroupMap: mutable.HashMap[Int, CardStack] = mutable.HashMap()

  val tooltip: Tooltip = new Tooltip() {
    style = PlayerArea.styleTooltip
  }

  private val playerImage: ImageView = new ImageView {
    image = GuiEnv.getImage(GuiEnv.Images.Image_Player)
  }

  private val playerLabel: Label = new Label() {
    text <== LanguageTranslator.bindTranslation(player.getPlayerNameObject).getOrElse(StringProperty(player.getPlayerName))
    alignmentInParent = Pos.Center
    style = PlayerArea.stylePlayerLabel
    effect <== when(isActive) choose activeEffect otherwise inactiveEffect
  }

  private val labelArea: StackPane = new StackPane() {
    children = playerLabel
    maxWidth = 150
    maxHeight = 80
  }

  val passButton: Button = new Button {
    text <== LanguageTranslator.bindTranslation(MessageEnv.Words.Pass).get
    style = PlayerArea.stylePassButton
    visible = player.isHumanPlayer
    disable <== !(isActive && Game.isPassingAllowed)
    alignmentInParent = Pos.CenterRight
    onAction = handle {
      controller.humanPlaying(ListBuffer.empty)
    }
  }

  private var playerActionArea: HBox = _

  val playerBox: StackPane = new StackPane() {
    children = List(playerImage, labelArea, passButton)
    hgrow = Priority.Always
    vgrow = Priority.Always
  }

  // Methods
  def setAsActive(active: Boolean): Unit = {
    isActive.set(active)
  }

  def setAsLeading(): Unit = {
    val leadingImageView: ImageView = GuiEnv.getLeadingImageView()

    if (!labelArea.children.contains(leadingImageView)) {
      labelArea.children.add(leadingImageView)
      leadingImageView.alignmentInParent = Pos.TopRight
    }
  }

  def updatePlayerLabel(): Unit = {
    playerLabel.text <== LanguageTranslator.bindTranslation(player.getPlayerNameObject).getOrElse(StringProperty(player.getPlayerName)) + " (" +  player.cardAmount + ")"
  }

  def setLayout(direction: GuiEnv.Layout.Value): Unit = {
    this.direction = direction

    playerActionArea = new HBox() {
      alignment = Pos.Center
      hgrow = Priority.Always
      vgrow = Priority.Always
      spacing = -25
    }

    direction match {
      case GuiEnv.Layout.TOP  =>
        add(playerBox, 0, 0)
        //add(playerActionArea, 0, 1)
      case GuiEnv.Layout.BOTTOM =>
        add(playerActionArea, 0, 0)
        add(playerBox, 0, 1)
      case GuiEnv.Layout.RIGHT =>
        //add(playerActionArea, 0, 0)
        add(playerBox, 1, 0)
      case GuiEnv.Layout.LEFT =>
        add(playerBox, 0, 0)
        //add(playerActionArea, 1, 0)
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
    cardGroupMap.clear()

    val playerCards = player.getCards.groupBy(_.cardValue)

    for (key <- playerCards.keys.toSeq.sorted) {
      val cardGroup = playerCards.apply(key)

      val cardStack = new CardStack()

      var translateCards = 0
      for (card <- cardGroup) {
        val cardImageView = card.getMySFXImageView

        if (!cardGroupMap.contains(card.cardValue)) {
          cardGroupMap.put(card.cardValue, cardStack)
        }

        cardStack.onMouseEntered = handle {
          tooltip.setText(cardGroup.length + "x " + card.cardName)
          val pos = cardStack.localToScreen(cardStack.getBoundsInLocal)
          tooltip.show(cardStack, pos.getMinX, pos.getMaxY)
          cardStack.setEffect(new DropShadow())
        }
        cardStack.onMouseExited = handle {
          tooltip.hide()
          cardStack.setEffect(null)
        }

        cardStack.addCard(card, cardImageView)
        cardImageView.setTranslateX(translateCards)

        translateCards += 6
      }

      playerActionArea.children.add(cardStack)
    }
  }

  def highlightSuitableCards(suitableCards: Map[Int, ListBuffer[Card]]): Unit = {
    cardGroupMap.foreach(cardGroup => {
      val cardValue: Int = cardGroup._1
      val cardStack: CardStack = cardGroup._2

      if (suitableCards.contains(cardValue)) {
        cardStack.onMouseReleased = handle {
          val pickedCards: ListBuffer[Card] = suitableCards.apply(cardValue)

          val (playingPossible, usedCards) = controller.humanPlaying(pickedCards)
          if (playingPossible) {
            if (cardStack.removeCards(usedCards)) {
              cardStack.setVisible(false)
            }
            updatePlayerLabel()
          }
        }
      } else {
        cardStack.setDisabled()
      }
    })
  }

  def removeCardEventsAndEffects(): Unit = {
    for (cardStack <- cardGroupMap.values) {
      cardStack.resetCards()
    }
  }

  def hidePlayerImage(): Unit = playerImage.visible = false
}

object PlayerArea {
  val stylePlayerArea = "-fx-padding: 5px"
  val stylePlayerLabel = "-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 2px"
  val styleTooltip = "-fx-font-size: 16px"
  val stylePassButton = "-fx-font-size: 20px"
}