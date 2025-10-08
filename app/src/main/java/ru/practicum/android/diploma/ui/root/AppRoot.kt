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
import ru.practicum.android.diploma.ui.component.BottomNavigationBar
import ru.practicum.android.diploma.ui.navigation.bottomTabs
import ru.practicum.android.diploma.ui.navigation.util.AppNavDestination
import ru.practicum.android.diploma.ui.navigation.util.appGraph
import ru.practicum.android.diploma.ui.navigation.util.navigateToFavorite
import ru.practicum.android.diploma.ui.navigation.util.navigateToHome
import ru.practicum.android.diploma.ui.navigation.util.navigateToTeam
import ru.practicum.android.diploma.ui.navigation.util.navigateToVacancy

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
                    tabs = bottomTabs,
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
                onVacancyClick = { vacancyId ->
                    navController.navigateToVacancy(vacancyId)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
