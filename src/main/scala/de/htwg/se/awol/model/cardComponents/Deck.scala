package de.htwg.se.awol.model.cardComponents

import de.htwg.se.awol.model.language.LanguageManager
import de.htwg.se.awol.model.language.handler.StakeAndPepper

case object Deck {
  val smallCardStackSize: Int = 32
  val bigCardStackSize: Int = 52
  val maxPlayerCardAmount: Int = 15
  val amountOfColoredEquals: Int = 4
}

case class Deck(amount: Int = Deck.smallCardStackSize) {
  private var cards: Array[Card] = Array[Card]()
  createCards(amount)

  private def createCards(amount: Int): Unit = {
    if (amount % Deck.amountOfColoredEquals != 0) {
      //assert(!(amount % 4 != 0), LanguageManager.getMessage("dividableByFour"))
      println(LanguageManager.getTranslation(StakeAndPepper.M_DividableByFour))
      return
    } else if (amount < Deck.smallCardStackSize || amount > Deck.bigCardStackSize) {
      //assert(amount >= Deck.defaultAmount && amount <= Deck.twoSetsAmount, LanguageManager.getMessage("maxAmountOfCards"))
      println(LanguageManager.getTranslation(StakeAndPepper.M_MaxAmountOfCards))
      return
    }

    cards = new Array[Card](amount)

    val cardStackSize: Int = amount / Deck.amountOfColoredEquals
    var startCard: Int = Deck.maxPlayerCardAmount - cardStackSize

    for (i <- 0 until amount) {
      val cardNum = i % cardStackSize + startCard
      val cardColor = i / cardStackSize

      cards(i) = Card(cardNum, cardColor)
    }
  }

  override def toString: String = {
    var sb: StringBuilder = new StringBuilder

    for(c <- cards) {
      sb.append(c).append("\n")
    }

    sb.toString()
  }

  def size: Int = cards.length
}