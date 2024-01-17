package com.pamela.flashcards.features.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pamela.flashcards.features.main.OverviewScreen
import com.pamela.flashcards.features.practice.PracticeScreen

@Composable
fun FlashCardsHostController(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "main") {
        composable(route = "main") {
            OverviewScreen(hiltViewModel(), {cardSetId ->
                navController.navigate("practice/$cardSetId")
            })
        }
        composable(route = "practice/{cardSetId}") {
            PracticeScreen(hiltViewModel())
        }
    }
}