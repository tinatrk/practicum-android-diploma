package ru.practicum.android.diploma.ui.navigation.util

import androidx.navigation.NavGraphBuilder
import ru.practicum.android.diploma.ui.navigation.favorites.favoriteDestination
import ru.practicum.android.diploma.ui.navigation.home.homeDestination
import ru.practicum.android.diploma.ui.navigation.team.teamDestination

fun NavGraphBuilder.appGraph() {
    homeDestination()
    favoriteDestination()
    teamDestination()
}
