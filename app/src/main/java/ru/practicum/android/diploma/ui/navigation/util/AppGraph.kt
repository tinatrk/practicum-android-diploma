package ru.practicum.android.diploma.ui.navigation.util

import androidx.navigation.NavGraphBuilder

fun NavGraphBuilder.appGraph(
    navigateToVacancy: (String) -> Unit,
    navigateToFilterIndustry: (Int?) -> Unit,
    navigateToFilterSettings: () -> Unit,
    navigateToWorkLocation: () -> Unit,
    navigateToFilterCountry: () -> Unit,
    navigateToFilterRegion: (Int?) -> Unit,
    onBackClick: () -> Unit,
    navigateBackFromFilterIndustry: () -> Unit,
    navigateBackFromWorkLocation: () -> Unit,
    navigateBackFromFilterCountry: () -> Unit,
    navigateBackFromFilterRegion: () -> Unit,
) {
    homeDestination(
        navigateToVacancy = navigateToVacancy,
        navigateToFilterSettings = navigateToFilterSettings
    )
    favoriteDestination(navigateToVacancy = navigateToVacancy)
    teamDestination()
    vacancyDestination(onBackClick = onBackClick)
    filterSettingsDestination(
        navigateBack = onBackClick,
        navigateToWorkLocation = navigateToWorkLocation,
        navigateToFilterIndustry = navigateToFilterIndustry
    )
    workLocationDestination(
        navigateToFilterCountry = navigateToFilterCountry,
        navigateToFilterRegion = navigateToFilterRegion,
        navigateBack = navigateBackFromWorkLocation
    )
    filterCountryDestination(navigateBack = navigateBackFromFilterCountry)
    filterRegionDestination(navigateBack = navigateBackFromFilterRegion)
    filterIndustryDestination(navigateBack = navigateBackFromFilterIndustry)
}
