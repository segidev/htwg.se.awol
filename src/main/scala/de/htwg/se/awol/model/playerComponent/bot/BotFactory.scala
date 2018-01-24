package de.htwg.se.awol.model.playerComponent.bot

import de.htwg.se.awol.model.playerComponent.Player
import de.htwg.se.awol.model.playerComponent.bot.advancedImpl.BotPlayer

trait TBotFactory {
  def create(playerNumber: Int): Player
}

class BotFactory extends TBotFactory {
  override def create(playerNumber: Int): Player = new BotPlayer(playerNumber)
}