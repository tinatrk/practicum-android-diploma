package ru.practicum.android.diploma.ui.navigation.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.domain.models.filters.FilterIndustry
import ru.practicum.android.diploma.ui.screen.FavoriteScreen
import ru.practicum.android.diploma.ui.screen.HomeScreen
import ru.practicum.android.diploma.ui.screen.TeamScreen
import ru.practicum.android.diploma.ui.screen.TestIndustryScreen
import ru.practicum.android.diploma.ui.screen.TestSettingsScreen
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

    @Serializable
    object TestSettings : AppNavDestination

    @Serializable
    object TestIndustry : AppNavDestination
}

/** Навигация для экрана: «Главная» */
fun NavGraphBuilder.homeDestination(
    navigateToVacancy: (String) -> Unit,
    navigateToSettings: () -> Unit
) {
    composable<AppNavDestination.Home> {
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            navigateToVacancy = navigateToVacancy,
            navigateToSettings = navigateToSettings
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
    navigateToVacancy: (String) -> Unit
) {
    composable<AppNavDestination.Favorite> {
        FavoriteScreen(navigateToVacancy = navigateToVacancy)
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
fun NavGraphBuilder.vacancyDestination(
    onBackClick: () -> Unit
) {
    composable<AppNavDestination.Vacancy> { navBackStackEntry ->
        val vacancyId = navBackStackEntry.toRoute<AppNavDestination.Vacancy>().vacancyId
        VacancyScreen(vacancyId = vacancyId, onBackClick)
    }
}

fun NavHostController.navigateToVacancy(vacancyId: String) {
    navigate(AppNavDestination.Vacancy(vacancyId))
}

/** Навигация для тестового экрана: «Настройки фильтрации» */
fun NavGraphBuilder.testSettingsDestination(
    navigateToIndustry: () -> Unit
) {
    composable<AppNavDestination.TestSettings> {
        TestSettingsScreen(
            navigateToIndustry = navigateToIndustry
        )
    }
}

fun NavHostController.navigateToTestSettings() {
    navigate(AppNavDestination.TestSettings)
}

/** Навигация для тестового экрана: «Отрасль» */
fun NavGraphBuilder.testIndustryDestination(
    onFinish: () -> Unit
) {
    composable<AppNavDestination.TestIndustry> {
        TestIndustryScreen(
            onFinish = onFinish
        )
    }
}

fun NavHostController.navigateToTestIndustry() {
    navigate(AppNavDestination.TestIndustry)
}
