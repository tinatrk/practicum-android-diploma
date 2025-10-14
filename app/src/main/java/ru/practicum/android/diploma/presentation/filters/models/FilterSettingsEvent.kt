package ru.practicum.android.diploma.presentation.filters.models

interface FilterSettingsEvent {

    data object NavigateBackNoRefresh : FilterSettingsEvent

    data object NavigateToSearchAndRefresh : FilterSettingsEvent
}
