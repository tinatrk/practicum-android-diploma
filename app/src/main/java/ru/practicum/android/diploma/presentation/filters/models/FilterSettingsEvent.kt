package ru.practicum.android.diploma.presentation.filters.models

import androidx.compose.runtime.Immutable

@Immutable
interface FilterSettingsEvent {
    data object NavigateBack : FilterSettingsEvent

    data object NavigateToWorkLocation : FilterSettingsEvent
}
