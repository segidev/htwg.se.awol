package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.environmentComponents.PlayerEnvironment

class Player(var rank: PlayerEnvironment.Rank.Value = PlayerEnvironment.Rank.Mob) {
  def rankName: String = LanguageTranslator.translate(rank)

  def setRank(newRank: PlayerEnvironment.Rank.Value): Unit = { rank = newRank }

  override def toString: String = rankName
}