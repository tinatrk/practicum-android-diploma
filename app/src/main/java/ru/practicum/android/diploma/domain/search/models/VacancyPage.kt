package ru.practicum.android.diploma.domain.search.models

data class VacancyPage(
    val found: Int,
    val pages: Int,
    val page: Int,
    val vacancies: List<Vacancy>
)
