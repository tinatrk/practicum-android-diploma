package ru.practicum.android.diploma.data.dto.response

import ru.practicum.android.diploma.data.dto.models.FilterAreaDto
import ru.practicum.android.diploma.data.network.Response

data class FilterAreaResponse(val areas: List<FilterAreaDto>) : Response()
