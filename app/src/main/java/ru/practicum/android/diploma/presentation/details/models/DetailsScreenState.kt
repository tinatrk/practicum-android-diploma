package ru.practicum.android.diploma.presentation.details.models

import ru.practicum.android.diploma.presentation.models.VacancyInfo

sealed interface DetailsScreenState {

    object Loading : DetailsScreenState

    object Empty : DetailsScreenState

    data class Content(val data: VacancyInfo) : DetailsScreenState

    object Error : DetailsScreenState

    object InternetError : DetailsScreenState

}
