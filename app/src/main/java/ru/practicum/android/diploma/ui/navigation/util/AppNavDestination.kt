package ru.practicum.android.diploma.ui.navigation.util

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
import ru.practicum.android.diploma.ui.screen.VacancyScreen

sealed interface AppNavDestination {
    @Serializable
    object Home : AppNavDestination

    @Serializable
    object Favorite : AppNavDestination

    @Serializable
    object Team : AppNavDestination

    @Serializable
    data class Vacancy(val vacancyId: String) : AppNavDestination
}

/** Навигация для экрана: «Главная» */
fun NavGraphBuilder.homeDestination(
    onVacancyClick: (String) -> Unit
) {
    composable<AppNavDestination.Home> {
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            onVacancyClick = onVacancyClick
        )
    }
}

fun NavHostController.navigateToHome() {
    navigate(AppNavDestination.Home) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

/** Навигация для экрана: «Избранное» */
fun NavGraphBuilder.favoriteDestination(
    onVacancyClick: (String) -> Unit
) {
    composable<AppNavDestination.Favorite> {
        FavoriteScreen(onVacancyClick = onVacancyClick)
    }
}

fun NavHostController.navigateToFavorite() {
    navigate(AppNavDestination.Favorite) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

/** Навигация для экрана: «Команда» */
fun NavGraphBuilder.teamDestination() {
    composable<AppNavDestination.Team> { TeamScreen() }
}

fun NavHostController.navigateToTeam() {
    navigate(AppNavDestination.Team) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

/** Навигация для экрана: «Вакансия» */
fun NavGraphBuilder.vacancyDestination() {
    composable<AppNavDestination.Vacancy> { navBackStackEntry ->
        val vacancyId = navBackStackEntry.arguments?.getString("vacancyId") ?: ""
        VacancyScreen(vacancyId = vacancyId)
    }
}

fun NavHostController.navigateToVacancy(vacancyId: String) {
    navigate(AppNavDestination.Vacancy(vacancyId))
}
