package ru.practicum.android.diploma.presentation.worklocation.models

sealed interface WorkLocationNavEvent {
    object NavigateBack : WorkLocationNavEvent
    object NavigateToCountryScreen : WorkLocationNavEvent
    data class NavigateToRegionScreen(val id: Int?) : WorkLocationNavEvent
}
