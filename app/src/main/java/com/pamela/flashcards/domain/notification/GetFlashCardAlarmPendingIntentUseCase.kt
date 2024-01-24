package com.pamela.flashcards.domain.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.pamela.flashcards.receivers.FlashCardNotificationReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetFlashCardAlarmPendingIntentUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(): PendingIntent {
        val showCardIntent = Intent(context, FlashCardNotificationReceiver::class.java)
        showCardIntent.putExtra("action", "showCard")
        return PendingIntent.getBroadcast(
            context,
            0,
            showCardIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}