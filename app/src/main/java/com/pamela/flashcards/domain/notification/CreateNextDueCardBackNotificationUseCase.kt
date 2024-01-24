package com.pamela.flashcards.domain.notification

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.pamela.flashcards.MainActivity
import com.pamela.flashcards.R
import com.pamela.flashcards.domain.flashcard.GetNextDueCardUseCase
import com.pamela.flashcards.domain.util.GetStringResourceUseCase
import com.pamela.flashcards.model.Difficulty
import com.pamela.flashcards.receivers.FlashCardNotificationReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CreateNextDueCardBackNotificationUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getStringResource: GetStringResourceUseCase,
    private val getNextDueCard: GetNextDueCardUseCase,
    private val notificationManagerCompat: NotificationManagerCompat
) {
    suspend operator fun invoke() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) return
        getNextDueCard().onSuccess {
            val updateCardPendingIntent = { difficulty: Difficulty ->

                val updateFlashCardIntent =
                    Intent(context, FlashCardNotificationReceiver::class.java)
                updateFlashCardIntent.putExtra("action", "updateCard")
                updateFlashCardIntent.putExtra("flashCardId", it.id.toString())
                updateFlashCardIntent.putExtra("difficulty", difficulty.name)

                PendingIntent.getBroadcast(
                    context,
                    it.id.hashCode(),
                    updateFlashCardIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
            val launchActivityPendingIntent = PendingIntent.getActivity(
                context,
                0,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val answerNotification = NotificationCompat.Builder(
                context,
                CreateNextDueCardFrontNotificationUseCase.CHANNEL_ID
            )
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getStringResource(R.string.app_name))
                .setContentText(it.back)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(it.back)
                )
                .setContentIntent(launchActivityPendingIntent)
                .addAction(
                    R.drawable.ic_launcher_foreground,
                    getStringResource(R.string.good),
                    updateCardPendingIntent(Difficulty.MEDIUM)
                )
                .addAction(
                    R.drawable.ic_launcher_foreground,
                    getStringResource(R.string.again),
                    updateCardPendingIntent(Difficulty.AGAIN)
                )
                .build()

            notificationManagerCompat.notify(it.id.hashCode(), answerNotification)
        }
    }
}