package com.pamela.flashcards.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.pamela.flashcards.domain.notification.CreateFlashCardAlarmUseCase
import com.pamela.flashcards.domain.notification.GetNotificationsPreferencesUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var createFlashCardAlarmUseCase: CreateFlashCardAlarmUseCase
    @Inject
    lateinit var getNotificationsPreferencesUseCase: GetNotificationsPreferencesUseCase
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            runBlocking {
                val preferences = getNotificationsPreferencesUseCase().getOrThrow()

                if (preferences.shouldSendFlashCards.not()) return@runBlocking
                if (preferences.maxFlashCardsSentPerDay <= 0 ) return@runBlocking

                createFlashCardAlarmUseCase()
            }
        }
    }
}