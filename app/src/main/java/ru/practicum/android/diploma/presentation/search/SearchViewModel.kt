package ru.practicum.android.diploma.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.VacancyBrief
import ru.practicum.android.diploma.domain.search.api.interactor.VacancySearchInteractor
import ru.practicum.android.diploma.util.common.Resource

class SearchViewModel(
    private val interactor: VacancySearchInteractor
) : ViewModel() {
    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val searchUiState = _searchUiState.asStateFlow()

    private val typedQuery = MutableStateFlow("")
    private var debounceJob: Job? = null
    private var currentPage = 0
    private var maxPages = Int.MAX_VALUE
    private var lastQuery: String? = null
    private var isNextPageLoading = false
    var vacanciesList = mutableListOf<VacancyBrief>()

    private companion object {
        const val PAGE_LOAD_DELAY_MS = 1000L
        const val MAX_PAGES = 10
    }

    fun setTypedQuery(newQuery: String) {
        typedQuery.value = newQuery
        startDebounceWatcher()
    }

    fun searchVacancies(query: String) {
        // Если поиск выполняется через кнопку на клавиатуре, то debounce не запустит поиск повторно.
        debounceJob?.cancel()

        currentPage = 1
        lastQuery = query
        vacanciesList.clear()
        loadNextPage()
    }

    fun loadNextPage() {
        val query = lastQuery ?: return
        if (isNextPageLoading || currentPage > maxPages) return

        viewModelScope.launch {
            isNextPageLoading = true
            val response = interactor.search(query, mapOf("page" to currentPage))
            if (currentPage == 1) _searchUiState.value = SearchUiState.Loading

            response.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        maxPages = resource.data.pages
                        val isLastPage = currentPage == maxPages

                        vacanciesList = (vacanciesList + resource.data.vacancies).toMutableList()
                        _searchUiState.value = SearchUiState.Success(
                            vacanciesList,
                            resource.data.found,
                            isLastPage
                        )
                        currentPage = resource.data.page + 1
                    }

                    is Resource.Error -> {
                        _searchUiState.value = SearchUiState.Error(resource.error)
                    }
                }
                isNextPageLoading = false
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun startDebounceWatcher() {
        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            typedQuery
                .filter { it.isNotEmpty() }
                .debounce(2000L)
                .distinctUntilChanged()
                .collectLatest { query ->
                    searchVacancies(query.trim())
                }
        }
    }
}

//    fun fakeSearchVacancies(query: String) {
//        currentPage = 1
//        lastQuery = query
//        fakeLoadNextPage()
//    }
//
//    fun fakeLoadNextPage() {
//        val query = lastQuery ?: return
//        if (isNextPageLoading || currentPage > maxPages) return
//
//        viewModelScope.launch {
//            isNextPageLoading = true
//            if (currentPage == 1) _searchUiState.value = SearchUiState.Loading
//            delay(1000L)
//
//
//            maxPages = 3
//            vacanciesList = (vacanciesList + fakeVacanciesList).toMutableList()
//
//            val isLastPage = currentPage == maxPages
//            _searchUiState.value = SearchUiState.Success(
//                vacanciesList,
//                30,
//                isLastPage
//            )
//            currentPage++
//            isNextPageLoading = false
//        }
//    }
//}
//
//val fakeVacanciesList = listOf(
//    VacancyBrief(
//        id = "1",
//        name = "Android Developer",
//        city = "Москва",
//        employerName = "TechCore",
//        employerLogo = null,
//        salaryFrom = 150_000,
//        salaryTo = 250_000,
//        salaryCurrency = "RUB"
//    ),
//    VacancyBrief(
//        id = "2",
//        name = "Data Analyst",
//        city = "Санкт-Петербург",
//        employerName = "DataBridge",
//        employerLogo = null,
//        salaryFrom = 120_000,
//        salaryTo = null,
//        salaryCurrency = "RUB"
//    ),
//    VacancyBrief(
//        id = "3",
//        name = "Product Manager",
//        city = "Екатеринбург",
//        employerName = "Innova",
//        employerLogo = null,
//        salaryFrom = 200_000,
//        salaryTo = 200_000,
//        salaryCurrency = "RUB"
//    ),
//    VacancyBrief(
//        id = "4",
//        name = "UI/UX Designer",
//        city = "Казань",
//        employerName = "Designify",
//        employerLogo = null,
//        salaryFrom = null,
//        salaryTo = 190_000,
//        salaryCurrency = "RUB"
//    ),
//    VacancyBrief(
//        id = "5",
//        name = "QA Engineer",
//        city = "Новосибирск",
//        employerName = "SoftLine QA",
//        employerLogo = null,
//        salaryFrom = null,
//        salaryTo = null,
//        salaryCurrency = "RUB"
//    )
//)

