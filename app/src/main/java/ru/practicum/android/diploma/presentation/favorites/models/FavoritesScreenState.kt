package ru.practicum.android.diploma.presentation.favorites.models

import ru.practicum.android.diploma.presentation.models.VacancyBriefInfo

sealed interface FavoritesScreenState {
    data class Content(val data: List<VacancyBriefInfo>) : FavoritesScreenState
    data object Loading : FavoritesScreenState
    data object Empty : FavoritesScreenState
    data object Error : FavoritesScreenState
}
