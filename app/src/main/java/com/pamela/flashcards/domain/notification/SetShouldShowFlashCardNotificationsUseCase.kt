package com.pamela.flashcards.domain.notification

import com.pamela.flashcards.database.preferences.PreferencesRepository
import com.pamela.flashcards.util.getCancellableResult
import javax.inject.Inject

class SetShouldShowFlashCardNotificationsUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(boolean: Boolean): Result<Unit> {
        return getCancellableResult {
            preferencesRepository.setShouldSendFlashCardNotifications(boolean)
            Result.success(Unit)
        }
    }
}