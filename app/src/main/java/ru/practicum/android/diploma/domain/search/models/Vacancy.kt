package ru.practicum.android.diploma.domain.search.models

data class Vacancy(
    val id: String,
    val name: String?,
    val salary: Salary?,
    val url: String?,
    val industry: String?
)
