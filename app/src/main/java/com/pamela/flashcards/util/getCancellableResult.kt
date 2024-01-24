package com.pamela.flashcards.util

import java.util.concurrent.CancellationException

inline fun <T> getCancellableResult(method: () -> Result<T>): Result<T> {
    return try {
        method()
    } catch (cancel: CancellationException) {
        throw cancel
    } catch (e: Exception) {
        Result.failure(e)
    }
}