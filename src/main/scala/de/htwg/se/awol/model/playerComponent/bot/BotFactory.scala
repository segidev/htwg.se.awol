package de.htwg.se.awol.model.playerComponent.bot

import de.htwg.se.awol.model.playerComponent.Player

trait TBotFactory {
  def create(playerNumber: Int): Player
}

class BotFactory extends TBotFactory {
  override def create(playerNumber: Int): Player = new de.htwg.se.awol.model.playerComponent.bot.baseImpl.BotPlayer(playerNumber)
}