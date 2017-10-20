case class Card(value:Int, color:Int) {
  def colorName = if (color == 1) "Karo" else if (color == 2) "Herz" else if (color == 3) "Kreuz"
  else if (color == 4) "Pik" else null
}

val card1 = Card(4,1)
card1.value
card1.color
card1.colorName
val card2 = Card(7,5)
card2.colorName

case class Deck(cards:Array[Card])

val deck1 = Deck(Array(Card(1,1), Card(1,2)))
deck1.cards(0)
