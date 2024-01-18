package com.pamela.flashcards.features.navigation

interface FlashCardsDestination {
    val route: String
    val routeWithArgs: String
}

object OverviewDestination: FlashCardsDestination {
    override val route = "overview"
    override val routeWithArgs: String
        get() = route
}

object PracticeDestination: FlashCardsDestination {
    override val route = "practice"
    const val cardSetId = "cardSetId"
    override val routeWithArgs: String
        get() = "$route/{$cardSetId}"

    fun populateRouteWithArgs(cardSetId: String): String {
        return "$route/$cardSetId"
    }
}

object PreviousDestination: FlashCardsDestination {
    override val route = "previous"
    override val routeWithArgs: String
        get() = route
}