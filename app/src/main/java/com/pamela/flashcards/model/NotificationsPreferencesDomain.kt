package com.pamela.flashcards.model

data class NotificationsPreferencesDomain(
    val shouldSendFlashCards: Boolean = false,
    val flashCardsStartHour: Int = 0,
    val flashCardsEndHour: Int = 23,
    val maxFlashCardsSentPerDay: Int = 10
)
