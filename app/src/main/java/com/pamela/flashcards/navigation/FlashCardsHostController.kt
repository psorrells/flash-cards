package com.pamela.flashcards.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pamela.flashcards.features.addcard.AddCardScreen
import com.pamela.flashcards.features.addset.AddSetScreen
import com.pamela.flashcards.features.overview.OverviewScreen
import com.pamela.flashcards.features.practice.PracticeScreen

@Composable
fun FlashCardsHostController(
    navController: NavHostController = rememberNavController(),
    viewModel: HostControllerViewModel = hiltViewModel()
) {

    val navAction by viewModel.navigator.navAction.collectAsStateWithLifecycle()

    LaunchedEffect(navAction) {
        navAction?.let {
            if (it.route == PreviousDestination.route) {
                navController.popBackStack()
            } else {
                navController.navigate(it.route, it.navOptions)
            }
        }
    }

    NavHost(navController = navController, startDestination = viewModel.getStartDestination()) {
        composable(route = OverviewDestination.route) {
            OverviewScreen()
        }
        composable(route = PracticeDestination.routeWithArgs) {
            PracticeScreen()
        }
        composable(route = AddSetDestination.routeWithArgs) {
            AddSetScreen()
        }
        composable(route = AddCardDestination.routeWithArgs) {
            AddCardScreen()
        }
    }
}