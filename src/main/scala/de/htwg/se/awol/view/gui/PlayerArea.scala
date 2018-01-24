package de.htwg.se.awol.view.gui

import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.controller.gameController.handler._TGameHandler
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.environmentComponents.{GuiEnv, MessageEnv}
import de.htwg.se.awol.model.playerComponent.Player

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scalafx.Includes._
import scalafx.beans.property.{BooleanProperty, StringProperty}
import scalafx.geometry.Pos
import scalafx.scene.control._
import scalafx.scene.effect.{DropShadow, Effect}
import scalafx.scene.image.ImageView
import scalafx.scene.layout._
import scalafx.scene.paint.Color

class PlayerArea(private val player: Player, controller: _TGameHandler) extends GridPane {
  alignment = Pos.Center
  hgrow = Priority.Always
  vgrow = Priority.Always
  style = PlayerArea.stylePlayerArea

  private val activeEffect: Effect = new DropShadow() {
    color = Color.Red
    spread = PlayerArea.activeEffectSpread
    radius = PlayerArea.activeEffectRadius
  }
  private val inactiveEffect: Effect = null.asInstanceOf[javafx.scene.effect.DropShadow]
  private val cardGlowEffect: Effect = new DropShadow() {
    color = Color.White
    spread = PlayerArea.cardGlowEffectSpread
    radius = PlayerArea.cardGlowEffectRadius
  }

  private var direction: GuiEnv.Layout.Value = _
  private val isActive: BooleanProperty = BooleanProperty(false)
  private val cardGroupMap: mutable.HashMap[Int, CardStack] = mutable.HashMap()

  val tooltip: Tooltip = new Tooltip() {
    style = PlayerArea.styleTooltip
  }

  private val playerImage: ImageView = new ImageView {
    image = GuiEnv.getPlayerImage
  }

  private val playerLabel: Label = new Label() {
    text <== LanguageTranslator.bindTranslation(player.getPlayerNameObject).getOrElse(StringProperty(player.getPlayerName))
    alignmentInParent = Pos.Center
    style = PlayerArea.stylePlayerLabel
    effect <== when(isActive) choose activeEffect otherwise inactiveEffect
  }

  private val labelArea: StackPane = new StackPane() {
    children = playerLabel
    maxWidth = PlayerArea.labelWidth
    maxHeight = PlayerArea.labelHeight
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

  // Popup
  private val popup = new CardAmountPopup(this)

  // Methods
  def setAsActive(active: Boolean): Unit = {
    isActive.set(active)
  }

  def setAsLeading(): Unit = {
    val leadingImageView: ImageView = GuiEnv.getLeadingImageView

    if (!labelArea.children.contains(leadingImageView)) {
      labelArea.children.add(leadingImageView)
      leadingImageView.alignmentInParent = Pos.TopRight
    }
  }

  def updatePlayerLabel(): Unit = {
    playerLabel.text <== LanguageTranslator.bindTranslation(player.getPlayerNameObject).getOrElse(StringProperty(player.getPlayerName)) +
      " (" +  player.cardAmount + ")"
  }

  def setLayout(direction: GuiEnv.Layout.Value): Unit = {
    this.direction = direction

    playerActionArea = new HBox() {
      alignment = Pos.Center
      hgrow = Priority.Always
      vgrow = Priority.Always
      spacing = PlayerArea.bigSpacing
    }

    direction match {
      case GuiEnv.Layout.TOP  =>
        add(playerBox, 0, 0)
      case GuiEnv.Layout.BOTTOM =>
        add(playerActionArea, 0, 0)
        add(playerBox, 0, 1)
      case GuiEnv.Layout.RIGHT =>
        add(playerBox, 1, 0)
      case GuiEnv.Layout.LEFT =>
        add(playerBox, 0, 0)
      case _ => throw new MatchError("Layout direction not defined")
    }

    alignImageToDirection(playerImage)
  }

  def alignImageToDirection(imageView: ImageView): Unit = {
    direction match {
      case GuiEnv.Layout.TOP => imageView.scaleY = PlayerArea.alignTopImage
      case GuiEnv.Layout.RIGHT => imageView.rotate = PlayerArea.alignRightSide
      case GuiEnv.Layout.LEFT => imageView.rotate = PlayerArea.alignLeftSide
      case _ =>
    }
  }

  def showCardsOnTable(): Unit = {
    playerActionArea.children.clear()
    cardGroupMap.clear()

    val playerCards = player.getCards.groupBy(_.cardValue)

    playerCards.keys.toSeq.sorted.foreach(cardValue => {
      val cardGroup = playerCards.apply(cardValue)

      val cardStack = new CardStack()

      cardGroup.sortBy(_.cardColorName).foreach(card => {
        val cardImageView = GuiEnv.getCardImageView(card)

        if (!cardGroupMap.contains(card.cardValue)) {
          cardGroupMap.put(card.cardValue, cardStack)
        }

        cardStack.onMouseEntered = handle {
          tooltip.setText(cardStack.getCardImageViews.length + "x " + card.cardValueName)
          val pos = cardStack.localToScreen(cardStack.getBoundsInLocal)
          tooltip.show(cardStack, pos.getMinX, pos.getMaxY)
          cardStack.setEffect(cardGlowEffect)
        }
        cardStack.onMouseExited = handle {
          tooltip.hide()
          cardStack.setEffect(null)
        }

        cardStack.addCard(card, cardImageView)
      })

      playerActionArea.children.add(cardStack)
    })
  }

  def highlightSuitableCards(suitableCards: Map[Int, ListBuffer[Card]], actualCardCount: Int): Unit = {
    cardGroupMap.foreach(cardGroup => {
      val cardValue: Int = cardGroup._1
      val cardStack: CardStack = cardGroup._2

      if (suitableCards.contains(cardValue)) {
        cardStack.onMouseReleased = handle {

          if (actualCardCount == 0) {
            if (cardStack.getCardImageViews.lengthCompare(1) == 0) {
              putCards(suitableCards, cardStack, 1, cardValue)
            } else {
              popup.init(cardStack, suitableCards, cardValue)
            }
          } else {
            putCards(suitableCards, cardStack, actualCardCount, cardValue)
          }
        }
      } else {
        cardStack.setDisabled()
      }
    })
  }

  def putCards(suitableCards: Map[Int, ListBuffer[Card]], cardStack: CardStack, cardAmount: Int, cardValue: Int): Boolean = {
    if (cardAmount != -1) {
      val pickedCards: ListBuffer[Card] = suitableCards.apply(cardValue)

      controller.humanPlaying(pickedCards.take(cardAmount)) match {
        case Some(usedCards) =>
          popup.resetPopup()

          if (cardStack.removeCards(usedCards)) {
            cardStack.setVisible(false)
          }
          updatePlayerLabel()
          true
        case _ => false
      }
    } else {
      false
    }
  }

  def removeCardEventsAndEffects(): Unit = {
    cardGroupMap.values.foreach(cardStack => {
      cardStack.resetCards()
    })
  }

  def hidePlayerImage(): Unit = playerImage.visible = false
}

object PlayerArea {
  val alignTopImage: Int = -1
  val alignRightSide: Int = -90
  val alignLeftSide: Int = 90

  val smallSpacing: Int = 5
  val bigSpacing: Int = -25

  val labelWidth: Int = 150
  val labelHeight: Int = 80

  val activeEffectSpread: Double = 0.2
  val activeEffectRadius: Int = 20
  val cardGlowEffectSpread: Double = 0.2
  val cardGlowEffectRadius: Int = 15

  val stylePlayerArea = "-fx-padding: 5px"
  val stylePlayerLabel = "-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 2px"
  val styleTooltip = "-fx-font-size: 16px"
  val stylePassButton = "-fx-font-size: 20px"
  val stylePopup = "-fx-background-color: rgba(0, 0, 0, 0.8); -fx-border-color: white; -fx-border-width: 1; -fx-border-radius: 6; -fx-padding: 8px"
  val stylePopupHint = "-fx-text-fill: white"
}