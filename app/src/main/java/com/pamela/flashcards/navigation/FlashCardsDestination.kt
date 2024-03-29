package com.pamela.flashcards.navigation

import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink

interface FlashCardsDestination {
    val route: String
    val routeWithArgs: String
        get() = route
}

object OverviewDestination : FlashCardsDestination {
    override val route = "overview"
}

object NotificationsSettingsDestination : FlashCardsDestination {
    override val route = "notifications-settings"
}

object PracticeDestination : FlashCardsDestination {
    override val route = "practice"
    const val cardDeckId = "cardDeckId"
    override val routeWithArgs: String
        get() = "$route/{$cardDeckId}"

    private val deepLinkRoute = "FlashCards://$routeWithArgs"

    val deepLinks = listOf(navDeepLink { uriPattern = deepLinkRoute })

    fun populateRouteWithArgs(cardDeckId: String): String {
        return "$route/$cardDeckId"
    }

    fun populateDeepLinkRouteWithArgs(cardDeckId: String): String {
        return "FlashCards://$route/$cardDeckId"
    }
}

object AddDeckDestination : FlashCardsDestination {
    override val route = "add-deck"
    const val cardDeckId = "cardDeckId"
    override val routeWithArgs: String
        get() = "$route/{$cardDeckId}"

    fun populateRouteWithArgs(cardDeckId: String? = null): String {
        return "$route/$cardDeckId"
    }
}

object AddCardDestination : FlashCardsDestination {
    override val route = "add-card"
    const val cardDeckId = "cardDeckId"
    const val cardId = "cardId"
    override val routeWithArgs: String
        get() = "$route/{$cardDeckId}/{$cardId}"

    fun populateRouteWithArgs(cardDeckId: String? = null, cardId: String? = null): String {
        return "$route/$cardDeckId/$cardId"
    }
}

object PreviousDestination : FlashCardsDestination {
    override val route = "previous"
}

object NavDrawerDestination : FlashCardsDestination {
    override val route = "nav-drawer"
}