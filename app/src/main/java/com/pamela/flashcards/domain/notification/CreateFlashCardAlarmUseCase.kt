package com.pamela.flashcards.domain.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.pamela.flashcards.receivers.FlashCardNotificationReceiver
import com.pamela.flashcards.util.getHoursInMillis
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Instant
import java.time.ZonedDateTime
import javax.inject.Inject

class CreateFlashCardAlarmUseCase @Inject constructor(
    private val getNotificationsPreferencesUseCase: GetNotificationsPreferencesUseCase,
    private val getFlashCardAlarmPendingIntentUseCase: GetFlashCardAlarmPendingIntentUseCase,
    private val alarmManager: AlarmManager
) {
    suspend operator fun invoke() {
        val preferences = getNotificationsPreferencesUseCase().getOrThrow()

        val startTime = ZonedDateTime
            .now()
            .withHour(preferences.flashCardsStartHour)
            .withMinute(0)
            .withSecond(0)
            .toInstant()
            .toEpochMilli()

        val windowInHours = preferences.flashCardsEndHour - preferences.flashCardsStartHour
        val expectedInterval = getHoursInMillis(windowInHours) / preferences.maxFlashCardsSentPerDay

        alarmManager.setInexactRepeating(
            AlarmManager.RTC,
            startTime,
            expectedInterval,
            getFlashCardAlarmPendingIntentUseCase()
        )
    }
}