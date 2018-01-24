package de.htwg.se.awol.controller.environmentController.settings.json

import de.htwg.se.awol.controller.environmentController.settings.{SettingsOutput, TSettingsHandlers}
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.{read, write}

import scala.reflect.io.File

case class SettingsHandler() extends TSettingsHandlers {
  override def getSettingsFileName: File = File("settings.ini")

  private implicit val formats: DefaultFormats.type = DefaultFormats

  override def save(speed: Int, language: String, deckSize: Int, playerCount: Int): Boolean = {
    val settingsOutput: SettingsOutput = SettingsOutput(speed: Int, language: String, deckSize: Int, playerCount: Int)
    val jsonString = write(settingsOutput)

    createSettingsFile(settingsDirectory) match {
      case Some(file) =>
        file.writeAll(jsonString)
        true
      case _ => false
    }
  }

  override def load(): Option[SettingsOutput] = {
    createSettingsFile(settingsDirectory) match {
      case Some(file) =>
        try {
          Some(read[SettingsOutput](file.bufferedReader()))
        } catch {
          case _: Exception => None
        }
      case _ => None
    }
  }
}