package com.pamela.flashcards.domain.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.pamela.flashcards.domain.notification.CreateNextDueCardFrontNotificationUseCase.Companion.DEFAULT_FLAGS
import com.pamela.flashcards.model.Difficulty
import com.pamela.flashcards.receivers.FlashCardNotificationReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.UUID
import javax.inject.Inject

class GetPendingIntentForCardUpdateUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(cardId: UUID, difficulty: Difficulty): PendingIntent {
        val updateCardIntent = Intent(context, FlashCardNotificationReceiver::class.java)
        updateCardIntent.putExtra("action", "updateCard")
        updateCardIntent.putExtra("flashCardId", cardId.toString())
        updateCardIntent.putExtra("difficulty", difficulty.name)
        return PendingIntent.getBroadcast(context, cardId.hashCode(), updateCardIntent, DEFAULT_FLAGS)
    }
}