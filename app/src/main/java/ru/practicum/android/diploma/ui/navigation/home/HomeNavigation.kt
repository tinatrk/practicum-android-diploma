package ru.practicum.android.diploma.ui.navigation.home

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.ui.screen.HomeScreen

fun NavGraphBuilder.homeDestination() {
    composable<Home> { HomeScreen() }
}

fun NavHostController.navigateToHome() {
    navigate(Home) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Serializable
object Home
