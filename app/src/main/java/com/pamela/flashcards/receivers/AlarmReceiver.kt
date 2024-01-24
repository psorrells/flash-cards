package com.pamela.flashcards.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.pamela.flashcards.domain.notification.CreateFlashCardAlarmUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var createFlashCardAlarmUseCase: CreateFlashCardAlarmUseCase
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            createFlashCardAlarmUseCase()
        }
    }
}