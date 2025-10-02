package ru.practicum.android.diploma.domain.models

data class VacancyBrief(
    val id: String,
    val name: String?,
    val salary: Salary?,
    val url: String?,
    val industry: String?
)
