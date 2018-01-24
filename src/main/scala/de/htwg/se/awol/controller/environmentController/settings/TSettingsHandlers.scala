package de.htwg.se.awol.controller.environmentController.settings

import scala.reflect.io.{Directory, File}

trait TSettingsHandlers {
  protected var settingsDirectory: Directory = _

  def createSettingsFile(directoryPath: Directory): Option[File] = {
    val fullFilePath: File = directoryPath.resolve(getSettingsFileName).toFile

    if (directoryPath.createDirectory().exists) {
      try {
        if (fullFilePath.createFile().exists) {
          Option(fullFilePath)
        } else {
          None
        }
      } catch {
        case _: Exception => None
      }
    } else {
      None
    }
  }

  def setSettingsDirectory(directory: Directory): Unit = {settingsDirectory = directory}

  def getSettingsFileName: File
  def save(speed: Int, language: String, deckSize: Int, playerCount: Int): Boolean
  def load(): Option[SettingsOutput]
}
