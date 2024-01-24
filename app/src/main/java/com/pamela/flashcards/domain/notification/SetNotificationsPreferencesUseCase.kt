package com.pamela.flashcards.domain.notification

import com.pamela.flashcards.database.preferences.PreferencesRepository
import com.pamela.flashcards.model.NotificationsPreferencesDomain
import com.pamela.flashcards.util.getCancellableResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.invoke
import javax.inject.Inject

class SetNotificationsPreferencesUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(newPrefs: NotificationsPreferencesDomain): Result<Unit> {
        return getCancellableResult {
            Dispatchers.Default.invoke {
                awaitAll(
                    async { preferencesRepository.setShouldSendFlashCardNotifications(newPrefs.shouldSendFlashCards) },
                    async { preferencesRepository.setMaxNotificationCardsPerDay(newPrefs.maxFlashCardsSentPerDay) },
                    async { preferencesRepository.setNotificationsStartHour(newPrefs.flashCardsStartHour) },
                    async { preferencesRepository.setNotificationsEndHour(newPrefs.flashCardsEndHour) }
                )
                Result.success(Unit)
            }
        }
    }
}