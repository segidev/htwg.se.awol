package de.htwg.se.awol

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.controller.gameController.gameBaseImpl._GameHandler
import de.htwg.se.awol.model.languageComponents.LanguageGerman
import de.htwg.se.awol.view.{Gui, Tui}

import scala.io.StdIn.readLine
import scalafx.application.Platform

object Arschloch {
  Settings.isGermanActive.set(true)

  val controller: _GameHandler = new _GameHandler()
  val tui: Tui = new Tui(controller)
  val gui: Gui = new Gui(controller)
  Platform.runLater(controller.loadSettings())

  def main(args: Array[String]): Unit = {
    var input: String = ""
    do {
      input = readLine().trim
      Platform.runLater(tui.processInputLine(input))
    } while(input != "q")
    gui.close()
  }
}
