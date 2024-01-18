package com.pamela.flashcards.navigation

interface FlashCardsDestination {
    val route: String
    val routeWithArgs: String
        get() = route
}

object OverviewDestination: FlashCardsDestination {
    override val route = "overview"
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

object AddSetDestination: FlashCardsDestination {
    override val route = "add-set"
}

object PreviousDestination: FlashCardsDestination {
    override val route = "previous"
}