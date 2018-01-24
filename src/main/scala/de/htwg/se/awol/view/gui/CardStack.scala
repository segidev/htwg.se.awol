package de.htwg.se.awol.view.gui

import de.htwg.se.awol.model.cardComponents.Card

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scalafx.Includes._
import scalafx.scene.image.ImageView
import scalafx.scene.layout.StackPane

class CardStack extends StackPane {
  private var translateCards = 0
  private val transparency: Double = 0.2
  private val cardImageMap: mutable.Map[Card, ImageView] = mutable.Map()
  style = CardStack.styleCardStack

  def addCard(card: Card, cardImageView: ImageView): Unit = {
    children.append(cardImageView)
    cardImageView.setTranslateX(translateCards)
    translateCards += 6

    cardImageMap.put(card, cardImageView)
  }

  def removeCards(usedCards: ListBuffer[Card]): Boolean = {
    cardImageMap.toSeq.sortBy(_._1.cardColorName).reverse.slice(0, usedCards.length).foreach(c => {
      val removeCard: Card = c._1
      cardImageMap.remove(removeCard)
    })

    cardImageMap.isEmpty
  }

  def setDisabled(): Unit = {
    this.onMouseReleased = null
    cardImageMap.foreach(_._2.opacity = transparency)
  }

  def resetCards(): Unit = {
    this.onMouseReleased = null
    cardImageMap.foreach(_._2.opacity = 1)
  }

  def getCardImageViews: ListBuffer[ImageView] = {
    val cardImageViews: ListBuffer[ImageView] = ListBuffer()

    cardImageMap.toSeq.sortBy(_._1.cardColorName).foreach(c => {
      cardImageViews.append(c._2)
    })
    cardImageViews
  }

  override def toString(): String = "Stack with %d cards".format(cardImageMap.size)
}

object CardStack {
  val styleCardStack: String = "-fx-padding: 5px"
}