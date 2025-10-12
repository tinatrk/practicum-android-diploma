package ru.practicum.android.diploma.domain.filters.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.filters.api.interactor.FilterInteractor
import ru.practicum.android.diploma.domain.filters.api.repository.FilterRepository
import ru.practicum.android.diploma.domain.models.filters.FilterCountry
import ru.practicum.android.diploma.domain.models.filters.FilterIndustry
import ru.practicum.android.diploma.domain.models.filters.FilterRegion
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource

class FilterInteractorImpl(
    private val filterRepository: FilterRepository
) : FilterInteractor {
    override fun getCountries(): Flow<Resource<List<FilterCountry>, Failure>> {
        return filterRepository.getCountries()
    }

    override fun getOtherCountries(): Flow<Resource<List<FilterCountry>, Failure>> {
        return filterRepository.getOtherCountries()
    }

    override fun getCountryNameById(countryId: Int): Flow<Resource<String, Failure>> {
        return filterRepository.getCountryNameById(countryId = countryId)
    }

    override fun getRegions(countryId: Int?): Flow<Resource<List<FilterRegion>, Failure>> {
        return filterRepository.getRegions(countryId = countryId)
    }

    override fun getIndustries(): Flow<Resource<List<FilterIndustry>, Failure>> {
        return filterRepository.getIndustries()
    }
}
