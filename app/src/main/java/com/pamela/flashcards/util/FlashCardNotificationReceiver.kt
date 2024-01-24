package com.pamela.flashcards.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.pamela.flashcards.domain.CreateNextDueCardBackNotificationUseCase
import com.pamela.flashcards.domain.CreateNextDueCardFrontNotificationUseCase
import com.pamela.flashcards.domain.GetFlashCardByIdUseCase
import com.pamela.flashcards.domain.UpdateFlashCardStatsUseCase
import com.pamela.flashcards.model.Difficulty
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import java.util.logging.Logger
import javax.inject.Inject

@AndroidEntryPoint
class FlashCardNotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var showCardNotificationUseCase: CreateNextDueCardFrontNotificationUseCase
    @Inject
    lateinit var showBackNotificationUseCase: CreateNextDueCardBackNotificationUseCase
    @Inject
    lateinit var updateFlashCardStatsUseCase: UpdateFlashCardStatsUseCase
    @Inject
    lateinit var getFlashCardByIdUseCase: GetFlashCardByIdUseCase

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.getStringExtra("action")) {
            "showCard" -> {
                runBlocking {
                    showCardNotificationUseCase()
                }
            }
            "showBack" -> {
                runBlocking {
                    showBackNotificationUseCase()
                }
            }

            "updateCard" -> {
                val flashCardId = getUuidOrNull(intent.getStringExtra("flashCardId"))
                val difficulty = intent.getStringExtra("difficulty")?.let { Difficulty.valueOf(it) }

                if (flashCardId != null && difficulty != null) {
                    runBlocking {
                        getFlashCardByIdUseCase(flashCardId)
                            .onSuccess {
                                updateFlashCardStatsUseCase(flashCard = it, difficulty = difficulty)
                                NotificationManagerCompat.from(context).cancelAll()
                            }
                    }
                }
            }

            else -> {


            }
        }
    }
}