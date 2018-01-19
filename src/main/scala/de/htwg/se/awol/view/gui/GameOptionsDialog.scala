package de.htwg.se.awol.view.gui

import de.htwg.se.awol.controller.gameController.Game
import de.htwg.se.awol.controller.languageController.LanguageTranslator
import de.htwg.se.awol.model.cardComponents.Deck
import de.htwg.se.awol.model.environmentComponents.MessageEnv

import scalafx.Includes._
import scalafx.geometry.Pos
import scalafx.scene.control._
import scalafx.scene.layout.{HBox, VBox}

class GameOptionsDialog extends Dialog {
  title = LanguageTranslator.translate(MessageEnv.Titles.GameOptions)
  private val dialogLayout = new VBox()

  private val playerOptions: HBox = new HBox {
    spacing = GameOptionsDialog.spacing
  }

  private val toggleGrp: ToggleGroup = new ToggleGroup()

  println(Game.getDefaultPlayerCount)

  private val radioButton2Players: RadioButton = new RadioButton(LanguageTranslator.translate(MessageEnv.Menues.Players_2)) {
    toggleGroup = toggleGrp
    userData = "2"
    selected = Game.getDefaultPlayerCount == 2
  }
  private val radioButton4Players: RadioButton = new RadioButton(LanguageTranslator.translate(MessageEnv.Menues.Players_4)) {
    userData = "4"
    toggleGroup = toggleGrp
    selected = Game.getDefaultPlayerCount == 4
  }
  private val radioButton6Players: RadioButton = new RadioButton(LanguageTranslator.translate(MessageEnv.Menues.Players_6)) {
    userData = "6"
    toggleGroup = toggleGrp
    selected = Game.getDefaultPlayerCount == 6
  }
  private val radioButton8Players: RadioButton = new RadioButton(LanguageTranslator.translate(MessageEnv.Menues.Players_8)) {
    userData = "8"
    toggleGroup = toggleGrp
    selected = Game.getDefaultPlayerCount == 8
  }

  playerOptions.children = List(
    radioButton2Players,
    radioButton4Players,
    radioButton6Players,
    radioButton8Players
  )

  // Deck Options
  private val deckOptions: HBox = new HBox {
    spacing = GameOptionsDialog.spacing
    alignment = Pos.CenterLeft
    style = GameOptionsDialog.styleDeckOptions
  }

  private val labelDeckSize: Label = new Label(LanguageTranslator.translate(MessageEnv.Menues.DeckSize))
  private val spinnerDeckSize: Spinner[Int] = new Spinner(4, Deck.bigCardStackSize, Game.getDefaultDeckSize, GameOptionsDialog.cardSizeStep) // TODO: Anstatt der 4 links wieder: Deck.smallCardStackSize, 12 mit Deck.smallCardStackSize

  spinnerDeckSize.value.onChange(checkValidation())

  def checkValidation(): Unit = {
    radioButton6Players.disable = (spinnerDeckSize.getValue % 6) != 0
    radioButton8Players.disable = (spinnerDeckSize.getValue % 8) != 0

    if (radioButton8Players.selected.getValue && radioButton8Players.isDisabled) {
      radioButton6Players.selected.set(true)
    }
    if (radioButton6Players.selected.getValue && radioButton6Players.isDisabled) {
      radioButton4Players.selected.set(true)
    }
  }

  checkValidation()

  deckOptions.children = List(
    labelDeckSize,
    spinnerDeckSize
  )

  dialogLayout.children = List(
    playerOptions,
    deckOptions
  )

  val buttonStart = new ButtonType(LanguageTranslator.translate(MessageEnv.Menues.StartGame))
  val buttonCancel = new ButtonType(LanguageTranslator.translate(MessageEnv.Menues.Cancel))

  private val diagPane = dialogPane()

  diagPane.setContent(dialogLayout)
  diagPane.buttonTypes = Seq (
    buttonStart,
    buttonCancel
  )

  def getDeckSize: Int = spinnerDeckSize.getValue
  def getPlayerCount: Int = toggleGrp.selectedToggle.value.getUserData.toString.toInt
}

object GameOptionsDialog {
  val spacing: Int = 5
  val cardSizeStep: Int = 4

  val styleDeckOptions: String = "-fx-padding: 10 0 0 0"
}
