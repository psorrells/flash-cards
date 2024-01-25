package com.pamela.flashcards.domain.notification

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.pamela.flashcards.MainActivity
import com.pamela.flashcards.domain.notification.CreateNextDueCardFrontNotificationUseCase.Companion.DEFAULT_FLAGS
import com.pamela.flashcards.navigation.PracticeDestination
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetPendingIntentForStudyDeckLaunchUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(deckId: String): PendingIntent {
        val deepLink = PracticeDestination.populateDeepLinkRouteWithArgs(deckId)
        val studyDeckIntent = Intent(
            Intent.ACTION_VIEW,
            deepLink.toUri(),
            context,
            MainActivity::class.java
        )

        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(studyDeckIntent)
            getPendingIntent(0, DEFAULT_FLAGS)
        }
    }
}