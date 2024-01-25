package com.pamela.flashcards.navigation

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
        navAction?.let {
            when (it.route) {
                PreviousDestination.route -> navController.popBackStack()
                NavDrawerDestination.route -> openDrawer()
                else -> navController.navigate(it.route, it.navOptions)
            }
        }
    }


    DefaultNavigationDrawer(drawerState = drawerState) {
        NavHost(navController = navController, startDestination = viewModel.getStartDestination()) {
            composable(route = OverviewDestination.route) { OverviewScreen() }
            composable(route = PracticeDestination.routeWithArgs) { PracticeScreen() }
            composable(route = AddDeckDestination.routeWithArgs) { AddDeckScreen() }
            composable(route = AddCardDestination.routeWithArgs) { AddCardScreen() }
            composable(route = NotificationsSettingsDestination.route) { NotificationsSettingsScreen() }
        }
    }
}