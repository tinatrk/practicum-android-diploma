package ru.practicum.android.diploma.domain.filters.impl

import ru.practicum.android.diploma.domain.filters.api.interactor.FilterInteractor
import ru.practicum.android.diploma.domain.filters.api.repository.FilterRepository
import ru.practicum.android.diploma.domain.models.filters.FilterAddress
import ru.practicum.android.diploma.domain.models.filters.FilterCountry
import ru.practicum.android.diploma.domain.models.filters.FilterIndustry
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource

class FilterInteractorImpl(
    private val filterRepository: FilterRepository
) : FilterInteractor {
    override fun getCountries(): Resource<List<FilterCountry>, Failure> {
        return filterRepository.getCountries()
    }

    override fun getRegions(countryId: Int?): Resource<List<FilterAddress>, Failure> {
        return filterRepository.getRegions(countryId = countryId)
    }

    override fun getIndustries(): Resource<List<FilterIndustry>, Failure> {
        return filterRepository.getIndustries()
    }
}
