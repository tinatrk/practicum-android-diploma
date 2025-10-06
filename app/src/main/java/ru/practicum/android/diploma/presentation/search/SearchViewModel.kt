package ru.practicum.android.diploma.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.VacancyBrief
import ru.practicum.android.diploma.domain.search.api.interactor.VacancySearchInteractor
import ru.practicum.android.diploma.util.common.Resource

class SearchViewModel(
    private val interactor: VacancySearchInteractor
) : ViewModel() {
    private val _ui = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val ui = _ui.asStateFlow()

    private var currentPage = 0
    private var maxPages = Int.MAX_VALUE
    private var lastQuery: String? = null
    private var isNextPageLoading = false
    var vacanciesList = mutableListOf<VacancyBrief>()

    fun searchVacancies(query: String) {
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
            if (currentPage == 1) _ui.value = SearchUiState.Loading

            response.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        currentPage = resource.data.page + 1
                        maxPages = resource.data.pages
                        isNextPageLoading = false
                        vacanciesList = (vacanciesList + resource.data.vacancies).toMutableList()
                        _ui.value = SearchUiState.Success(
                            vacanciesList,
                            resource.data.found
                        )
                    }

                    is Resource.Error -> {
                        isNextPageLoading = false
                        _ui.value = SearchUiState.Error(resource.error)
                    }
                }
            }
        }
    }

    fun fakeSearchVacancies(query: String) {
        currentPage = 1
        lastQuery = query
        fakeLoadNextPage()
    }

    fun fakeLoadNextPage() {
        val query = lastQuery ?: return
        if (isNextPageLoading || currentPage > maxPages) return

        viewModelScope.launch {
            isNextPageLoading = true
            if (currentPage == 1) _ui.value = SearchUiState.Loading
            delay(1000L)

            currentPage++
            maxPages = 10
            isNextPageLoading = false

            vacanciesList = (vacanciesList + fakeVacanciesList).toMutableList()
            _ui.value = SearchUiState.Success(
                vacanciesList,
                50
            )
        }
    }
}


val fakeVacanciesList = listOf(
    VacancyBrief(
        id = "1",
        name = "Android Developer",
        city = "Москва",
        employerName = "TechCore",
        employerLogo = null,
        salaryFrom = 150_000,
        salaryTo = 250_000,
        salaryCurrency = "RUB"
    ),
    VacancyBrief(
        id = "2",
        name = "Data Analyst",
        city = "Санкт-Петербург",
        employerName = "DataBridge",
        employerLogo = null,
        salaryFrom = 120_000,
        salaryTo = null,
        salaryCurrency = "RUB"
    ),
    VacancyBrief(
        id = "3",
        name = "Product Manager",
        city = "Екатеринбург",
        employerName = "Innova",
        employerLogo = null,
        salaryFrom = 200_000,
        salaryTo = 200_000,
        salaryCurrency = "RUB"
    ),
    VacancyBrief(
        id = "4",
        name = "UI/UX Designer",
        city = "Казань",
        employerName = "Designify",
        employerLogo = null,
        salaryFrom = null,
        salaryTo = 190_000,
        salaryCurrency = "RUB"
    ),
    VacancyBrief(
        id = "5",
        name = "QA Engineer",
        city = "Новосибирск",
        employerName = "SoftLine QA",
        employerLogo = null,
        salaryFrom = null,
        salaryTo = null,
        salaryCurrency = "RUB"
    )
)

