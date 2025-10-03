package ru.practicum.android.diploma.ui.navigation.bottom

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.ui.screen.FavoriteScreen
import ru.practicum.android.diploma.ui.screen.HomeScreen
import ru.practicum.android.diploma.ui.screen.TeamScreen

sealed interface BottomNavDestination {
    @Serializable
    object Home : BottomNavDestination

    @Serializable
    object Favorite : BottomNavDestination

    @Serializable
    object Team : BottomNavDestination
}

/** Навигация для экрана: «Главная» */
fun NavGraphBuilder.homeDestination() {
    composable<BottomNavDestination.Home> { HomeScreen(Modifier.fillMaxSize()) }
}

fun NavHostController.navigateToHome() {
    navigate(BottomNavDestination.Home) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

/** Навигация для экрана: «Избранное» */
fun NavGraphBuilder.favoriteDestination() {
    composable<BottomNavDestination.Favorite> { FavoriteScreen() }
}

fun NavHostController.navigateToFavorite() {
    navigate(BottomNavDestination.Favorite) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

/** Навигация для экрана: «Команда» */
fun NavGraphBuilder.teamDestination() {
    composable<BottomNavDestination.Team> { TeamScreen() }
}

fun NavHostController.navigateToTeam() {
    navigate(BottomNavDestination.Team) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}


