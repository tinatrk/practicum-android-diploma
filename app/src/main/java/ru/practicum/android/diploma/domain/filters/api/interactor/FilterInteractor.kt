package ru.practicum.android.diploma.domain.filters.api.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.filters.FilterAddress
import ru.practicum.android.diploma.domain.models.filters.FilterCountry
import ru.practicum.android.diploma.domain.models.filters.FilterIndustry
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource

interface FilterInteractor {
    fun getIndustries(): Flow<Resource<List<FilterIndustry>, Failure>>

    fun getCountries(): Flow<Resource<List<FilterCountry>, Failure>>

    fun getRegions(countryId: Int?): Flow<Resource<List<FilterAddress>, Failure>>
}
