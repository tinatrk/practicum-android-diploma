package ru.practicum.android.diploma.presentation.details.models

import androidx.compose.runtime.Immutable
import ru.practicum.android.diploma.presentation.models.VacancyInfo

@Immutable
sealed interface DetailsScreenState {

    object Loading : DetailsScreenState

    object Empty : DetailsScreenState

    data class Content(val data: VacancyInfo) : DetailsScreenState

    object Error : DetailsScreenState

    object InternetError : DetailsScreenState

}
