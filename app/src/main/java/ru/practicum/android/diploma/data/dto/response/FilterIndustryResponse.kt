package ru.practicum.android.diploma.data.dto.response

import ru.practicum.android.diploma.data.dto.models.FilterIndustryDto
import ru.practicum.android.diploma.data.network.Response

data class FilterIndustryResponse(val industries: List<FilterIndustryDto>) : Response()
