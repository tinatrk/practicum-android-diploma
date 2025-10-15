package ru.practicum.android.diploma.domain.models.vacancy

data class VacancyPage(
    val found: Int,
    val pages: Int,
    val page: Int,
    val vacancies: List<VacancyBrief>
)
