package ru.practicum.android.diploma.presentation.filters.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.api.interactor.FilterInteractor
import ru.practicum.android.diploma.domain.models.filters.FilterRegion
import ru.practicum.android.diploma.presentation.filters.models.RegionUiState
import ru.practicum.android.diploma.ui.navigation.util.NavResultKeys
import ru.practicum.android.diploma.util.common.Resource

class FilterRegionViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: FilterInteractor,
    private val selectedCountryId: Int?
) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()
    private val _regions = MutableStateFlow<List<FilterRegion>>(emptyList())
    private val _regionUiState = MutableStateFlow<RegionUiState>(RegionUiState.Loading)
    val regionUiState = _regionUiState.asStateFlow()
    private var debounceJob: Job? = null
    private val _shouldFinish = MutableStateFlow(false)
    val shouldFinish: StateFlow<Boolean> = _shouldFinish
    private val _hideKeyboardEvent = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val hideKeyboardEvent = _hideKeyboardEvent.asSharedFlow()

    init {
        loadRegions()
    }

    private fun loadRegions() {
        viewModelScope.launch {
            _regionUiState.value = RegionUiState.Loading
            interactor.getRegions(countryId = selectedCountryId)
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _regions.value = resource.data.toList()
                            _regionUiState.value = RegionUiState.Success(_regions.value)
                        }

                        is Resource.Error -> {
                            _regionUiState.value = RegionUiState.Error(resource.error)
                        }
                    }
                }
        }
    }

    fun onSearchTextChange(query: String) {
        _query.value = query

        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(DEBOUNCE_MILLIS)
            search(query)
            _hideKeyboardEvent.tryEmit(Unit)
        }
    }

    fun onQueryCleared() {
        _query.value = ""
        debounceJob?.cancel()
        _regionUiState.value = RegionUiState.Success(_regions.value)
    }

    fun search(query: String?) {
        val result = if (query.isNullOrEmpty()) {
            _regions.value
        } else {
            val normalizeQuery = query.normalize()
            _regions.value.filter {
                it.name.normalize().contains(normalizeQuery)
            }.sortedWith(compareBy<FilterRegion> {
                !it.name.normalize().startsWith(normalizeQuery)
            }.thenBy { it.name })
        }

        _regionUiState.value = RegionUiState.Success(result)
    }

    fun onReturnWithParam(id: Int, name: String, parentId: Int) {
        savedStateHandle[NavResultKeys.SELECTED_REGION] =
            FilterRegion(id = id, name = name, parentId = parentId)
        _shouldFinish.value = true
    }

    fun onReturnWithoutParam() {
        savedStateHandle[NavResultKeys.SELECTED_REGION] = null
        _shouldFinish.value = true
    }

    fun consumeFinishEvent() {
        _shouldFinish.value = false
    }

    private fun String.normalize(): String =
        lowercase()
            .replace('ё', 'е')
            .fold(StringBuilder()) { acc, c ->
                acc.append(c)
            }.toString()


    companion object {
        const val DEBOUNCE_MILLIS = 1000L
    }

}
