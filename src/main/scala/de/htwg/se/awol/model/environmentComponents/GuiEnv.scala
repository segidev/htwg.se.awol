package de.htwg.se.awol.model.environmentComponents

import scalafx.scene.image.Image

object GuiEnv {
  // Definitions
  private val dimPlayerWidth = 120
  private val dimCardWidth = 100

  private val imagePlayer: Image = new Image("file:assets/player/player_alt.png", dimPlayerWidth, dimPlayerWidth, true, true)

  private lazy val imageClub_2 = new Image("file:assets/cards/2_of_clubs.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageDiamond_2 = new Image("file:assets/cards/2_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageHeart_2 = new Image("file:assets/cards/2_of_hearts.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageSpade_2 = new Image("file:assets/cards/2_of_spades.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageClub_3 = new Image("file:assets/cards/3_of_clubs.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageDiamond_3 = new Image("file:assets/cards/3_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageHeart_3 = new Image("file:assets/cards/3_of_hearts.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageSpade_3 = new Image("file:assets/cards/3_of_spades.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageClub_4 = new Image("file:assets/cards/4_of_clubs.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageDiamond_4 = new Image("file:assets/cards/4_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageHeart_4 = new Image("file:assets/cards/4_of_hearts.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageSpade_4 = new Image("file:assets/cards/4_of_spades.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageClub_5 = new Image("file:assets/cards/5_of_clubs.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageDiamond_5 = new Image("file:assets/cards/5_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageHeart_5 = new Image("file:assets/cards/5_of_hearts.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageSpade_5 = new Image("file:assets/cards/5_of_spades.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageClub_6 = new Image("file:assets/cards/6_of_clubs.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageDiamond_6 = new Image("file:assets/cards/6_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageHeart_6 = new Image("file:assets/cards/6_of_hearts.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageSpade_6 = new Image("file:assets/cards/6_of_spades.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageClub_7 = new Image("file:assets/cards/7_of_clubs.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageDiamond_7 = new Image("file:assets/cards/7_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageHeart_7 = new Image("file:assets/cards/7_of_hearts.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageSpade_7 = new Image("file:assets/cards/7_of_spades.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageClub_8 = new Image("file:assets/cards/8_of_clubs.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageDiamond_8 = new Image("file:assets/cards/8_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageHeart_8 = new Image("file:assets/cards/8_of_hearts.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageSpade_8 = new Image("file:assets/cards/8_of_spades.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageClub_9 = new Image("file:assets/cards/9_of_clubs.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageDiamond_9 = new Image("file:assets/cards/9_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageHeart_9 = new Image("file:assets/cards/9_of_hearts.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageSpade_9 = new Image("file:assets/cards/9_of_spades.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageClub_10 = new Image("file:assets/cards/10_of_clubs.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageDiamond_10 = new Image("file:assets/cards/10_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageHeart_10 = new Image("file:assets/cards/10_of_hearts.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageSpade_10 = new Image("file:assets/cards/10_of_spades.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageClub_jack = new Image("file:assets/cards/jack_of_clubs.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageDiamond_jack = new Image("file:assets/cards/jack_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageHeart_jack = new Image("file:assets/cards/jack_of_hearts.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageSpade_jack = new Image("file:assets/cards/jack_of_spades.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageClub_queen = new Image("file:assets/cards/queen_of_clubs.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageDiamond_queen = new Image("file:assets/cards/queen_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageHeart_queen = new Image("file:assets/cards/queen_of_hearts.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageSpade_queen = new Image("file:assets/cards/queen_of_spades.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageClub_king = new Image("file:assets/cards/king_of_clubs.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageDiamond_king = new Image("file:assets/cards/king_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageHeart_king = new Image("file:assets/cards/king_of_hearts.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageSpade_king = new Image("file:assets/cards/king_of_spades.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageClub_ace = new Image("file:assets/cards/ace_of_clubs.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageDiamond_ace = new Image("file:assets/cards/ace_of_diamonds.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageHeart_ace = new Image("file:assets/cards/ace_of_hearts.png", dimCardWidth, dimCardWidth, true, true)
  private lazy val imageSpade_ace = new Image("file:assets/cards/ace_of_spades.png", dimCardWidth, dimCardWidth, true, true)

  object Images extends Enumeration {
    val Image_Player,
    Image_Club_2, Image_Diamond_2, Image_Heart_2, Image_Spade_2,
    Image_Club_3, Image_Diamond_3, Image_Heart_3, Image_Spade_3,
    Image_Club_4, Image_Diamond_4, Image_Heart_4, Image_Spade_4,
    Image_Club_5, Image_Diamond_5, Image_Heart_5, Image_Spade_5,
    Image_Club_6, Image_Diamond_6, Image_Heart_6, Image_Spade_6,
    Image_Club_7, Image_Diamond_7, Image_Heart_7, Image_Spade_7,
    Image_Club_8, Image_Diamond_8, Image_Heart_8, Image_Spade_8,
    Image_Club_9, Image_Diamond_9, Image_Heart_9, Image_Spade_9,
    Image_Club_10, Image_Diamond_10, Image_Heart_10, Image_Spade_10,
    Image_Club_Jack, Image_Diamond_Jack, Image_Heart_Jack, Image_Spade_Jack,
    Image_Club_Queen, Image_Diamond_Queen, Image_Heart_Queen, Image_Spade_Queen,
    Image_Club_King, Image_Diamond_King, Image_Heart_King, Image_Spade_King,
    Image_Club_Ace, Image_Diamond_Ace, Image_Heart_Ace, Image_Spade_Ace = Value
  }

  private val imageCards: Map[GuiEnv.Images.Value, Image] = Map (
    Images.Image_Player -> imagePlayer,

    Images.Image_Club_2 -> imageClub_2,
    Images.Image_Diamond_2 -> imageDiamond_2,
    Images.Image_Heart_2 -> imageHeart_2,
    Images.Image_Spade_2 -> imageSpade_2,
    Images.Image_Club_3 -> imageClub_3,
    Images.Image_Diamond_3 -> imageDiamond_3,
    Images.Image_Heart_3 -> imageHeart_3,
    Images.Image_Spade_3 -> imageSpade_3,
    Images.Image_Club_4 -> imageClub_4,
    Images.Image_Diamond_4 -> imageDiamond_4,
    Images.Image_Heart_4 -> imageHeart_4,
    Images.Image_Spade_4 -> imageSpade_4,
    Images.Image_Club_5 -> imageClub_5,
    Images.Image_Diamond_5 -> imageDiamond_5,
    Images.Image_Heart_5 -> imageHeart_5,
    Images.Image_Spade_5 -> imageSpade_5,
    Images.Image_Club_6 -> imageClub_6,
    Images.Image_Diamond_6 -> imageDiamond_6,
    Images.Image_Heart_6 -> imageHeart_6,
    Images.Image_Spade_6 -> imageSpade_6,
    Images.Image_Club_7 -> imageClub_7,
    Images.Image_Diamond_7 -> imageDiamond_7,
    Images.Image_Heart_7 -> imageHeart_7,
    Images.Image_Spade_7 -> imageSpade_7,
    Images.Image_Club_8 -> imageClub_8,
    Images.Image_Diamond_8 -> imageDiamond_8,
    Images.Image_Heart_8 -> imageHeart_8,
    Images.Image_Spade_8 -> imageSpade_8,
    Images.Image_Club_9 -> imageClub_9,
    Images.Image_Diamond_9 -> imageDiamond_9,
    Images.Image_Heart_9 -> imageHeart_9,
    Images.Image_Spade_9 -> imageSpade_9,
    Images.Image_Club_10 -> imageClub_10,
    Images.Image_Diamond_10 -> imageDiamond_10,
    Images.Image_Heart_10 -> imageHeart_10,
    Images.Image_Spade_10 -> imageSpade_10,
    Images.Image_Club_Jack -> imageClub_jack,
    Images.Image_Diamond_Jack -> imageDiamond_jack,
    Images.Image_Heart_Jack -> imageHeart_jack,
    Images.Image_Spade_Jack -> imageSpade_jack,
    Images.Image_Club_Queen -> imageClub_queen,
    Images.Image_Diamond_Queen -> imageDiamond_queen,
    Images.Image_Heart_Queen -> imageHeart_queen,
    Images.Image_Spade_Queen -> imageSpade_queen,
    Images.Image_Club_King -> imageClub_king,
    Images.Image_Diamond_King -> imageDiamond_king,
    Images.Image_Heart_King -> imageHeart_king,
    Images.Image_Spade_King -> imageSpade_king,
    Images.Image_Club_Ace -> imageClub_ace,
    Images.Image_Diamond_Ace -> imageDiamond_ace,
    Images.Image_Heart_Ace -> imageHeart_ace,
    Images.Image_Spade_Ace -> imageSpade_ace
  )

  def getImage(img: Images.Value): Image = imageCards.get(img) match {
    case Some(i) => i
    case _ => throw new MatchError("Image enumeration not found in imageCards!")
  }
}
