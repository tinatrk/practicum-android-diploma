package ru.practicum.android.diploma.ui.navigation.util

import androidx.navigation.NavGraphBuilder

fun NavGraphBuilder.appGraph(
    navigateToVacancy: (String) -> Unit
) {
    homeDestination(navigateToVacancy = navigateToVacancy)
    favoriteDestination(navigateToVacancy = navigateToVacancy)
    teamDestination()
    vacancyDestination()
}
