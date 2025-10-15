package ru.practicum.android.diploma.data.dto.request

import ru.practicum.android.diploma.data.network.Request

data class VacancyRequest(
    val options: Map<String, String>,
    val text: String,
    val onlyWithSalary: Boolean
) : Request
