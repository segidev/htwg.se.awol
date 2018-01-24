package de.htwg.se.awol.view.gui

import de.htwg.se.awol.model.cardComponents.Card

import scala.collection.mutable.ListBuffer
import scalafx.Includes._
import scalafx.scene.image.ImageView
import scalafx.scene.layout.StackPane

class CardStack extends StackPane {
  private var translateCards = 0
  private val transparency: Double = 0.2
  private val cards: ListBuffer[Card] = ListBuffer()
  private val cardImageViews: ListBuffer[ImageView] = ListBuffer()
  style = CardStack.styleCardStack

  def addCard(card: Card, cardImageView: ImageView): Unit = {
    children.append(cardImageView)
    cardImageView.setTranslateX(translateCards)
    translateCards += 6

    cardImageViews.append(cardImageView)

    cards.append(card)
  }

  def removeCards(usedCards: ListBuffer[Card]): Boolean = {
    cardImageViews.remove(0, usedCards.length)
    cards.--=(usedCards)

    cards.isEmpty
  }

  def setDisabled(): Unit = {
    this.onMouseReleased = null
    cardImageViews.foreach(_.opacity = transparency)
  }

  def resetCards(): Unit = {
    this.onMouseReleased = null
    cardImageViews.foreach(_.opacity = 1)
  }

  def getCardImageViews: ListBuffer[ImageView] = {
    cardImageViews
  }

  override def toString(): String = "Stack with %d cards".format(cardImageViews.length)
}

object CardStack {
  val styleCardStack: String = "-fx-padding: 5px"
}