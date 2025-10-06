package ru.practicum.android.diploma.presentation.models

import androidx.compose.runtime.Immutable

@Immutable
sealed interface NavigationEventToVacancy {
    data object Default: NavigationEventToVacancy
    data class OpenVacancyDetails(val vacancyId: String) : NavigationEventToVacancy
}

