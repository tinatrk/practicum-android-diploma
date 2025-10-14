package ru.practicum.android.diploma.ui.navigation.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.ui.screen.FavoriteScreen
import ru.practicum.android.diploma.ui.screen.FilterCountryScreen
import ru.practicum.android.diploma.ui.screen.FilterIndustryScreen
import ru.practicum.android.diploma.ui.screen.FilterRegionScreen
import ru.practicum.android.diploma.ui.screen.FilterSettingsScreen
import ru.practicum.android.diploma.ui.screen.HomeScreen
import ru.practicum.android.diploma.ui.screen.TeamScreen
import ru.practicum.android.diploma.ui.screen.VacancyScreen
import ru.practicum.android.diploma.ui.screen.WorkLocationScreen

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
    data object FilterSettings : AppNavDestination

    @Serializable
    data class FilterIndustry(val selectedIndustryId: Int?) : AppNavDestination

    @Serializable
    data object WorkLocation : AppNavDestination

    @Serializable
    data object FilterCountry : AppNavDestination

    @Serializable
    data class FilterRegion(val selectedCountryId: Int?) : AppNavDestination
}

/** Навигация для экрана: «Главная» */
fun NavGraphBuilder.homeDestination(
    navigateToVacancy: (String) -> Unit,
    navigateToFilterSettings: () -> Unit
) {
    composable<AppNavDestination.Home> {
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            navigateToVacancy = navigateToVacancy,
            navigateToFilterSettings = navigateToFilterSettings
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

/** Навигация для экрана: «Настройки фильтрации» */
fun NavGraphBuilder.filterSettingsDestination(
    navigateBack: () -> Unit,
    navigateToWorkLocation: () -> Unit,
    navigateToFilterIndustry: (Int?) -> Unit
) {
    composable<AppNavDestination.FilterSettings> {
        FilterSettingsScreen(
            navigateBack = navigateBack,
            navigateToFilterIndustry = navigateToFilterIndustry,
            navigateToWorkLocation = navigateToWorkLocation,
            navigateToSearch = navigateBack
        )
    }
}

fun NavHostController.navigateToFilterSettings() {
    navigate(AppNavDestination.FilterSettings)
}

/** Навигация для экрана: «Отрасль» */
fun NavGraphBuilder.filterIndustryDestination(
    navigateBack: () -> Unit
) {
    composable<AppNavDestination.FilterIndustry> { navBackStackEntry ->
        val selectedIndustryId = navBackStackEntry.toRoute<AppNavDestination.FilterIndustry>().selectedIndustryId
        FilterIndustryScreen(
            navigateBack = navigateBack,
            selectedIndustryId = selectedIndustryId
        )
    }
}

fun NavHostController.navigateToFilterIndustry(selectedIndustryId: Int?) {
    navigate(AppNavDestination.FilterIndustry(selectedIndustryId))
}

/** Навигация для экрана: «Место работы» */
fun NavGraphBuilder.workLocationDestination(
    navigateBack: () -> Unit,
    navigateToFilterCountry: () -> Unit,
    navigateToFilterRegion: (Int?) -> Unit,
) {
    composable<AppNavDestination.WorkLocation> {
        WorkLocationScreen(
            navigateBack = navigateBack,
            navigateToFilterCountry = navigateToFilterCountry,
            navigateToFilterRegion = navigateToFilterRegion
        )
    }
}

fun NavHostController.navigateToWorkLocation() {
    navigate(AppNavDestination.WorkLocation)
}

/** Навигация для экрана: «Страна» */
fun NavGraphBuilder.filterCountryDestination(
    navigateBack: () -> Unit
) {
    composable<AppNavDestination.FilterCountry> {
        FilterCountryScreen(
            navigateBack = navigateBack,
        )
    }
}

fun NavHostController.navigateToFilterCountry() {
    navigate(AppNavDestination.FilterCountry)
}

/** Навигация для экрана: «Регион» */
fun NavGraphBuilder.filterRegionDestination(
    navigateBack: () -> Unit,
) {
    composable<AppNavDestination.FilterRegion> { navBackStackEntry ->
        val selectedCountryId = navBackStackEntry.toRoute<AppNavDestination.FilterRegion>().selectedCountryId
        FilterRegionScreen(
            navigateBack = navigateBack,
            selectedCountryId = selectedCountryId
        )
    }
}

fun NavHostController.navigateToFilterRegion(selectedCountryId: Int?) {
    navigate(AppNavDestination.FilterRegion(selectedCountryId))
}
