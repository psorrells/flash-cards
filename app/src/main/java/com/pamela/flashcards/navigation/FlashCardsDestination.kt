package com.pamela.flashcards.navigation

interface FlashCardsDestination {
    val route: String
    val routeWithArgs: String
        get() = route
}

object OverviewDestination : FlashCardsDestination {
    override val route = "overview"
}

object PracticeDestination : FlashCardsDestination {
    override val route = "practice"
    const val cardSetId = "cardSetId"
    override val routeWithArgs: String
        get() = "$route/{$cardSetId}"

    fun populateRouteWithArgs(cardSetId: String): String {
        return "$route/$cardSetId"
    }
}

object AddSetDestination : FlashCardsDestination {
    override val route = "add-set"
    const val cardSetId = "cardSetId"
    override val routeWithArgs: String
        get() = "$route/{$cardSetId}"

    fun populateRouteWithArgs(cardSetId: String? = null): String {
        return "$route/$cardSetId"
    }
}

object AddCardDestination : FlashCardsDestination {
    override val route = "add-card"
    const val cardSetId = "cardSetId"
    const val cardId = "cardId"
    override val routeWithArgs: String
        get() = "$route/{$cardSetId}/{$cardId}"

    fun populateRouteWithArgs(cardSetId: String? = null, cardId: String? = null): String {
        return "$route/$cardSetId/$cardId"
    }
}

object PreviousDestination : FlashCardsDestination {
    override val route = "previous"
}