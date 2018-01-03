package de.htwg.se.awol.model.environmentComponents

object MessageEnv {
  object Warnings extends Enumeration {
    val DividableByFour, MaxAmountOfCards, MissingLanguage, MissingTranslation = Value
  }
}