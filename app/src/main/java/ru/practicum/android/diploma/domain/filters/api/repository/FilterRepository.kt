package ru.practicum.android.diploma.domain.filters.api.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.filters.FilterCountry
import ru.practicum.android.diploma.domain.models.filters.FilterIndustry
import ru.practicum.android.diploma.domain.models.filters.FilterRegion
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource

interface FilterRepository {
    fun getIndustries(): Flow<Resource<List<FilterIndustry>, Failure>>

    fun getCountries(): Flow<Resource<List<FilterCountry>, Failure>>

    fun getCountryNameById(countryId: Int): Flow<Resource<String, Failure>>

    fun getRegions(countryId: Int?): Flow<Resource<List<FilterRegion>, Failure>>
}
