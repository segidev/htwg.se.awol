package de.htwg.se.awol.environmentController

import de.htwg.se.awol.controller.environmentController.SettingsJSON
import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.model.languageComponents.LanguageGerman
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SettingsSpec extends WordSpec with Matchers {
  val settingsObj: SettingsJSON = new SettingsJSON(2000, "Deutsch", 32, 4)

  "The Settings class" can {
    "check whether a certain language is active" in {
      Settings.setLanguage(LanguageGerman)
      Settings.isLanguageActive(LanguageGerman) should be(true)
    }
  }
  //TODO: Settings Tests
}
