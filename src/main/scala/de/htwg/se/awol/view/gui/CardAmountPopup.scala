package de.htwg.se.awol.view.gui

import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.environmentComponents.MessageEnv

import scala.collection.mutable.ListBuffer
import scalafx.Includes.handle
import scalafx.geometry.Pos
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.stage.Popup

class CardAmountPopup(playerArea: PlayerArea) extends Popup {
  onAutoHide = handle(resetPopup())
  autoHide_=(true)

  private val pickHint: Label = new Label() {
    text <== LanguageTranslator.bindTranslation(MessageEnv.PhrasesHuman.HowManyCardsToPlay).get
    style = PlayerArea.stylePopupHint
  }
  private val pickOneCardButton: Button = new Button("1")
  private val pickTwoCardButton: Button = new Button("2")
  private val pickThreeCardButton: Button = new Button("3")
  private val pickFourCardButton: Button = new Button("4")

  private val buttonBox = new HBox {
    spacing = PlayerArea.smallSpacing
    alignment = Pos.Center
    children = List(pickOneCardButton, pickTwoCardButton, pickThreeCardButton, pickFourCardButton)
  }

  private val popupMain = new VBox {
    spacing = PlayerArea.smallSpacing
    alignment = Pos.Center
    style = PlayerArea.stylePopup
    children = List(pickHint, buttonBox)
  }

  this.getContent.add(popupMain)

  // Methods
  def init(cardStack: CardStack, suitableCards: Map[Int, ListBuffer[Card]], cardValue: Int): Unit = {
    pickOneCardButton.disable = cardStack.getCardImageViews.lengthCompare(1) < 0
    pickTwoCardButton.disable = cardStack.getCardImageViews.lengthCompare(2) < 0
    pickThreeCardButton.disable = cardStack.getCardImageViews.lengthCompare(3) < 0
    pickFourCardButton.disable =  cardStack.getCardImageViews.lengthCompare(4) < 0

    show(cardStack, cardStack.localToScreen(cardStack.getBoundsInLocal).getMinX, cardStack.localToScreen(cardStack.getBoundsInLocal).getMinY - 50)

    pickOneCardButton.onAction = handle {
      playerArea.putCards(suitableCards, cardStack, 1, cardValue)
    }
    pickTwoCardButton.onAction = handle {
      playerArea.putCards(suitableCards, cardStack, 2, cardValue)
    }
    pickThreeCardButton.onAction = handle {
      playerArea.putCards(suitableCards, cardStack, 3, cardValue)
    }
    pickFourCardButton.onAction = handle {
      playerArea.putCards(suitableCards, cardStack, 4, cardValue)
    }
  }

  def resetPopup(): Unit = {
    hide()

    pickOneCardButton.onAction = null
    pickTwoCardButton.onAction = null
    pickThreeCardButton.onAction = null
    pickFourCardButton.onAction = null
  }
}
