package ru.practicum.android.diploma.domain.models.vacancy

data class Contacts(
    val id: String,
    val name: String?,
    val email: String?,
    val phone: List<Phone>?
)
