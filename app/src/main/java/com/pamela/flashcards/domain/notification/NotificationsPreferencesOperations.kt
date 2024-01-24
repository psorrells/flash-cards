package com.pamela.flashcards.domain.notification

import javax.inject.Inject

class NotificationsPreferencesOperations @Inject constructor(
    val getNotificationsPreferences: GetNotificationsPreferencesUseCase,
    val setNotificationsPreferences: SetNotificationsPreferencesUseCase
)