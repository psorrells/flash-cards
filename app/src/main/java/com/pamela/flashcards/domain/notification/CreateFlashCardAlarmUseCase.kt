package com.pamela.flashcards.domain.notification

import android.app.AlarmManager
import com.pamela.flashcards.util.getHoursInMillis
import java.time.ZonedDateTime
import javax.inject.Inject

class CreateFlashCardAlarmUseCase @Inject constructor(
    private val getNotificationsPreferences: GetNotificationsPreferencesUseCase,
    private val getPendingIntentForFlashCardAlarm: GetPendingIntentForFlashCardAlarmUseCase,
    private val alarmManager: AlarmManager
) {
    suspend operator fun invoke() {
        val preferences = getNotificationsPreferences().getOrThrow()

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
            getPendingIntentForFlashCardAlarm()
        )
    }
}