package ru.practicum.android.diploma.ui.navigation.util

import androidx.navigation.NavGraphBuilder
import ru.practicum.android.diploma.domain.models.filters.FilterIndustry

fun NavGraphBuilder.appGraph(
    navigateToVacancy: (String) -> Unit,
    onBackClick: () -> Unit,
    navigateToIndustry: () -> Unit,
    navigateToSettings: () -> Unit,
    onFinishIndustry: () -> Unit
) {
    homeDestination(navigateToVacancy = navigateToVacancy, navigateToSettings = navigateToSettings)
    favoriteDestination(navigateToVacancy = navigateToVacancy)
    teamDestination()
    vacancyDestination(onBackClick = onBackClick)
    testSettingsDestination(navigateToIndustry = navigateToIndustry)
    testIndustryDestination(onFinish = onFinishIndustry)
}
