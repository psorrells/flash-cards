package com.pamela.flashcards.domain.notification

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.pamela.flashcards.R
import com.pamela.flashcards.domain.flashcard.GetNextDueCardUseCase
import com.pamela.flashcards.domain.notification.CreateNextDueCardFrontNotificationUseCase.Companion.CHANNEL_ID
import com.pamela.flashcards.domain.util.GetStringResourceUseCase
import com.pamela.flashcards.model.Difficulty
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CreateNextDueCardBackNotificationUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getStringResource: GetStringResourceUseCase,
    private val getNextDueCard: GetNextDueCardUseCase,
    private val getPendingIntentForStudyDeckLaunch: GetPendingIntentForStudyDeckLaunchUseCase,
    private val getPendingIntentForCardUpdate: GetPendingIntentForCardUpdateUseCase,
    private val notificationManagerCompat: NotificationManagerCompat
) {
    suspend operator fun invoke() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) return
        getNextDueCard().onSuccess {
            val answerNotification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(it.front)
                .setContentText(it.back)
                .setStyle(NotificationCompat.BigTextStyle().bigText(it.back))
                .setContentIntent(getPendingIntentForStudyDeckLaunch(it.deckId.toString()))
                .addAction(
                    R.drawable.ic_launcher_foreground,
                    getStringResource(R.string.good),
                    getPendingIntentForCardUpdate(it.id, Difficulty.MEDIUM)
                )
                .addAction(
                    R.drawable.ic_launcher_foreground,
                    getStringResource(R.string.again),
                    getPendingIntentForCardUpdate(it.id, Difficulty.AGAIN)
                )
                .build()
            notificationManagerCompat.notify(it.id.hashCode(), answerNotification)
        }
    }
}