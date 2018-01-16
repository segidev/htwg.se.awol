package de.htwg.se.awol.controller.gameController

import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.playerComponent.Player

import scala.collection.mutable.ListBuffer
import scala.swing.event.Event

case class GameContinuedFromPause() extends Event
case class PlayersCreated() extends Event
case class PlayerStatusChanged() extends Event
case class CardsHandedToPlayers() extends Event
case class CardsOnTableChanged() extends Event
case class PronounceWinnerOfRound(player: Player) extends Event
case class ShowEndOfGame(king: Player, asshole: Player) extends Event
case class HumanPlayerPlaying(suitableCards: Map[Int, ListBuffer[Card]]) extends Event
case class BotPlayerPlaying(player: Player, pickedCards: ListBuffer[Card]) extends Event
//case class GridSizeChanged(newSize: Int) extends Event
//class CandidatesChanged extends Event