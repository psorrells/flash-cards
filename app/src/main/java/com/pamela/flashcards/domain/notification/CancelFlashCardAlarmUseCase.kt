package com.pamela.flashcards.domain.notification

import android.app.AlarmManager
import javax.inject.Inject

class CancelFlashCardAlarmUseCase @Inject constructor(
    private val getPendingIntentForFlashCardAlarm: GetPendingIntentForFlashCardAlarmUseCase,
    private val alarmManager: AlarmManager
) {
    operator fun invoke() {
        alarmManager.cancel(getPendingIntentForFlashCardAlarm())
    }
}