package de.htwg.se.awol.controller.gameController.handler

import de.htwg.se.awol.controller.gameController.handler.gameMockImpl._GameHandler

trait _TGameHandlerFactory {
  def create(): _TGameHandler
}

class _GameHandlerFactory extends _TGameHandlerFactory {
  override def create(): _TGameHandler = new _GameHandler()
}