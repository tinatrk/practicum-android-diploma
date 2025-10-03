package ru.practicum.android.diploma.ui.navigation.util

import androidx.navigation.NavGraphBuilder
import ru.practicum.android.diploma.ui.navigation.bottom.favoriteDestination
import ru.practicum.android.diploma.ui.navigation.bottom.homeDestination
import ru.practicum.android.diploma.ui.navigation.bottom.teamDestination

fun NavGraphBuilder.appGraph() {
    homeDestination()
    favoriteDestination()
    teamDestination()
}
