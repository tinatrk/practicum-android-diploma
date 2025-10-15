package ru.practicum.android.diploma.presentation.filters.models

sealed interface WorkLocationNavEvent {
    object NavigateBack : WorkLocationNavEvent
    object NavigateToCountryScreen : WorkLocationNavEvent
    data class NavigateToRegionScreen(val id: Int?) : WorkLocationNavEvent
}
