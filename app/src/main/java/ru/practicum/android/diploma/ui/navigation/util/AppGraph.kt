package ru.practicum.android.diploma.ui.navigation.util

import androidx.navigation.NavGraphBuilder

fun NavGraphBuilder.appGraph(
    onVacancyClick: (String) -> Unit
) {
    homeDestination(onVacancyClick = onVacancyClick)
    favoriteDestination(onVacancyClick = onVacancyClick)
    teamDestination()
    vacancyDestination()
}
