package de.htwg.se.awol.model.languageComponents

import de.htwg.se.awol.model.environmentComponents.{SettingEnv, _}

object LanguageEnglish extends _LanguageHandler {
  override val translations: Map[Any, String] = Map(
    PlayerEnv.Rank.Mob -> "Mob",
    PlayerEnv.Rank.King -> "King",
    PlayerEnv.Rank.Viceroy -> "Viceroy",
    PlayerEnv.Rank.Viceasshole -> "Viceasshole",
    PlayerEnv.Rank.Asshole -> "Asshole",

    PlayerEnv.BotNames.Player_1 -> "You",

    CardEnv.Colors.Clubs -> "Club",
    CardEnv.Colors.Spades -> "Spade",
    CardEnv.Colors.Hearts -> "Heart",
    CardEnv.Colors.Diamonds -> "Diamond",
    CardEnv.Values.Two -> "2",
    CardEnv.Values.Three -> "3",
    CardEnv.Values.Four -> "4",
    CardEnv.Values.Five -> "5",
    CardEnv.Values.Six -> "6",
    CardEnv.Values.Seven -> "7",
    CardEnv.Values.Eight -> "8",
    CardEnv.Values.Nine -> "9",
    CardEnv.Values.Ten -> "10",
    CardEnv.Values.Jack -> "Jack",
    CardEnv.Values.Queen -> "Queen",
    CardEnv.Values.King -> "King",
    CardEnv.Values.Ace -> "Ace",

    MessageEnv.Warnings.DividableByFour -> "Amount of cards must be dividable by 4!",
    MessageEnv.Warnings.MaxAmountOfCards -> "Amount of cards must be in between 32 and 52",
    MessageEnv.Warnings.MissingLanguage -> "The language you specified was not found: ",
    MessageEnv.Warnings.MissingTranslation -> "No translation found for: ",
    MessageEnv.Warnings.PlayerCountMismatch -> "Player count of %d is not allowed!",

    MessageEnv.Menues.File -> "File",
    MessageEnv.Menues.NewGame -> "New Game",
    MessageEnv.Menues.Quit -> "Quit",
    MessageEnv.Menues.Options -> "Options",
    MessageEnv.Menues.Players_2 -> "2 Players",
    MessageEnv.Menues.Players_4 -> "4 Players",
    MessageEnv.Menues.Players_6 -> "6 Players",
    MessageEnv.Menues.Players_8 -> "8 Players",
    MessageEnv.Menues.DeckSize -> "Decksize",
    MessageEnv.Menues.StartGame -> "Start Game",
    MessageEnv.Menues.Cancel -> "Cancel",
    MessageEnv.Menues.GameSpeed -> "Game Speed",
    MessageEnv.Menues.Fast -> "Fast",
    MessageEnv.Menues.Normal -> "Normal",
    MessageEnv.Menues.Slow -> "Slow",
    MessageEnv.Menues.GameLanguage -> "Language",

    MessageEnv.Words.Yes -> "Yes",
    MessageEnv.Words.No -> "No",
    MessageEnv.Words.Pass -> "Pass",
    MessageEnv.Words.CardSwap -> "Card swap time!\n\n",
    MessageEnv.Words.EndOfGame -> "End of game\n\n",

    MessageEnv.Titles.GameTitle -> "Asshole",
    MessageEnv.Titles.QuitGame -> "Quit Game",
    MessageEnv.Titles.GameOptions -> "Game Options",

    MessageEnv.Questions.QuitGame -> "Do you really want to exit the game?",

    MessageEnv.PhrasesGeneral.WelcomeToTheGame -> "Welcome to the game",
    MessageEnv.PhrasesGeneral.ClickAnywhereToContinue -> "\nClick anywhere to continue...",
    MessageEnv.PhrasesGeneral.HitEnterToContinue -> "\nHit Enter to continue...",
    MessageEnv.PhrasesGeneral.GameHasStarted -> "The game has started",
    MessageEnv.PhrasesGeneral.CardsHandedOutToPlayers -> "Every play received cards",

    MessageEnv.PhrasesBot.WonTheRound -> "%s has won the round!",
    MessageEnv.PhrasesBot.IsTheWinner -> "%s ist the winner and ranks as %s\n",
    MessageEnv.PhrasesBot.IsTheLooser -> "%s lost and is the new %s\n",
    MessageEnv.PhrasesBot.ReceivesCardFrom -> "%s receives %s from %s\n",
    MessageEnv.PhrasesBot.ReceivesCardFromHuman -> "%s receives %s from you\n",
    MessageEnv.PhrasesBot.IsPlayingNow -> "\n== %s is playing now! ==\n",
    MessageEnv.PhrasesBot.Passed -> "\n%s passed",
    MessageEnv.PhrasesBot.UsedTheseCards -> "\n%s played those cards: %s",
    MessageEnv.PhrasesBot.HasCardsLeft -> "\n%s has %d card(s) left",

    MessageEnv.PhrasesHuman.WonTheRound -> "You have won the round! Nice :)",
    MessageEnv.PhrasesHuman.IsTheWinner -> "You are the winner and you rank as %s\n",
    MessageEnv.PhrasesHuman.IsTheLooser -> "You have lost, that makes you the %s\n",
    MessageEnv.PhrasesHuman.ReceivesCardFrom -> "You receive %s from %s\n",
    MessageEnv.PhrasesHuman.SuitableCards -> "You can play these cards: %s",
    MessageEnv.PhrasesHuman.NoSuitableCards -> "No suitable cards found!",
    MessageEnv.PhrasesHuman.IsPlayingNow -> "\n== It's your turn! ==",
    MessageEnv.PhrasesHuman.Passed -> "You passed",
    MessageEnv.PhrasesHuman.PassForbidden -> "You are not allowed to pass. Please put some cards.",
    MessageEnv.PhrasesHuman.YouPlayedThoseCards -> "You played those cards: %s",
    MessageEnv.PhrasesHuman.HowManyCardsToPlay -> "How many cards do you want to play?",
    MessageEnv.PhrasesHuman.CommandNotAvailable -> "Command not possible, please try another one.",
    MessageEnv.PhrasesHuman.CardCommandNotAvailable -> "Command \"%s\" not available for cards",

    SettingEnv.Language.German -> "German",
    SettingEnv.Language.English -> "English",
    SettingEnv.Language.Youth -> "Youngster Talk"
  )

  override def getLanguageCode: SettingEnv.Language.Value = SettingEnv.Language.English
}