package com.pamela.flashcards.domain.util

import com.pamela.flashcards.model.Difficulty
import com.pamela.flashcards.model.FlashCardDomain
import com.pamela.flashcards.util.getDaysInMillis
import com.pamela.flashcards.util.getHoursInMillis
import com.pamela.flashcards.util.getMinutesInMillis
import javax.inject.Inject

class GetNewIntervalByDifficultyUseCase @Inject constructor() {
    operator fun invoke(flashCard: FlashCardDomain, difficulty: Difficulty): Long {
        return if (flashCard.lastIntervalInMillis == 0L) {
            when (difficulty) {
                Difficulty.EASY -> getDaysInMillis(1)
                Difficulty.MEDIUM -> getHoursInMillis(4)
                Difficulty.HARD -> getMinutesInMillis(30)
                Difficulty.AGAIN -> getMinutesInMillis(5)
            }
        } else if (
            flashCard.lastIntervalInMillis < getHoursInMillis(12) &&
            difficulty == Difficulty.EASY
        ) {
            getDaysInMillis(1)
        } else {
            when (difficulty) {
                Difficulty.EASY -> flashCard.lastIntervalInMillis * 2L
                Difficulty.MEDIUM -> (flashCard.lastIntervalInMillis * 1.5).toLong()
                Difficulty.HARD -> (flashCard.lastIntervalInMillis * 1.25).toLong()
                Difficulty.AGAIN -> getMinutesInMillis(5)
            }
        }
    }
}