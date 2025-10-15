package ru.practicum.android.diploma.ui.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.collections.immutable.toImmutableList
import ru.practicum.android.diploma.domain.models.filters.FilterAddress
import ru.practicum.android.diploma.domain.models.filters.FilterCountry
import ru.practicum.android.diploma.domain.models.filters.FilterIndustry
import ru.practicum.android.diploma.domain.models.filters.FilterRegion
import ru.practicum.android.diploma.ui.components.BottomNavigationBar
import ru.practicum.android.diploma.ui.navigation.bottomTabs
import ru.practicum.android.diploma.ui.navigation.util.AppNavDestination
import ru.practicum.android.diploma.ui.navigation.util.NavResultKeys
import ru.practicum.android.diploma.ui.navigation.util.appGraph
import ru.practicum.android.diploma.ui.navigation.util.navigateToFavorite
import ru.practicum.android.diploma.ui.navigation.util.navigateToFilterCountry
import ru.practicum.android.diploma.ui.navigation.util.navigateToFilterIndustry
import ru.practicum.android.diploma.ui.navigation.util.navigateToFilterRegion
import ru.practicum.android.diploma.ui.navigation.util.navigateToFilterSettings
import ru.practicum.android.diploma.ui.navigation.util.navigateToHome
import ru.practicum.android.diploma.ui.navigation.util.navigateToTeam
import ru.practicum.android.diploma.ui.navigation.util.navigateToVacancy
import ru.practicum.android.diploma.ui.navigation.util.navigateToWorkLocation

@Composable
fun AppRoot(
    modifier: Modifier = Modifier
) {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomIsVisible = currentDestination
        ?.hierarchy
        ?.any {
            it.route == AppNavDestination.Home::class.qualifiedName ||
                it.route == AppNavDestination.Favorite::class.qualifiedName ||
                it.route == AppNavDestination.Team::class.qualifiedName
        } == true

    Scaffold(
        bottomBar = {
            if (bottomIsVisible) {
                BottomNavigationBar(
                    tabs = bottomTabs.toImmutableList(),
                    currentDestination = currentDestination,
                    onItemSelected = { tab ->
                        when (tab) {
                            AppNavDestination.Home::class -> navController.navigateToHome()
                            AppNavDestination.Favorite::class -> navController.navigateToFavorite()
                            AppNavDestination.Team::class -> navController.navigateToTeam()
                        }
                    },
                    modifier = modifier
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppNavDestination.Home,
            modifier = modifier.padding(innerPadding)
        ) {
            appGraph(
                navigateToVacancy = { vacancyId ->
                    navController.navigateToVacancy(vacancyId)
                },
                navigateToFilterSettings = {
                    navController.navigateToFilterSettings()
                },
                navigateToWorkLocation = {
                    navController.navigateToWorkLocation()
                },
                navigateToFilterCountry = {
                    navController.navigateToFilterCountry()
                },
                navigateToFilterRegion = { selectedCountryId ->
                    navController.navigateToFilterRegion(selectedCountryId)
                },
                navigateToFilterIndustry = { selectedIndustryId ->
                    navController.navigateToFilterIndustry(selectedIndustryId)
                },

                onBackClick = {
                    navController.popBackStack()
                },

                navigateBackFromWorkLocation = {
                    val result = navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.get<FilterAddress>(NavResultKeys.SELECTED_WORK_ADDRESS)

                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(NavResultKeys.SELECTED_WORK_ADDRESS, result)

                    navController.popBackStack()
                },

                navigateBackFromFilterCountry = {
                    val result = navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.get<FilterCountry>(NavResultKeys.SELECTED_COUNTRY)

                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(NavResultKeys.SELECTED_COUNTRY, result)

                    navController.popBackStack()
                },

                navigateBackFromFilterRegion = {
                    val result = navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.get<FilterRegion>(NavResultKeys.SELECTED_REGION)

                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(NavResultKeys.SELECTED_REGION, result)

                    navController.popBackStack()
                },

                navigateBackFromFilterIndustry = {
                    val result = navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.get<FilterIndustry>(NavResultKeys.SELECTED_INDUSTRY)

                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(NavResultKeys.SELECTED_INDUSTRY, result)

                    navController.popBackStack()
                },
            )
        }
    }
}
