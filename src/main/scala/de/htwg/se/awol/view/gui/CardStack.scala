package de.htwg.se.awol.view.gui

import de.htwg.se.awol.model.cardComponents.Card

import scala.collection.mutable.ListBuffer
import scalafx.Includes.handle
import scalafx.scene.image.ImageView
import scalafx.scene.layout.StackPane
import scalafx.Includes._

class CardStack extends StackPane {
  private val transparency: Double = 0.2
  private val cards: ListBuffer[Card] = ListBuffer()
  private val cardImageViews: ListBuffer[ImageView] = ListBuffer()

  def addCard(card: Card, cardImageView: ImageView): Unit = {
    children.append(cardImageView)
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