package de.htwg.se.awol.model.environmentComponents

import de.htwg.se.awol.model.cardComponents.Card

import scalafx.scene.image.{Image, ImageView}

object GuiEnv {
  // Definitions
  private val dimPlayerWidth = 80
  private val dimCardWidth = 140
  private val dimLeadingWidth = 32

  private val imagePlayer: Image = new Image("file:assets/player/player_alt.png", dimPlayerWidth, dimPlayerWidth, true, true)

  private val imageTable: Image = new Image("file:assets/table/table.jpg")

  private val imageViewLeading: ImageView = new ImageView(new Image("file:assets/player/leading.png", dimLeadingWidth, dimLeadingWidth, true, true))

  private val imageCardMap: Map[String, ImageView] = Map(
    "2_of_clubs" -> new ImageView(new Image("file:assets/cards/2_of_clubs.png", dimCardWidth, dimCardWidth, true, true)),
    "2_of_diamonds" -> new ImageView(new Image("file:assets/cards/2_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)),
    "2_of_hearts" -> new ImageView(new Image("file:assets/cards/2_of_hearts.png", dimCardWidth, dimCardWidth, true, true)),
    "2_of_spades" -> new ImageView(new Image("file:assets/cards/2_of_spades.png", dimCardWidth, dimCardWidth, true, true)),
    "3_of_clubs" -> new ImageView(new Image("file:assets/cards/3_of_clubs.png", dimCardWidth, dimCardWidth, true, true)),
    "3_of_diamonds" -> new ImageView(new Image("file:assets/cards/3_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)),
    "3_of_hearts" -> new ImageView(new Image("file:assets/cards/3_of_hearts.png", dimCardWidth, dimCardWidth, true, true)),
    "3_of_spades" -> new ImageView(new Image("file:assets/cards/3_of_spades.png", dimCardWidth, dimCardWidth, true, true)),
    "4_of_clubs" -> new ImageView(new Image("file:assets/cards/4_of_clubs.png", dimCardWidth, dimCardWidth, true, true)),
    "4_of_diamonds" -> new ImageView(new Image("file:assets/cards/4_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)),
    "4_of_hearts" -> new ImageView(new Image("file:assets/cards/4_of_hearts.png", dimCardWidth, dimCardWidth, true, true)),
    "4_of_spades" -> new ImageView(new Image("file:assets/cards/4_of_spades.png", dimCardWidth, dimCardWidth, true, true)),
    "5_of_clubs" -> new ImageView(new Image("file:assets/cards/5_of_clubs.png", dimCardWidth, dimCardWidth, true, true)),
    "5_of_diamonds" -> new ImageView(new Image("file:assets/cards/5_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)),
    "5_of_hearts" -> new ImageView(new Image("file:assets/cards/5_of_hearts.png", dimCardWidth, dimCardWidth, true, true)),
    "5_of_spades" -> new ImageView(new Image("file:assets/cards/5_of_spades.png", dimCardWidth, dimCardWidth, true, true)),
    "6_of_clubs" -> new ImageView(new Image("file:assets/cards/6_of_clubs.png", dimCardWidth, dimCardWidth, true, true)),
    "6_of_diamonds" -> new ImageView(new Image("file:assets/cards/6_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)),
    "6_of_hearts" -> new ImageView(new Image("file:assets/cards/6_of_hearts.png", dimCardWidth, dimCardWidth, true, true)),
    "6_of_spades" -> new ImageView(new Image("file:assets/cards/6_of_spades.png", dimCardWidth, dimCardWidth, true, true)),
    "7_of_clubs" -> new ImageView(new Image("file:assets/cards/7_of_clubs.png", dimCardWidth, dimCardWidth, true, true)),
    "7_of_diamonds" -> new ImageView(new Image("file:assets/cards/7_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)),
    "7_of_hearts" -> new ImageView(new Image("file:assets/cards/7_of_hearts.png", dimCardWidth, dimCardWidth, true, true)),
    "7_of_spades" -> new ImageView(new Image("file:assets/cards/7_of_spades.png", dimCardWidth, dimCardWidth, true, true)),
    "8_of_clubs" -> new ImageView(new Image("file:assets/cards/8_of_clubs.png", dimCardWidth, dimCardWidth, true, true)),
    "8_of_diamonds" -> new ImageView(new Image("file:assets/cards/8_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)),
    "8_of_hearts" -> new ImageView(new Image("file:assets/cards/8_of_hearts.png", dimCardWidth, dimCardWidth, true, true)),
    "8_of_spades" -> new ImageView(new Image("file:assets/cards/8_of_spades.png", dimCardWidth, dimCardWidth, true, true)),
    "9_of_clubs" -> new ImageView(new Image("file:assets/cards/9_of_clubs.png", dimCardWidth, dimCardWidth, true, true)),
    "9_of_diamonds" -> new ImageView(new Image("file:assets/cards/9_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)),
    "9_of_hearts" -> new ImageView(new Image("file:assets/cards/9_of_hearts.png", dimCardWidth, dimCardWidth, true, true)),
    "9_of_spades" -> new ImageView(new Image("file:assets/cards/9_of_spades.png", dimCardWidth, dimCardWidth, true, true)),
    "10_of_clubs" -> new ImageView(new Image("file:assets/cards/10_of_clubs.png", dimCardWidth, dimCardWidth, true, true)),
    "10_of_diamonds" -> new ImageView(new Image("file:assets/cards/10_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)),
    "10_of_hearts" -> new ImageView(new Image("file:assets/cards/10_of_hearts.png", dimCardWidth, dimCardWidth, true, true)),
    "10_of_spades" -> new ImageView(new Image("file:assets/cards/10_of_spades.png", dimCardWidth, dimCardWidth, true, true)),
    "11_of_clubs" -> new ImageView(new Image("file:assets/cards/jack_of_clubs.png", dimCardWidth, dimCardWidth, true, true)),
    "11_of_diamonds" -> new ImageView(new Image("file:assets/cards/jack_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)),
    "11_of_hearts" -> new ImageView(new Image("file:assets/cards/jack_of_hearts.png", dimCardWidth, dimCardWidth, true, true)),
    "11_of_spades" -> new ImageView(new Image("file:assets/cards/jack_of_spades.png", dimCardWidth, dimCardWidth, true, true)),
    "12_of_clubs" -> new ImageView(new Image("file:assets/cards/queen_of_clubs.png", dimCardWidth, dimCardWidth, true, true)),
    "12_of_diamonds" -> new ImageView(new Image("file:assets/cards/queen_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)),
    "12_of_hearts" -> new ImageView(new Image("file:assets/cards/queen_of_hearts.png", dimCardWidth, dimCardWidth, true, true)),
    "12_of_spades" -> new ImageView(new Image("file:assets/cards/queen_of_spades.png", dimCardWidth, dimCardWidth, true, true)),
    "13_of_clubs" -> new ImageView(new Image("file:assets/cards/king_of_clubs.png", dimCardWidth, dimCardWidth, true, true)),
    "13_of_diamonds" -> new ImageView(new Image("file:assets/cards/king_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)),
    "13_of_hearts" -> new ImageView(new Image("file:assets/cards/king_of_hearts.png", dimCardWidth, dimCardWidth, true, true)),
    "13_of_spades" -> new ImageView(new Image("file:assets/cards/king_of_spades.png", dimCardWidth, dimCardWidth, true, true)),
    "14_of_clubs" -> new ImageView(new Image("file:assets/cards/ace_of_clubs.png", dimCardWidth, dimCardWidth, true, true)),
    "14_of_diamonds" -> new ImageView(new Image("file:assets/cards/ace_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)),
    "14_of_hearts" -> new ImageView(new Image("file:assets/cards/ace_of_hearts.png", dimCardWidth, dimCardWidth, true, true)),
    "14_of_spades" -> new ImageView(new Image("file:assets/cards/ace_of_spades.png", dimCardWidth, dimCardWidth, true, true))
  )

  def getCardSize: (Double, Double) = {
    (imageCardMap.head._2.getImage.getWidth, imageCardMap.head._2.getImage.getHeight)
  }

  def getCardImage(card: Card): ImageView = imageCardMap.apply(card.cardFilename)

  def getPlayerImage(): Image = imagePlayer

  def getLeadingImageView(): ImageView = imageViewLeading

  def getTableImage(): Image = imageTable

  object Layout extends Enumeration {
    val TOP, RIGHT, BOTTOM, LEFT = Value
  }
}
