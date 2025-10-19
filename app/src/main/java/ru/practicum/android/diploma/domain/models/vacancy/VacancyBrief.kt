package ru.practicum.android.diploma.domain.models.vacancy

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
