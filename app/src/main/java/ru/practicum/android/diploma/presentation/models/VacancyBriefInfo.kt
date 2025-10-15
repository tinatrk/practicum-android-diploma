package ru.practicum.android.diploma.presentation.models

import androidx.compose.runtime.Immutable

@Immutable
data class VacancyBriefInfo(
    val id: String,
    val name: String,
    val city: String,
    val employerName: String,
    val employerLogo: String?,
    val salary: String
)
