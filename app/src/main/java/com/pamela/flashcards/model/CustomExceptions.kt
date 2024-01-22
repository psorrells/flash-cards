package com.pamela.flashcards.model

import java.util.UUID


open class UserErrorException : Exception() {
    override val message = "this is manageable via the UI"
}
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

class InvalidFlashCardDeckIdError(deckId: UUID) : UserErrorException() {
    override val message = "The deck with id $deckId does not exist!"
}

class IncompleteFormError() : UserErrorException() {
    override val message = "one or more required fields in form are left blank"
}