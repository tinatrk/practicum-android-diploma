package ru.practicum.android.diploma.ui.navigation.util

import androidx.navigation.NavGraphBuilder

fun NavGraphBuilder.appGraph(
    navigateToVacancy: (String) -> Unit,
    onBackClick: () -> Unit,
    navigateToFilterIndustry: () -> Unit,
    navigateToFilterSettings: () -> Unit,
    onFinishFilterIndustry: () -> Unit
) {
    homeDestination(
        navigateToVacancy = navigateToVacancy,
        navigateToFilterSettings = navigateToFilterSettings
    )
    favoriteDestination(navigateToVacancy = navigateToVacancy)
    teamDestination()
    vacancyDestination(onBackClick = onBackClick)
    filterSettingsDestination(navigateToFilterIndustry = navigateToFilterIndustry)
    filterIndustryDestination(onFinish = onFinishFilterIndustry)
}
