package ru.practicum.android.diploma.presentation.models

import ru.practicum.android.diploma.domain.models.Contacts
import ru.practicum.android.diploma.domain.models.Employer

data class VacancyInfo(
    val id: String,
    val name: String,
    val description: String?,
    val salary: String,
    val address: String?,
    val experience: String,
    val schedule: String?,
    val employment: String?,
    val contacts: Contacts?,
    val employerName: String,
    val employerLogo: String?,
    val area: String,
    val skills: List<String>?,
    val url: String?,
    val industry: String?,
    val isFavorite: Boolean
)
