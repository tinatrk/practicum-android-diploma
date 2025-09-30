package ru.practicum.android.diploma.ui.navigation.team

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.ui.screen.TeamScreen

@Serializable
object Team

fun NavGraphBuilder.teamDestination() {
    composable<Team> { TeamScreen() }
}

fun NavHostController.navigateToTeam() {
    navigate(Team) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
