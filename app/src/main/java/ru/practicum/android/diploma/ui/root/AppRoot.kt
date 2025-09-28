package ru.practicum.android.diploma.ui.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.practicum.android.diploma.ui.component.BottomNavigationBar
import ru.practicum.android.diploma.ui.navigation.AppScreen
import ru.practicum.android.diploma.ui.navigation.bottomTabs
import ru.practicum.android.diploma.ui.screen.FavoriteScreen
import ru.practicum.android.diploma.ui.screen.HomeScreen
import ru.practicum.android.diploma.ui.screen.TeamScreen

@Composable
fun AppRoot(
    modifier: Modifier = Modifier
) {
    val navController: NavHostController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute: String? = currentBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                tabs = bottomTabs,
                currentRoute = currentRoute,
                onItemSelected = { route ->
                    if (route != currentRoute) {
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                modifier = modifier
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreen.Home.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(AppScreen.Home.route) { HomeScreen() }
            composable(AppScreen.Favorite.route) { FavoriteScreen() }
            composable(AppScreen.Team.route) { TeamScreen() }
        }
    }
}
