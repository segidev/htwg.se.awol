package de.htwg.se.awol.view

import java.awt.Dimension
import javax.swing.JFrame

import de.htwg.se.awol.controller.gameController._GameHandler
import de.htwg.se.awol.view.gui.Table

class Gui(controller: _GameHandler) extends JFrame {
  setMinimumSize(new Dimension(1024, 768))
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

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
}