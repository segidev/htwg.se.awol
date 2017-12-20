package de.htwg.se.awol.model.environmentComponents

object MessageEnvironment {
  object Warnings extends Enumeration {
    val DividableByFour, MaxAmountOfCards, MissingLanguage, MissingTranslation = Value
  }
}