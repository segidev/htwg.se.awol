package de.htwg.se.awol.view.gui

import de.htwg.se.awol.controller.environmentController.Settings
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.environmentComponents.{MessageEnv, SettingEnv}

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control._
import scalafx.scene.input.KeyCombination

class MainMenuBar(table: Table) extends MenuBar {
  val languageOptionsGroup = new ToggleGroup()
  val speedOptionsGroup = new ToggleGroup()

  menus = List(
    // File Menu
    new Menu() {
      text <== LanguageTranslator.bindTranslation(MessageEnv.Menues.File).get
      items = List(
        new MenuItem(LanguageTranslator.translate(MessageEnv.Menues.NewGame)) {
          text <== LanguageTranslator.bindTranslation(MessageEnv.Menues.NewGame).get
          accelerator = KeyCombination.keyCombination("Ctrl + N")
          onAction = {
            _: ActionEvent => table.showNewGameDialog()
          }
        },

        new SeparatorMenuItem(),

        new MenuItem(LanguageTranslator.translate(MessageEnv.Menues.Quit)) {
          text <== LanguageTranslator.bindTranslation(MessageEnv.Menues.Quit).get
          accelerator = KeyCombination.keyCombination("Ctrl + Q")
          onAction = {
            _: ActionEvent => table.exitApplication()
          }
        }
      )
    },

    // Options Menu
    new Menu() {
      text <== LanguageTranslator.bindTranslation(MessageEnv.Menues.Options).get
      items = List(
        // Language Menu
        new Menu() {
          text <== LanguageTranslator.bindTranslation(MessageEnv.Menues.GameLanguage).get
          items = List(
            new RadioMenuItem(LanguageTranslator.translate(SettingEnv.Language.German)) {
              text <== LanguageTranslator.bindTranslation(SettingEnv.Language.German).get
              toggleGroup = languageOptionsGroup
              selected <==> Settings.isGermanActive
              onAction = handle(table.saveSettings())
            },
            new RadioMenuItem(LanguageTranslator.translate(SettingEnv.Language.English)) {
              text <== LanguageTranslator.bindTranslation(SettingEnv.Language.English).get
              toggleGroup = languageOptionsGroup
              selected <==> Settings.isEnglishActive
              onAction = handle(table.saveSettings())
            }
          )
        },

        new SeparatorMenuItem(),

        // Speed Menu
        new Menu() {
          text <== LanguageTranslator.bindTranslation(MessageEnv.Menues.GameSpeed).get
          items = List(
            new RadioMenuItem(LanguageTranslator.translate(MessageEnv.Menues.Normal)) {
              text <== LanguageTranslator.bindTranslation(MessageEnv.Menues.Normal).get
              toggleGroup = speedOptionsGroup
              selected <==> Settings.isNormalSpeedActive
              onAction = handle(table.saveSettings())
            },
            new RadioMenuItem(LanguageTranslator.translate(MessageEnv.Menues.Fast)) {
              text <== LanguageTranslator.bindTranslation(MessageEnv.Menues.Fast).get
              toggleGroup = speedOptionsGroup
              selected <==> Settings.isFastSpeedActive
              onAction = handle(table.saveSettings())
            },
            new RadioMenuItem(LanguageTranslator.translate(MessageEnv.Menues.Slow)) {
              text <== LanguageTranslator.bindTranslation(MessageEnv.Menues.Slow).get
              toggleGroup = speedOptionsGroup
              selected <==> Settings.isSlowSpeedActive
              onAction = handle(table.saveSettings())
            }
          )
        }
      )
    }
  )
}
