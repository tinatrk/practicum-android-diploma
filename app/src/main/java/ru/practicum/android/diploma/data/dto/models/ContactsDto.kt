package ru.practicum.android.diploma.data.dto.models

data class ContactsDto(
    val id: String,
    val name: String?,
    val email: String?,
    val phone: List<String>?
)
