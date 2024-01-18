package com.pamela.flashcards.model

class EmptyResultError : Exception() {
    override val message = "Expected return value, but result was empty!"
}