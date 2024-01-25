package com.pamela.flashcards.domain.notification

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.pamela.flashcards.R
import com.pamela.flashcards.domain.deck.GetFlashCardDeckByIdUseCase
import com.pamela.flashcards.domain.flashcard.GetNextDueCardUseCase
import com.pamela.flashcards.domain.util.GetStringResourceUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CreateNextDueCardFrontNotificationUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getStringResource: GetStringResourceUseCase,
    private val getNextDueCard: GetNextDueCardUseCase,
    private val getFlashCardDeckById: GetFlashCardDeckByIdUseCase,
    private val getPendingIntentForStudyDeckLaunch: GetPendingIntentForStudyDeckLaunchUseCase,
    private val getPendingIntentForCardFlip: GetPendingIntentForCardFlipUseCase,
    private val notificationManagerCompat: NotificationManagerCompat
) {
    suspend operator fun invoke() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) return
        getNextDueCard().onSuccess {
            val deckName = getFlashCardDeckById(it.deckId).getOrNull()?.name
            val questionNotification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(deckName ?: getStringResource(R.string.app_name))
                .setContentText(it.front)
                .setStyle(NotificationCompat.BigTextStyle().bigText(it.front))
                .setContentIntent(getPendingIntentForStudyDeckLaunch(it.deckId.toString()))
                .addAction(
                    R.drawable.ic_launcher_foreground,
                    getStringResource(R.string.flip),
                    getPendingIntentForCardFlip(it.id)
                )
                .build()
            notificationManagerCompat.notify(it.id.hashCode(), questionNotification)
        }
    }

    companion object {
        const val CHANNEL_ID = "6221997"
        const val DEFAULT_FLAGS = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    }
}