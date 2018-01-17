package de.htwg.se.awol

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.controller.gameController._GameHandler
import de.htwg.se.awol.model.languageComponents.{LanguageEnglish, LanguageGerman, LanguageYouth}
import de.htwg.se.awol.view.{Gui, Tui}

import scala.io.StdIn.readLine
import scalafx.application.Platform

object Arschloch {
  Settings.setLanguage(LanguageGerman)

  val defaultPlayerCount: Int = 4
  val controller: _GameHandler = new _GameHandler()
  val tui: Tui = new Tui(controller)
  val gui: Gui = new Gui(controller)

  def main(args: Array[String]): Unit = {
    var input: String = ""
    do {
      input = readLine()
      Platform.runLater(tui.processInputLine(input))
    } while(input != "q")
    gui.close()
  }
}
