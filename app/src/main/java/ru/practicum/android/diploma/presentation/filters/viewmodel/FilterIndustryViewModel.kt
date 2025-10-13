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

    private var curChoice: FilterIndustry? = null
    private val originalList: MutableList<FilterIndustry> = mutableListOf()
    private val filteredList: MutableList<FilterIndustry> = mutableListOf()

    private var searchJob: Job? = null

    init {
        curChoice = selectedIndustryId?.let { FilterIndustry(id = it, name = EMPTY_STRING) }
        viewModelScope.launch {
            filterInteractor.getIndustries().collect { result ->
                val state: FilterIndustryScreenState = when (result) {
                    is Resource.Success -> {
                        originalList.addAll(result.data)
                        FilterIndustryScreenState.Content(
                            data = result.data,
                            curChoice = curChoice?.id
                        )
                    }

                    is Resource.Error -> {
                        FilterIndustryScreenState.Error(error = result.error)
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

    // сделать debounce?
    fun onSearchTextChanged(searchQuery: String?) {
        searchJob?.cancel()
        // сохранять запрос как состояние?

        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY_MILLIS)
            search(searchQuery)
        }
    }

    fun search(searchQuery: String? = null) {
        renderState(FilterIndustryScreenState.Loading)

        filteredList.clear()
        if (searchQuery.isNullOrEmpty()) {
            updateDisplayList(originalList)
        } else {
            for (item in originalList) {
                if (item.name.contains(searchQuery, true)) {
                    filteredList.add(item)
                }
            }
            updateDisplayList(filteredList)
        }
    }

    private fun updateDisplayList(newList: List<FilterIndustry>) {
        renderState(
            FilterIndustryScreenState.Content(
                data = newList,
                curChoice = curChoice?.id
            )
        )
    }

    fun onClearSearchQuery() {
        searchJob?.cancel()
        filteredList.clear()
        updateDisplayList(originalList)
    }

    fun onIndustryItemClick(filterIndustry: FilterIndustry?) {
        curChoice = filterIndustry
        renderState(
            FilterIndustryScreenState.Content(
                data = filteredList.ifEmpty { originalList },
                curChoice = curChoice?.id
            )
        )
    }

    fun onSaveChoiceClick() {
        curChoice?.let { if (it.name != EMPTY_STRING) onReturnWithParam(it) else onReturnWithoutParam() }
            ?: onReturnWithoutParam()
    }

    fun onBackNavigate() {
        onReturnWithoutParam()
    }


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
        private const val EMPTY_STRING = ""
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
    }
}
