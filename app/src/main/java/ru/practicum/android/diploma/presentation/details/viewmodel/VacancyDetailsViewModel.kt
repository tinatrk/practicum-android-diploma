package ru.practicum.android.diploma.presentation.details.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.details.api.interactor.ExternalNavigatorInteractor
import ru.practicum.android.diploma.domain.details.api.interactor.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.favorites.api.interactor.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy
import ru.practicum.android.diploma.presentation.converter.VacancyConverter
import ru.practicum.android.diploma.presentation.details.models.DetailsScreenState
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource
import ru.practicum.android.diploma.util.debounce

class VacancyDetailsViewModel(
    private val vacancyId: String,
    private val detailsInteractor: VacancyDetailsInteractor,
    private val favoritesInteractor: FavoritesInteractor,
    private val externalNavigatorInteractor: ExternalNavigatorInteractor,
    private val vacancyConverter: VacancyConverter
) : ViewModel() {

    private var vacancy: Vacancy? = null

    private val detailsState: Flow<DetailsScreenState> =
        detailsInteractor.getVacancyDetails(vacancyId)
            .map { result ->
                processResult(result)
            }.catch {
                emit(DetailsScreenState.Error)
            }

    private val isFavorite: Flow<Boolean> =
        favoritesInteractor.isVacancyFavorite(vacancyId)
            .catch { emit(false) }

    val screenState: StateFlow<DetailsScreenState> =
        combine(
            detailsState.onStart { emit(DetailsScreenState.Loading) },
            isFavorite.onStart { emit(false) }
        ) { state, isFavorite ->
            when (state) {
                is DetailsScreenState.Content -> {
                    state.copy(data = state.data.copy(isFavorite = isFavorite))
                }

                else -> state
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = DetailsScreenState.Loading
        )

    private fun processResult(result: Resource<Vacancy, Failure>): DetailsScreenState {
        return when (result) {
            is Resource.Success -> {
                vacancy = result.data
                DetailsScreenState.Content(data = vacancyConverter.map(vacancy!!))
            }

            is Resource.Error -> {
                when (result.error) {
                    is Failure.BadRequest -> DetailsScreenState.Empty
                    else -> DetailsScreenState.Error
                }
            }
        }
    }

    private val onFavoriteClickDelay: (Unit) -> Unit =
        debounce(ON_FAVORITE_CLICK_DELAY_MILLIS, viewModelScope, false) {
            viewModelScope.launch {
                if (isFavorite.first()) {
                    vacancy?.let {
                        favoritesInteractor.deleteFavoriteVacancy(it)
                    }
                } else {
                    vacancy?.let {
                        favoritesInteractor.saveFavoriteVacancy(it)
                    }
                }
            }
        }

    fun onFavoriteClick() {
        onFavoriteClickDelay(Unit)
    }

    fun onShareClick() {
        if (vacancy?.url != null) {
            externalNavigatorInteractor.shareVacancy(vacancy?.url!!)
        } else {
            Log.e(VACANCY_DETAIL_VIEW_MODEL_TAG, "url is null")
        }
    }

    fun onPhoneClick(phone: String) {
        externalNavigatorInteractor.callToThePhone(phone)
    }

    fun onEmailClick() {
        if (vacancy?.contacts?.email != null) {
            externalNavigatorInteractor.sendEmail(vacancy?.contacts!!.email!!)
        } else {
            Log.e(VACANCY_DETAIL_VIEW_MODEL_TAG, "email is null")
        }
    }

    companion object {
        private const val VACANCY_DETAIL_VIEW_MODEL_TAG = "VacancyDetailViewModel"
        private const val STOP_TIMEOUT_MILLIS = 5000L
        private const val ON_FAVORITE_CLICK_DELAY_MILLIS = 1000L
    }
}
