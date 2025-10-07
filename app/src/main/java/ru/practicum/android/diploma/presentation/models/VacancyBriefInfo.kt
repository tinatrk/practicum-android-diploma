package ru.practicum.android.diploma.presentation.models

data class VacancyBriefInfo(
    val id: String,
    val name: String,
    val city: String,
    val employerName: String,
    val employerLogo: String?,
    val salary: String
)
