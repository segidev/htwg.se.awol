package de.htwg.se.awol.model.playerComponent

import de.htwg.se.awol.model.environmentComponents.PlayerEnvironment
import de.htwg.se.awol.model.language.LanguageManager

/*object PlayerRank extends Enumeration {
  val DEFAULT = StakeAndPepper.P_Mob
  val KING = StakeAndPepper.P_King
  val VICEROY = StakeAndPepper.P_Viceroy
  val VICEASSHOLE = StakeAndPepper.P_Viceasshole
  val ASSHOLE = StakeAndPepper.P_Asshole
}*/

// TODO PlayerRank.Value doesn't work here. It would be nice tough. But how to handle translation then... :/
class Player(var rank: PlayerEnvironment.Value = PlayerEnvironment.P_Mob) {
  def rankName: String = LanguageManager.getTranslation(rank)

  def setRank(newRank: PlayerEnvironment.Value): Unit = { rank = newRank }

  override def toString: String = rankName
}