package de.htwg.se.awol.model.environmentComponents

import de.htwg.se.awol.model.cardComponents.Card
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

import scalafx.embed.swing.SFXPanel
import scalafx.scene.image.{Image, ImageView}


@RunWith(classOf[JUnitRunner])
class GuiSpec extends WordSpec with Matchers {
  new SFXPanel()

  "All getter mehthods" should {
    "work if GUI is initialized" in {
      val tableImage: Image = GuiEnv.getTableImage
      val imageView: ImageView = GuiEnv.getLeadingImageView
      val playerImage: Image = GuiEnv.getPlayerImage
      val cardSize: (Double, Double) = GuiEnv.getCardSize
    }
  }
  "Every card" should {
    "have an ImageView" in {
      val amount: Int = 52
      val cardStackSize: Int = amount / 4
      val starterCard: Int = 2

      for (i <- 0 until amount) {
        val cardNum = CardEnv.Values.apply(i % cardStackSize + starterCard)
        val cardColor = CardEnv.Colors.apply(i / cardStackSize)

        GuiEnv.getCardImage(Card(cardNum, cardColor))
      }
    }
  }
  "Enumerations" should {
    "be covered" in {
      GuiEnv.Layout.values
    }
  }
}
