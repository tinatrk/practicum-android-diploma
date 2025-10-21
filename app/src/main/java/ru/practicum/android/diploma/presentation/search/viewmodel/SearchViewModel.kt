package ru.practicum.android.diploma.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.api.interactor.FilterStorageInteractor
import ru.practicum.android.diploma.domain.search.api.interactor.VacancySearchInteractor
import ru.practicum.android.diploma.presentation.converter.VacancyConverter
import ru.practicum.android.diploma.presentation.models.VacancyBriefInfo
import ru.practicum.android.diploma.presentation.search.models.SearchUiState
import ru.practicum.android.diploma.util.common.Resource

class SearchViewModel(
    private val interactor: VacancySearchInteractor,
    private val converter: VacancyConverter,
    private val filterInteractor: FilterStorageInteractor
) : ViewModel() {
    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val searchUiState = _searchUiState.asStateFlow()
    private val _isNextPageError = MutableSharedFlow<Boolean>()
    val isNextPageError = _isNextPageError.asSharedFlow()
    private var debounceJob: Job? = null
    private val typedQuery = MutableStateFlow("")
    private var currentPage = -1
    private var maxPages = Int.MAX_VALUE
    private var lastQuery: String? = null
    private var isNextPageLoading = false
    private var vacanciesInfoList = mutableListOf<VacancyBriefInfo>()

    private val _filterSettings = filterInteractor.getFilterSettings()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = null
        )

    val isFiltersSet: StateFlow<Boolean> = _filterSettings
        .map { settings ->
            settings?.let {
                listOf(
                    it.address,
                    it.industry,
                    it.salary,
                    it.onlyWithSalary?.takeIf { only -> only }
                ).any { value -> value != null }
            } ?: false
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = false
        )

    private var isFilterSetChanged: Boolean = false

    private companion object {
        const val PAGE_LOAD_DEBOUNCE = 2000L
        private const val STOP_TIMEOUT_MILLIS = 5000L
    }

    init {
        _filterSettings
            .onEach {
                isFilterSetChanged = true
                searchVacancies(lastQuery.orEmpty())
            }
            .launchIn(viewModelScope)
    }

    fun searchVacancies(query: String) {
        // Если поиск выполняется через кнопку на клавиатуре, то debounce не запустит поиск повторно.
        debounceJob?.cancel()

        if (query.isEmpty()) {
            lastQuery = null
            _searchUiState.value = SearchUiState.Idle
            return
        }

        if (query == lastQuery && _searchUiState.value is SearchUiState.Success && !isFilterSetChanged) {
            return
        }

        isFilterSetChanged = false
        currentPage = 1
        maxPages = 1
        lastQuery = query
        vacanciesInfoList.clear()
        loadNextPage()
    }

    fun loadNextPage() {
        val query = lastQuery ?: return
        if (isNextPageLoading || currentPage > maxPages) return

        viewModelScope.launch {
            isNextPageLoading = true
            val response = interactor.search(
                query = query,
                page = currentPage,
                filterSettings = _filterSettings.value
            )
            if (currentPage == 1) _searchUiState.value = SearchUiState.Loading

            response.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val vacanciesBriefInfo = converter.map(resource.data.vacancies)

                        vacanciesInfoList = (vacanciesInfoList + vacanciesBriefInfo).toMutableList()

                        maxPages = resource.data.pages
                        val isLastPage = currentPage == maxPages
                        currentPage = resource.data.page + 1

                        _isNextPageError.emit(false)
                        _searchUiState.value = SearchUiState.Success(
                            vacanciesInfoList,
                            resource.data.found,
                            isLastPage
                        )
                    }

                    is Resource.Error -> {
                        if (currentPage > 1) {
                            _isNextPageError.emit(true)
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
