package ru.practicum.android.diploma.ui.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.practicum.android.diploma.ui.component.BottomNavigationBar
import ru.practicum.android.diploma.ui.navigation.bottom.BottomNavDestination
import ru.practicum.android.diploma.ui.navigation.bottom.navigateToFavorite
import ru.practicum.android.diploma.ui.navigation.bottom.navigateToHome
import ru.practicum.android.diploma.ui.navigation.bottom.navigateToTeam
import ru.practicum.android.diploma.ui.navigation.bottomTabs
import ru.practicum.android.diploma.ui.navigation.util.appGraph

@Composable
fun AppRoot(
    modifier: Modifier = Modifier
) {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                tabs = bottomTabs,
                currentDestination = currentDestination,
                onItemSelected = { tab ->
                    when (tab) {
                        BottomNavDestination.Home::class -> navController.navigateToHome()
                        BottomNavDestination.Favorite::class -> navController.navigateToFavorite()
                        BottomNavDestination.Team::class -> navController.navigateToTeam()
                    }
                },
                modifier = modifier
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavDestination.Home,
            modifier = modifier.padding(innerPadding)
        ) {
            appGraph()
        }
    }
}
