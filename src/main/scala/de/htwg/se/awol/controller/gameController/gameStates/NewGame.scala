package de.htwg.se.awol.controller.gameController.gameStates
import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.controller.gameController.gameBaseImpl._GameHandler
import de.htwg.se.awol.model.playerComponent.HumanPlayer
import de.htwg.se.awol.model.playerComponent.playerBaseImpl.BotPlayer

class NewGame extends _GameHandler with GameState {
  override def executeState(controller: _GameHandler): Unit = {
    assert(Game.getGameState == Game.States.NewGame, "Creating players in wrong game state!")

    Game.setHumanPlayer(HumanPlayer(0))
    controller.playerList.append(Game.getHumanPlayer)

    (1 until controller.totalPlayerCount).foreach(playerNumber => {
      controller.playerList.append(new BotPlayer(playerNumber))
    })

    Game.setGameState(Game.States.HandOut)
  }
}
