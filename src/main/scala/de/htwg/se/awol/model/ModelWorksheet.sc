private def clamp(i: Int, min: Int, max: Int): Int = if(i < min) min else if (i > max) max else i

case object Card {
  val colorNames = Array[String]("Kreuz", "Pik", "Herz", "Karo")
  val cardNames = Array[String]("Bube", "Dame", "König", "Ass")
}

case class Card(value: Int, color: Int){
  private def colorName: String = Card.colorNames(clamp(color, 0, 3))
  private def cardName: String = if (value > 10) Card.cardNames(value - 11) else value.toString

  override def toString: String = cardName + " [" + colorName + "]"
}

case object Deck {
  val defaultAmount: Int = 32;
  val defaulCardAmount: Int = 15
}

case class Deck(amount: Int = Deck.defaultAmount) {
  val cards: Array[Card] = new Array[Card](amount)
  createCards(amount)

  def createCards(amount: Int): Unit = {
    var i = 0
    val cardStackSize: Int = amount / 4
    var startCard: Int = Deck.defaulCardAmount - cardStackSize
    var iterator = 0 to amount - 1

    for (i <- iterator) {
      val cardColor = i / cardStackSize
      val cardNum = i % cardStackSize + startCard
      cards(i) = Card(cardNum, cardColor)
    }

    for(c <- cards) println(c)
  }
}

val deck1 = new Deck(52)