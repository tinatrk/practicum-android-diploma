package ru.practicum.android.diploma.ui.navigation.util

import androidx.navigation.NavGraphBuilder

fun NavGraphBuilder.appGraph(
    actions: AppGraphActions
) {
    homeDestination(
        navigateToVacancy = actions.navigateToVacancy,
        navigateToFilterSettings = actions.navigateToFilterSettings
    )
    favoriteDestination(navigateToVacancy = actions.navigateToVacancy)
    teamDestination()
    vacancyDestination(onBackClick = actions.onBackClick)
    filterSettingsDestination(
        navigateBack = actions.onBackClick,
        navigateToWorkLocation = actions.navigateToWorkLocation,
        navigateToFilterIndustry = actions.navigateToFilterIndustry
    )
    workLocationDestination(
        navigateToFilterCountry = actions.navigateToFilterCountry,
        navigateToFilterRegion = actions.navigateToFilterRegion,
        navigateBack = actions.navigateBackFromWorkLocation
    )
    filterCountryDestination(navigateBack = actions.navigateBackFromFilterCountry)
    filterRegionDestination(navigateBack = actions.navigateBackFromFilterRegion)
    filterIndustryDestination(navigateBack = actions.navigateBackFromFilterIndustry)
}
