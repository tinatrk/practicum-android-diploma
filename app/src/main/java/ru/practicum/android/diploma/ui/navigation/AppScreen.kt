package ru.practicum.android.diploma.ui.navigation

import ru.practicum.android.diploma.R

sealed class AppScreen(val route: String, val iconRes: Int, val labelRes: Int) {
    object Home : AppScreen(
        route = "Home",
        iconRes = R.drawable.main_24px,
        labelRes = R.string.label_home
    )
    object Favorite : AppScreen(
        route = "Fav",
        iconRes = R.drawable.favorites_24px,
        labelRes = R.string.label_fav
    )
    object Team : AppScreen(
        route = "Team",
        iconRes = R.drawable.team_24px,
        labelRes = R.string.label_team
    )
}

val bottomTabs: List<AppScreen> =
    listOf(
        AppScreen.Home,
        AppScreen.Favorite,
        AppScreen.Team
    )
