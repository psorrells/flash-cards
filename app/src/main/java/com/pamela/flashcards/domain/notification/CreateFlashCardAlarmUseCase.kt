package com.pamela.flashcards.domain.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.pamela.flashcards.receivers.FlashCardNotificationReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CreateFlashCardAlarmUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke() {
        val alarmManager: AlarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val showCardIntent = Intent(context, FlashCardNotificationReceiver::class.java)
        showCardIntent.putExtra("action", "showCard")
        val showCardPendingIntent =
            PendingIntent.getBroadcast(
                context,
                0,
                showCardIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )


        alarmManager.setInexactRepeating(
            AlarmManager.RTC,
            System.currentTimeMillis(),
            AlarmManager.INTERVAL_HOUR,
            showCardPendingIntent
        )
    }
}