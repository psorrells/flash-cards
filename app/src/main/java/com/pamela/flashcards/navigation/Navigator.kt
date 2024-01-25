package com.pamela.flashcards.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

interface Navigator {
    val navAction: StateFlow<NavigationAction?>

    fun navigateTo(route: String)

    fun popBackStack()

    fun navigateToOverview()

    fun navigateAndPopUpToOverview(route: String)

}

class NavigatorImpl @Inject constructor() : Navigator {

    private val _navAction: MutableStateFlow<NavigationAction?> by lazy {
        MutableStateFlow(null)
    }
    override val navAction = _navAction.asStateFlow()

    override fun navigateTo(route: String) {
        _navAction.update {
            createDefaultNavigationAction(route)
        }
    }

    override fun popBackStack() {
        _navAction.update {
            createDefaultNavigationAction(PreviousDestination.route)
        }
    }

    override fun navigateToOverview() {
        _navAction.update {
            createPopUpToOverviewNavigationAction(OverviewDestination.route)
        }
    }

    override fun navigateAndPopUpToOverview(route: String) {
        _navAction.update {
            createPopUpToOverviewNavigationAction(route)
        }
    }


    private fun createDefaultNavigationAction(route: String): NavigationAction {
        return object : NavigationAction {
            override val route = route
            override val navOptions = NavOptions.Builder().setLaunchSingleTop(true).build()
        }
    }

    private fun createPopUpToOverviewNavigationAction(route: String): NavigationAction {
        return object : NavigationAction {
            override val route = route
            override val navOptions = NavOptions
                .Builder()
                .setLaunchSingleTop(true)
                .setPopUpTo(
                    route = OverviewDestination.route,
                    inclusive = false,
                    saveState = false
                )
                .build()
        }
    }
}

interface NavigationAction {
    val route: String
    val navOptions: NavOptions
        get() = NavOptions.Builder().build()
}