package de.htwg.se.awol.model.environmentComponents

sealed trait CardEnvironment

case object C_Clubs extends CardEnvironment
case object C_Spades extends CardEnvironment
case object C_Hearts extends CardEnvironment
case object C_Diamonds extends CardEnvironment
case object C_Jack extends CardEnvironment
case object C_Queen extends CardEnvironment
case object C_King extends CardEnvironment
case object C_Ace extends CardEnvironment