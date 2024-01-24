package com.pamela.flashcards.domain.notification

import com.pamela.flashcards.database.preferences.PreferencesRepository
import com.pamela.flashcards.util.getCancellableResult
import javax.inject.Inject

class SetNotificationsEndHourUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(int: Int): Result<Unit> {
        return getCancellableResult {
            preferencesRepository.setNotificationsEndHour(int)
            Result.success(Unit)
        }
    }
}