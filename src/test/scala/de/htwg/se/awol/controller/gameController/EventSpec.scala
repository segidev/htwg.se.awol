package de.htwg.se.awol.controller.gameController

import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.playerComponent.bot.baseImpl.BotPlayer
import de.htwg.se.awol.model.playerComponent.human.HumanPlayer
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

import scala.collection.mutable.ListBuffer

@RunWith(classOf[JUnitRunner])
class EventSpec extends WordSpec with Matchers {
  "The pass counter" should {
    "be able to be set and added to" in {
      val newMap: Map[Int, ListBuffer[Card]] = Map(1 -> ListBuffer())
      HumanPlayerPlaying(newMap, 2)
    }
    "be able to be set and added 123to" in {
      val newMap: Map[Int, ListBuffer[Card]] = Map(1 -> ListBuffer())
      ShowEndOfGame(new HumanPlayer(0), new BotPlayer(1))
    }

  }
}
