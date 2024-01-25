package com.pamela.flashcards.domain.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.pamela.flashcards.domain.notification.CreateNextDueCardFrontNotificationUseCase.Companion.DEFAULT_FLAGS
import com.pamela.flashcards.receivers.FlashCardNotificationReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.UUID
import javax.inject.Inject

class GetPendingIntentForCardFlipUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(cardId: UUID): PendingIntent {
        val showBackIntent = Intent(context, FlashCardNotificationReceiver::class.java)
        showBackIntent.putExtra("action", "showBack")
        return PendingIntent.getBroadcast(context, cardId.hashCode(), showBackIntent, DEFAULT_FLAGS)
    }
}