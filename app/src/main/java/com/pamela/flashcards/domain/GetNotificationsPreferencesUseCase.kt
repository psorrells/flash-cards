package com.pamela.flashcards.domain

import com.pamela.flashcards.database.preferences.PreferencesRepository
import com.pamela.flashcards.model.NotificationsPreferencesDomain
import com.pamela.flashcards.util.getCancellableResult
import javax.inject.Inject

class GetNotificationsPreferencesUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(): Result<NotificationsPreferencesDomain> {
        return getCancellableResult {
            Result.success(preferencesRepository.getNotificationsPreferences())
        }
    }
}