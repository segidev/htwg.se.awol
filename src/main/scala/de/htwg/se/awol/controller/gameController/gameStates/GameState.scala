package de.htwg.se.awol.controller.gameController.gameStates

import de.htwg.se.awol.controller.gameController.gameBaseImpl._GameHandler

trait GameState {
  def executeState(controller: _GameHandler): Unit
}
