import scala.collection.mutable.ListBuffer

case class Card(value: Int, color: Int) {
  override def toString: String = s"Card [$value - $color]"
}

trait Player {
  val cards: ListBuffer[Card] = ListBuffer()

  def addCard(card: Card): Unit = cards.append(card)
  def getPossibleCards(): Unit
  def pickCardsToDrop(): Unit
}

class HumanPlayer() extends Player {
  override def getPossibleCards(): Unit = ???

  override def pickCardsToDrop(): Unit = ???
}

class BotPlayer() extends Player {
  override def getPossibleCards(): Unit = ???

  override def pickCardsToDrop(): Unit = ???
}

val player: Player = new HumanPlayer()
player.addCard(Card(2, 1))
player.cards