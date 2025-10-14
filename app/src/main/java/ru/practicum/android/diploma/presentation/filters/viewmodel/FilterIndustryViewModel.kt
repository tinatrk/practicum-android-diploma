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
import ru.practicum.android.diploma.presentation.filters.models.FilterIndustryScreenState
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
        MutableStateFlow<FilterIndustryScreenState>(FilterIndustryScreenState.Loading)
    val screenState = _screenState.asStateFlow()
    private val _industries = MutableStateFlow<List<FilterIndustry>>(emptyList())
    private var displayedIndustries: List<FilterIndustry> = listOf()

    private var curChoice: FilterIndustry? = null

    private var searchJob: Job? = null

    init {
        curChoice = selectedIndustryId?.let { FilterIndustry(id = it, name = "") }
        loadIndustries()
    }

    private fun loadIndustries() {
        viewModelScope.launch {
            filterInteractor.getIndustries().collect { result ->
                val state: FilterIndustryScreenState = when (result) {
                    is Resource.Success -> {
                        _industries.value = result.data.toList()
                        FilterIndustryScreenState.Content(
                            data = result.data,
                            curChoice = curChoice?.id,
                        )
                    }

                    is Resource.Error -> {
                        FilterIndustryScreenState.Error(
                            error = result.error,
                        )
                    }
                }
                renderState(state)
            }
        }
    }

    private fun renderState(state: FilterIndustryScreenState) {
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
                FilterIndustryScreenState.Content(
                    data = newList,
                    curChoice = curChoice?.id,
                )
            )
        } else {
            renderState(
                FilterIndustryScreenState.Error(error = Failure.NotFound)
            )
        }
    }

    fun onClearSearchQuery() {
        searchJob?.cancel()
        renderState(
            FilterIndustryScreenState.Content(
                data = _industries.value,
                curChoice = curChoice?.id
            )
        )
    }

    fun onIndustryItemClick(filterIndustry: FilterIndustry?) {
        curChoice = if (curChoice?.id != filterIndustry?.id) {
            filterIndustry
        } else {
            null
        }

        renderState(
            FilterIndustryScreenState.Content(
                data = displayedIndustries,
                curChoice = curChoice?.id,
            )
        )
    }

    fun onSaveChoiceClick() {
        curChoice?.let { if (it.name != "") onReturnWithParam(it) else onReturnWithoutParam() }
            ?: onReturnWithoutParam()
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
