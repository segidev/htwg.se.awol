package de.htwg.se.awol.controller.gameController

import de.htwg.se.awol.model.cardComponents.Card

import scala.collection.mutable.ListBuffer
import scala.swing.event.Event

class PlayersChanged extends Event
class ActivePlayerChanged extends Event
class CardsChanged extends Event
class CardsOnTableChanged extends Event
case class HumanPlayerPlaying(suitableCards: Map[Int, ListBuffer[Card]]) extends Event
//case class GridSizeChanged(newSize: Int) extends Event
//class CandidatesChanged extends Event