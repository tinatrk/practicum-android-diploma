package ru.practicum.android.diploma.ui.navigation.favorites

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.ui.screen.FavoriteScreen

@Serializable
object Favorite

fun NavGraphBuilder.favoriteDestination() {
    composable<Favorite> { FavoriteScreen() }
}

fun NavHostController.navigateToFavorite() {
    navigate(Favorite) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
