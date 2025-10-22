package ru.practicum.android.diploma.presentation.models

import androidx.compose.runtime.Immutable
import ru.practicum.android.diploma.ui.navigation.util.DetailsSource

@Immutable
sealed interface NavigationEventToVacancy {
    data object Idle : NavigationEventToVacancy
    data class OpenVacancyDetails(val vacancyId: String, val source: DetailsSource?) : NavigationEventToVacancy
}
