package de.htwg.se.awol

import com.google.inject.AbstractModule
import de.htwg.se.awol.controller.gameController.handler._TGameHandler
import de.htwg.se.awol.controller.gameController.handler.gameBaseImpl._GameHandler
import de.htwg.se.awol.model.playerComponent.bot.{BotFactory, TBotFactory}

class ArschlochModule extends AbstractModule {
  def configure(): Unit = {
    bind(classOf[TBotFactory]).to(classOf[BotFactory])
    bind(classOf[_TGameHandler]).to(classOf[_GameHandler])
  }
}