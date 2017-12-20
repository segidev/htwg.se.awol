package de.htwg.se.awol.model.cardComponents

import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.environmentComponents.MessageEnvironment

case object Deck {
  val smallCardStackSize: Int = 32
  val bigCardStackSize: Int = 52
  val maxPlayerCardAmount: Int = 15
  val amountOfColoredEquals: Int = 4
}

case class Deck(amount: Int = Deck.smallCardStackSize) {
  validateDeck()

  private var cards: Array[Card] = createCards(amount)

  private def createCards(amount: Int): Array[Card] = {
    var cards = new Array[Card](amount)

    val cardStackSize: Int = amount / Deck.amountOfColoredEquals
    var startCard: Int = Deck.maxPlayerCardAmount - cardStackSize

    for (i <- 0 until amount) {
      val cardNum = i % cardStackSize + startCard
      val cardColor = i / cardStackSize

      cards(i) = Card(cardNum, cardColor)
    }

    cards
  }

  override def toString: String = {
    var sb: StringBuilder = new StringBuilder

    for(c <- cards) {
      sb.append(c).append("\n")
    }

    sb.toString()
  }

  def size: Int = cards.length

  def validateDeck(): Unit = {
    if (amount % Deck.amountOfColoredEquals != 0) {
      throw new IllegalArgumentException(LanguageTranslator.translate(MessageEnvironment.Warnings.DividableByFour))
    } else if (amount < Deck.smallCardStackSize || amount > Deck.bigCardStackSize) {
      throw new IndexOutOfBoundsException(LanguageTranslator.translate(MessageEnvironment.Warnings.MaxAmountOfCards))
    }
  }
}