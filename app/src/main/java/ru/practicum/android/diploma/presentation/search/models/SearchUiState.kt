package ru.practicum.android.diploma.presentation.search.models

import androidx.compose.runtime.Immutable
import ru.practicum.android.diploma.presentation.models.VacancyBriefInfo
import ru.practicum.android.diploma.util.common.Failure

@Immutable
sealed class SearchUiState {
    data object Idle : SearchUiState()
    data object Loading : SearchUiState()
    data class Success(
        val data: List<VacancyBriefInfo>,
        val count: Int,
        val isLastPage: Boolean = false
    ) : SearchUiState()
    data class Error(val failure: Failure) : SearchUiState()
}
