package ru.practicum.android.diploma.data.db.dto

data class VacancyPreview(
    val vacancyId: String,
    val name: String?,
    val city: String?,
    val employerName: String?,
    val employerLogo: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurrency: String?
)
