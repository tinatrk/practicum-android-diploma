package ru.practicum.android.diploma.presentation.search

import ru.practicum.android.diploma.domain.models.VacancyBrief
import ru.practicum.android.diploma.domain.models.VacancyPage
import ru.practicum.android.diploma.util.common.Failure

sealed class SearchUiState {
    data object Idle : SearchUiState()
    data object Loading : SearchUiState()
    data class Success(val data: List<VacancyBrief>, val count: Int) : SearchUiState()
    data class Error(val error: Failure) : SearchUiState()
}
