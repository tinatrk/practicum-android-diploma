package ru.practicum.android.diploma.domain.models

data class VacancyBrief(
    val id: String,
    val name: String?,
    val city: String?,
    val employerName: String?,
    val employerLogo: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurrency: String?
)
