package ru.practicum.android.diploma.presentation.favorites.models

import androidx.compose.runtime.Immutable

@Immutable
sealed interface NavigationEvent {
    data object Default: NavigationEvent
    data class OpenVacancyDetails(val vacancyId: String) : NavigationEvent
}

