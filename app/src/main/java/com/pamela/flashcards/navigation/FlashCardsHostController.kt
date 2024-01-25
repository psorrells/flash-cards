package com.pamela.flashcards.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pamela.flashcards.features.addcard.AddCardScreen
import com.pamela.flashcards.features.adddeck.AddDeckScreen
import com.pamela.flashcards.features.notifications.NotificationsSettingsScreen
import com.pamela.flashcards.features.overview.OverviewScreen
import com.pamela.flashcards.features.practice.PracticeScreen
import kotlinx.coroutines.launch

@Composable
fun FlashCardsHostController(
    navController: NavHostController = rememberNavController(),
    viewModel: HostControllerViewModel = hiltViewModel()
) {

    val navAction by viewModel.navigator.navAction.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val openDrawer = { coroutineScope.launch { drawerState.open() } }

    LaunchedEffect(navAction) {
        navAction?.route?.let { route ->
            when (route) {
                PreviousDestination.route -> navController.popBackStack()
                NavDrawerDestination.route -> openDrawer()
                else -> navController.navigate(route, navAction?.navOptions)
            }
        }
        navAction?.deepLink?.let { route ->
            navController.navigate(route, navAction?.navOptions)
        }
    }


    DefaultNavigationDrawer(drawerState = drawerState) {
        NavHost(navController = navController, startDestination = viewModel.getStartDestination()) {
            composable(
                route = OverviewDestination.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { fadeOut() }
            ) { OverviewScreen() }
            composable(
                route = PracticeDestination.routeWithArgs,
                deepLinks = PracticeDestination.deepLinks,
                enterTransition = { EnterTransition.None },
                exitTransition = { fadeOut() }
            ) { PracticeScreen() }
            composable(
                route = AddDeckDestination.routeWithArgs,
                enterTransition = { slideInVertically(animationSpec = tween(500), initialOffsetY = { it }) },
                exitTransition = { slideOutVertically(animationSpec = tween(500), targetOffsetY = { it }) }
            ) { AddDeckScreen() }
            composable(
                route = AddCardDestination.routeWithArgs,
                enterTransition = { slideInVertically(animationSpec = tween(500), initialOffsetY = { it }) },
                exitTransition = { slideOutVertically(animationSpec = tween(500), targetOffsetY = { it }) }
            ) { AddCardScreen() }
            composable(
                route = NotificationsSettingsDestination.route,
                enterTransition = { slideInHorizontally(animationSpec = tween(500), initialOffsetX = { -it }) },
                exitTransition = { slideOutHorizontally(animationSpec = tween(500), targetOffsetX = { -it }) }
            ) { NotificationsSettingsScreen() }
        }
    }
}