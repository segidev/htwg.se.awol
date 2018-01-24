package de.htwg.se.awol.controller.environmentController.settings.xml

import de.htwg.se.awol.controller.environmentController.settings.{SettingsOutput, TSettingsHandlers}

import scala.reflect.io.File
import scala.xml.{Elem, XML}

case class SettingsHandler() extends TSettingsHandlers {
  override def getSettingsFileName: File = File("settings.xml")

  override def save(speed: Int, language: String, deckSize: Int, playerCount: Int): Boolean = {
    val xmlOutput: Elem = <settings>
        <speed>{speed}</speed>
        <language>{language}</language>
        <deckSize>{deckSize}</deckSize>
        <playerCount>{playerCount}</playerCount>
      </settings>

    createSettingsFile(settingsDirectory) match {
      case Some(file) =>
        XML.save(file.path, xmlOutput)
        true
      case _ => false
    }
  }

  override def load(): Option[SettingsOutput] = {
    createSettingsFile(settingsDirectory) match {
      case Some(file) =>
        try {
          val xmlData = XML.load(file.path)
          Some(SettingsOutput((xmlData \ "speed").text.toInt, (xmlData \ "language").text,
            (xmlData \ "deckSize").text.toInt, (xmlData \ "playerCount").text.toInt))
        } catch {
          case _: Exception => None
        }
      case _ => None
    }
  }

}