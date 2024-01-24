package com.pamela.flashcards.domain.notification

import javax.inject.Inject

class NotificationsPreferencesOperations @Inject constructor(
    val getNotificationsPreferences: GetNotificationsPreferencesUseCase,
    val setMaxFlashCardNotifications: SetMaxFlashCardNotificationsPerDayUseCase,
    val setNotificationsEndHour: SetNotificationsEndHourUseCase,
    val setNotificationsStartHour: SetNotificationsStartHourUseCase,
    val setShouldShowFlashCardNotifications: SetShouldShowFlashCardNotificationsUseCase
)