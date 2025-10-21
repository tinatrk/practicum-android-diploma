package ru.practicum.android.diploma.ui.navigation.util

data class AppGraphActions(
    val navigateToVacancy: (String, DetailsSource) -> Unit,
    val navigateToFilterIndustry: (Int?) -> Unit,
    val navigateToFilterSettings: () -> Unit,
    val navigateToWorkLocation: () -> Unit,
    val navigateToFilterCountry: () -> Unit,
    val navigateToFilterRegion: (Int?) -> Unit,
    val onBackClick: () -> Unit,
    val navigateBackFromFilterIndustry: () -> Unit,
    val navigateBackFromWorkLocation: () -> Unit,
    val navigateBackFromFilterCountry: () -> Unit,
    val navigateBackFromFilterRegion: () -> Unit,
)
