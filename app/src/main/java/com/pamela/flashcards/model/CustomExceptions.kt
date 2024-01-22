package com.pamela.flashcards.model

class EmptyResultError : Exception() {
    override val message = "Expected return value, but result was empty!"
}

class MissingSavedStateError(arg: String) : Exception() {
    override val message = "Expected to have $arg from saved state handle, but was not present!"
}

class GetNewCardException : Exception() {
    override val message = "Time to fetch a new card!"
}

class FailedDeleteError(e: Throwable) : Exception() {
    override val message = "Failed to delete record: ${e.message}"
    override val cause = e
}