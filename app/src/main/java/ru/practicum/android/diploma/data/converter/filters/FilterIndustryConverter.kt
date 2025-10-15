package ru.practicum.android.diploma.data.converter.filters

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.models.FilterIndustryDto
import ru.practicum.android.diploma.domain.models.filters.FilterIndustry
import ru.practicum.android.diploma.util.ResourceProvider

class FilterIndustryConverter(
    private val resourceProvider: ResourceProvider
) {
    fun map(industry: FilterIndustryDto): FilterIndustry {
        return FilterIndustry(
            id = industry.id,
            name = industry.name ?: resourceProvider.getString(R.string.industry_unknown)
        )
    }

    fun map(industries: List<FilterIndustryDto>): List<FilterIndustry> {
        return industries.map { map(it) }
    }
}
