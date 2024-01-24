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
import com.pamela.flashcards.receivers.FlashCardNotificationReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CreateNextDueCardFrontNotificationUseCase @Inject constructor(
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
            val showBackIntent = Intent(context, FlashCardNotificationReceiver::class.java)
            showBackIntent.putExtra("action", "showBack")
            val showBackPendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    it.id.hashCode(),
                    showBackIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            val launchActivityPendingIntent = PendingIntent.getActivity(
                context,
                0,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val questionNotification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getStringResource(R.string.app_name))
                .setContentText(it.front)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(it.front)
                )
                .setContentIntent(launchActivityPendingIntent)
                .addAction(
                    R.drawable.ic_launcher_foreground,
                    getStringResource(R.string.flip),
                    showBackPendingIntent
                )
                .build()
            notificationManagerCompat.notify(it.id.hashCode(), questionNotification)
        }
    }

    companion object {
        const val CHANNEL_ID = "6221997"
    }
}