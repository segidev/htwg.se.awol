package de.htwg.se.awol.model.cardComponents

import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.environmentComponents.{CardEnv, MessageEnv}

import scala.collection.mutable.ListBuffer

case object Deck {
  val smallCardStackSize: Int = 32
  val bigCardStackSize: Int = 52
  val maxPlayerCardAmount: Int = 15
  val amountOfColoredEquals: Int = 4
}

case class Deck(private val amount: Int = Deck.smallCardStackSize) {
  validateDeck()

  private val cardStackSize: Int = amount / Deck.amountOfColoredEquals
  private val cards: Array[Card] = createCards(amount)

  private def createCards(amount: Int): Array[Card] = {
    val cards = new Array[Card](amount)

    val startCard: Int = Deck.maxPlayerCardAmount - cardStackSize

    (0 until amount).foreach(i => {
      val cardNum = CardEnv.Values.apply(i % cardStackSize + startCard)
      val cardColor = CardEnv.Colors.apply(i / cardStackSize)

      cards(i) = new Card(cardNum, cardColor)
    })

    cards
  }

  def getCards: ListBuffer[Card] = cards.to[ListBuffer]

  def getDeckSize: Int = cards.length

  def validateDeck(): Unit = {
    if (amount < 4 || amount > Deck.bigCardStackSize) { // TODO: Anstatt der 4 links wieder: Deck.smallCardStackSize
      throw new IndexOutOfBoundsException(LanguageTranslator.translate(MessageEnv.Warnings.MaxAmountOfCards))
    } else if (amount % Deck.amountOfColoredEquals != 0) {
      throw new IllegalArgumentException(LanguageTranslator.translate(MessageEnv.Warnings.DividableByFour))
    }
  }

  override def toString: String = {
    val sb: StringBuilder = new StringBuilder

    var maxLength = 0
    cards.foreach(card => {
      if (card.toString.length > maxLength) {
        maxLength = card.toString.length
      }
    })

    sb.append(getDeckSize + " cards\n")
    (0 until cardStackSize).foreach(i => {
      sb.append("|   ")
      sb.append(cards(i)).append(String.format("%" + (maxLength - cards(i).toString.length + 5) + "s", ""))
        .append(cards(i + cardStackSize)).append(String.format("%" + (maxLength - cards(i + cardStackSize).toString.length + 5) + "s", ""))
        .append(cards(i + cardStackSize * 2)).append(String.format("%" + (maxLength - cards(i + cardStackSize * 2).toString.length + 5) + "s", ""))
        .append(cards(i + cardStackSize * 3))
        .append("\n")
    })

    sb.toString()
  }
}