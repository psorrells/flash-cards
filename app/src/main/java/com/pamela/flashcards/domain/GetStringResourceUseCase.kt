package com.pamela.flashcards.domain

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetStringResourceUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(id: Int): String {
        return context.getString(id)
    }
}