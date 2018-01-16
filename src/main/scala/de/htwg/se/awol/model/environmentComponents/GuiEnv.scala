package de.htwg.se.awol.model.environmentComponents

import de.htwg.se.awol.model.cardComponents.Card
import de.htwg.se.awol.model.environmentComponents.GuiEnv.Images

import scalafx.Includes.handle
import scalafx.scene.image.{Image, ImageView}

object GuiEnv {
  // Definitions
  private val dimPlayerWidth = 80
  private val dimCardWidth = 140
  private val dimLeadingWidth = 32

  private val imagePlayer: Image = new Image("file:assets/player/player_alt.png", dimPlayerWidth, dimPlayerWidth, true, true)

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

  object Images extends Enumeration {
    val Image_Player = Value
  }

  private val images: Map[GuiEnv.Images.Value, Image] = Map (
    Images.Image_Player -> imagePlayer
  )

  def getCardSize: (Double, Double) = {
    (imageCardMap.head._2.getImage.getWidth, imageCardMap.head._2.getImage.getHeight)
  }

  def getCardImage(card: Card): ImageView = {
    imageCardMap.get(card.cardFilename) match {
      case Some(img) => img
      case _ => throw new MatchError("Card image " + card.cardFilename + " not found")
    }
  }

  def clearAllCardsFromEvents(): Unit = {
    imageCardMap.values.foreach(_.onMouseReleased = handle())
  }

  def getImage(img: Images.Value): Image = images.get(img) match {
    case Some(i) => i
    case _ => throw new MatchError("Image enumeration not found in imageCards!")
  }

  def getLeadingImageView(): ImageView = imageViewLeading

  object Layout extends Enumeration {
    val TOP, RIGHT, BOTTOM, LEFT = Value
  }
}
