package ru.practicum.android.diploma.presentation.filters.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.api.interactor.FilterInteractor
import ru.practicum.android.diploma.domain.models.filters.FilterIndustry
import ru.practicum.android.diploma.presentation.filters.models.FilterIndustryUiState
import ru.practicum.android.diploma.presentation.filters.models.FilterIndustryUi
import ru.practicum.android.diploma.presentation.mappers.toFilterIndustry
import ru.practicum.android.diploma.presentation.mappers.totoFilterIndustryUiList
import ru.practicum.android.diploma.ui.navigation.util.NavResultKeys
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource

class FilterIndustryViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val selectedIndustryId: Int?,
    private val filterInteractor: FilterInteractor
) : ViewModel() {

    private val _shouldFinish = MutableStateFlow(false)
    val shouldFinish: StateFlow<Boolean> = _shouldFinish

    private val _screenState =
        MutableStateFlow<FilterIndustryUiState>(FilterIndustryUiState.Loading)
    val screenState = _screenState.asStateFlow()
    private val _industries = MutableStateFlow<List<FilterIndustry>>(emptyList())
    private var displayedIndustries: List<FilterIndustry> = listOf()

    private var curChoice: FilterIndustryUi? = null

    private var searchJob: Job? = null

    init {
        curChoice = selectedIndustryId?.let { FilterIndustryUi(id = it, name = "") }
        loadIndustries()
    }

    private fun loadIndustries() {
        viewModelScope.launch {
            filterInteractor.getIndustries().collect { result ->
                val state: FilterIndustryUiState = when (result) {
                    is Resource.Success -> {
                        _industries.value = result.data.toList()
                        FilterIndustryUiState.Content(
                            data = result.data.totoFilterIndustryUiList(),
                            curChoice = curChoice?.id,
                        )
                    }

                    is Resource.Error -> {
                        FilterIndustryUiState.Error(
                            error = result.error,
                        )
                    }
                }
                renderState(state)
            }
        }
    }

    private fun renderState(state: FilterIndustryUiState) {
        _screenState.update {
            state
        }
    }

    fun onSearchTextChanged(searchQuery: String?) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY_MILLIS)
            search(searchQuery)
        }
    }

    fun search(query: String?) {
        displayedIndustries = if (query.isNullOrEmpty()) {
            _industries.value
        } else {
            val normalizeQuery = query.normalize()
            _industries.value.filter {
                it.name.normalize().contains(normalizeQuery)
            }.sortedWith(compareBy<FilterIndustry> {
                !it.name.normalize().startsWith(normalizeQuery)
            }.thenBy { it.name })
        }

        updateDisplayList(displayedIndustries)
    }

    private fun updateDisplayList(newList: List<FilterIndustry>) {
        if (newList.isNotEmpty()) {
            renderState(
                FilterIndustryUiState.Content(
                    data = newList.totoFilterIndustryUiList(),
                    curChoice = curChoice?.id,
                )
            )
        } else {
            renderState(
                FilterIndustryUiState.Error(error = Failure.NotFound)
            )
        }
    }

    fun onClearSearchQuery() {
        searchJob?.cancel()
        renderState(
            FilterIndustryUiState.Content(
                data = _industries.value.totoFilterIndustryUiList(),
                curChoice = curChoice?.id
            )
        )
    }

    fun onIndustryItemClick(filterIndustry: FilterIndustryUi?) {
        curChoice = if (curChoice?.id != filterIndustry?.id) {
            filterIndustry
        } else {
            null
        }

        renderState(
            FilterIndustryUiState.Content(
                data = displayedIndustries
                    .ifEmpty {
                        _industries.value
                    }
                    .totoFilterIndustryUiList(),
                curChoice = curChoice?.id,
            )
        )
    }

    fun onSaveChoiceClick() {
        curChoice?.let {
            if (it.name != "") onReturnWithParam(it.toFilterIndustry()) else onReturnWithoutParam()
        } ?: onReturnWithoutParam()
    }

    fun onBackNavigate() {
        onReturnWithoutParam()
    }

    private fun String.normalize(): String =
        lowercase()
            .replace('ё', 'е')
            .fold(StringBuilder()) { acc, c ->
                acc.append(c)
            }.toString()

    private fun onReturnWithParam(param: FilterIndustry) {
        savedStateHandle[NavResultKeys.SELECTED_INDUSTRY] = param
        _shouldFinish.value = true
    }

    private fun onReturnWithoutParam() {
        savedStateHandle[NavResultKeys.SELECTED_INDUSTRY] = null
        _shouldFinish.value = true
    }

    fun consumeFinishEvent() {
        _shouldFinish.value = false
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 1000L
    }
}
