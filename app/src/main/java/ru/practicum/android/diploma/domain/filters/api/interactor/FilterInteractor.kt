package ru.practicum.android.diploma.domain.filters.api.interactor

import ru.practicum.android.diploma.domain.models.filters.FilterAddress
import ru.practicum.android.diploma.domain.models.filters.FilterCountry
import ru.practicum.android.diploma.domain.models.filters.FilterIndustry
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource

interface FilterInteractor {
    fun getIndustries(): Resource<List<FilterIndustry>, Failure>

    fun getCountries(): Resource<List<FilterCountry>, Failure>

    fun getRegions(countryId: Int?): Resource<List<FilterAddress>, Failure>
}
