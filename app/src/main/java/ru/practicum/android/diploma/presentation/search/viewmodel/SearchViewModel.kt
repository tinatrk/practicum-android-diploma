package ru.practicum.android.diploma.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.search.api.interactor.VacancySearchInteractor
import ru.practicum.android.diploma.presentation.converter.VacancyConverter
import ru.practicum.android.diploma.presentation.models.VacancyBriefInfo
import ru.practicum.android.diploma.presentation.search.models.SearchUiState
import ru.practicum.android.diploma.util.common.Resource

class SearchViewModel(
    private val interactor: VacancySearchInteractor,
    private val converter: VacancyConverter
) : ViewModel() {
    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val searchUiState = _searchUiState.asStateFlow()
    private val _isNextPageError = MutableStateFlow(false)
    val isNextPageError = _isNextPageError.asStateFlow()
    private var debounceJob: Job? = null
    private val typedQuery = MutableStateFlow("")
    private var currentPage = 0
    private var maxPages = Int.MAX_VALUE
    private var lastQuery: String? = null
    private var isNextPageLoading = false
    var vacanciesInfoList = mutableListOf<VacancyBriefInfo>()

    private companion object {
        const val PAGE_LOAD_DEBOUNCE = 2000L
        const val PAGE_PARAM = "page"
    }

    fun searchVacancies(query: String) {
        // Если поиск выполняется через кнопку на клавиатуре, то debounce не запустит поиск повторно.
        debounceJob?.cancel()

        if (query.isEmpty()) {
            _searchUiState.value = SearchUiState.Idle
            return
        }

        if (query == lastQuery && _searchUiState.value is SearchUiState.Success) {
            return
        }

        currentPage = 0
        lastQuery = query
        vacanciesInfoList.clear()
        loadNextPage()
    }

    fun loadNextPage() {
        val query = lastQuery ?: return
        if (isNextPageLoading || currentPage > maxPages) return

        viewModelScope.launch {
            isNextPageLoading = true
            val response = interactor.search(query, mapOf(PAGE_PARAM to currentPage))
            if (currentPage == 0) _searchUiState.value = SearchUiState.Loading

            response.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val vacanciesBriefInfo = converter.map(resource.data.vacancies)

                        maxPages = resource.data.pages
                        val isLastPage = currentPage == maxPages
                        vacanciesInfoList = (vacanciesInfoList + vacanciesBriefInfo).toMutableList()

                        _isNextPageError.value = false
                        _searchUiState.value = SearchUiState.Success(
                            vacanciesInfoList,
                            resource.data.found,
                            isLastPage
                        )
                        currentPage = resource.data.page + 1
                    }

                    is Resource.Error -> {
                        if (currentPage > 0) {
                            _isNextPageError.value = true
                        } else {
                            _searchUiState.value = SearchUiState.Error(resource.error)
                        }
                    }
                }
                isNextPageLoading = false
            }
        }
    }

    fun setTypedQuery(newQuery: String) {
        typedQuery.value = newQuery
        startDebounceWatcher()
    }

    fun clearTextClick() {
        lastQuery = ""
        _searchUiState.value = SearchUiState.Idle
    }

    @OptIn(FlowPreview::class)
    private fun startDebounceWatcher() {
        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            typedQuery
                .debounce(PAGE_LOAD_DEBOUNCE)
                .distinctUntilChanged()
                .collectLatest { query ->
                    searchVacancies(query.trim())
                }
        }
    }
}
