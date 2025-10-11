package ru.practicum.android.diploma.presentation.favorites.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.favorites.api.interactor.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.vacancy.VacancyBrief
import ru.practicum.android.diploma.presentation.converter.VacancyConverter
import ru.practicum.android.diploma.presentation.favorites.models.FavoritesScreenState
import ru.practicum.android.diploma.presentation.models.NavigationEventToVacancy
import ru.practicum.android.diploma.util.afterDebounce
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource

class FavoritesViewModel(
    private val favoritesInteractor: FavoritesInteractor,
    private val vacancyConverter: VacancyConverter
) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<NavigationEventToVacancy>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    val screenStateFlow: StateFlow<FavoritesScreenState> = favoritesInteractor.getAllFavoriteVacancies()
        .map { result ->
            processResult(result)
        }
        .onStart {
            emit(FavoritesScreenState.Loading)
        }
        .catch { e ->
            Log.e(FAVORITES_VIEW_MODEL_TAG, e.message ?: "ошибка при получении списка избранных вакансий из БД")
            emit(FavoritesScreenState.Error)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = FavoritesScreenState.Loading
        )

    private fun processResult(result: Resource<List<VacancyBrief>, Failure>): FavoritesScreenState {
        return when (result) {
            is Resource.Success -> {
                FavoritesScreenState.Content(data = result.data.map { vacancyConverter.map(it) })
            }

            is Resource.Error -> {
                when (result.error) {
                    is Failure.NotFound -> FavoritesScreenState.Empty
                    else -> FavoritesScreenState.Error
                }
            }
        }
    }

    private val onVacancyClickDebounce: (String) -> Unit =
        afterDebounce(ON_VACANCY_CLICK_DELAY_MILLIS, viewModelScope, false) { vacancyId ->
            viewModelScope.launch {
                _navigationEvent.emit(NavigationEventToVacancy.OpenVacancyDetails(vacancyId = vacancyId))
            }
        }

    fun onVacancyClick(vacancyId: String) {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEventToVacancy.Idle)
        }
        onVacancyClickDebounce(vacancyId)
    }

    companion object {
        private const val FAVORITES_VIEW_MODEL_TAG = "FavoritesViewModel"
        private const val STOP_TIMEOUT_MILLIS = 5000L
        private const val ON_VACANCY_CLICK_DELAY_MILLIS = 1000L
    }
}
