package de.htwg.se.awol.view

import java.awt.event.{WindowAdapter, WindowEvent}
import javax.swing.{JFrame, WindowConstants}

import com.google.inject.Inject
import de.htwg.se.awol.controller.gameController.handler._TGameHandler
import de.htwg.se.awol.controller.gameController.handler.gameBaseImpl._GameHandler
import de.htwg.se.awol.view.gui.Table

import scala.swing.Dimension
import scalafx.application.Platform

class Gui @Inject()(controller: _TGameHandler) extends JFrame {
  val defaultSize: (Int, Int) = (1024, 768)

  setMinimumSize(new Dimension(defaultSize._1, defaultSize._2))
  setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE)

  // Layout
  val mainPanel: Table = new Table(controller)
  add(mainPanel)

  setLocationRelativeTo(null)
  setVisible(true)

  // Methods
  def close(): Unit = {
    setVisible(false)
    dispose()
  }

  addWindowListener(new WindowAdapter {
    override def windowClosing(e: WindowEvent): Unit = {
      Platform.runLater(mainPanel.exitApplication())
    }
  })
}