package de.htwg.se.awol

import com.google.inject.{Guice, Injector}
import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.controller.gameController.handler._TGameHandler
import de.htwg.se.awol.view.{Gui, Tui}

import scala.io.StdIn.readLine
import scalafx.application.Platform

object Arschloch {
  Settings.isGermanActive.set(true)

  val injector: Injector = Guice.createInjector(new ArschlochModule())
  val controller: _TGameHandler = injector.getInstance(classOf[_TGameHandler])
  println(controller.getImplType)

  val tui: Tui = injector.getInstance(classOf[Tui])
  val gui: Gui = injector.getInstance(classOf[Gui])

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
