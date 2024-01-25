package com.pamela.flashcards.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.pamela.flashcards.domain.notification.CreateNextDueCardBackNotificationUseCase
import com.pamela.flashcards.domain.notification.CreateNextDueCardFrontNotificationUseCase
import com.pamela.flashcards.domain.flashcard.GetFlashCardByIdUseCase
import com.pamela.flashcards.domain.flashcard.UpdateFlashCardStatsUseCase
import com.pamela.flashcards.domain.notification.GetNotificationsPreferencesUseCase
import com.pamela.flashcards.model.Difficulty
import com.pamela.flashcards.util.getUuidOrNull
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import java.time.ZonedDateTime
import javax.inject.Inject

@AndroidEntryPoint
class FlashCardNotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var showCardNotification: CreateNextDueCardFrontNotificationUseCase
    @Inject
    lateinit var showBackNotification: CreateNextDueCardBackNotificationUseCase
    @Inject
    lateinit var updateFlashCardStats: UpdateFlashCardStatsUseCase
    @Inject
    lateinit var getFlashCardById: GetFlashCardByIdUseCase
    @Inject
    lateinit var getNotificationsPreferences: GetNotificationsPreferencesUseCase
    @Inject
    lateinit var notificationManagerCompat: NotificationManagerCompat

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.getStringExtra("action")) {
            "showCard" -> {
                runBlocking {
                    val preferences = getNotificationsPreferences().getOrThrow()
                    if (preferences.shouldSendFlashCards.not()) return@runBlocking
                    if (preferences.maxFlashCardsSentPerDay <= 0) return@runBlocking
                    if (preferences.flashCardsEndHour <= ZonedDateTime.now().hour) return@runBlocking
                    if (preferences.flashCardsStartHour > ZonedDateTime.now().hour) return@runBlocking
                    showCardNotification()
                }
            }
            "showBack" -> {
                runBlocking {
                    showBackNotification()
                }
            }
            "updateCard" -> {
                val flashCardId = getUuidOrNull(intent.getStringExtra("flashCardId"))
                val difficulty = intent.getStringExtra("difficulty")?.let { Difficulty.valueOf(it) }

                if (flashCardId != null && difficulty != null) {
                    runBlocking {
                        getFlashCardById(flashCardId)
                            .onSuccess {
                                updateFlashCardStats(flashCard = it, difficulty = difficulty)
                                notificationManagerCompat.cancelAll()
                            }
                    }
                }
            }
            else -> { /* no-op */ }
        }
    }
}