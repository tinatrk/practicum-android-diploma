package ru.practicum.android.diploma.presentation.mappers

import ru.practicum.android.diploma.domain.models.filters.FilterAddress
import ru.practicum.android.diploma.domain.models.filters.FilterCountry
import ru.practicum.android.diploma.domain.models.filters.FilterIndustry
import ru.practicum.android.diploma.domain.models.filters.FilterRegion
import ru.practicum.android.diploma.domain.models.filters.FilterSettings
import ru.practicum.android.diploma.presentation.filters.models.FilterAddressUi
import ru.practicum.android.diploma.presentation.filters.models.FilterCountryUi
import ru.practicum.android.diploma.presentation.filters.models.FilterIndustryUi
import ru.practicum.android.diploma.presentation.filters.models.FilterRegionUi
import ru.practicum.android.diploma.presentation.filters.models.FilterSettingsUi

class FilterConverter {
    /** FilterCountry map */
    fun toFilterCountryUi(filterCountry: FilterCountry): FilterCountryUi {
        return FilterCountryUi(
            id = filterCountry.id,
            name = filterCountry.name
        )
    }

    fun toFilterCountryUiList(filterCountries: List<FilterCountry>): List<FilterCountryUi> {
        return filterCountries.map { toFilterCountryUi(it) }
    }

    fun toFilterCountry(filterCountryUi: FilterCountryUi): FilterCountry {
        return FilterCountry(
            id = filterCountryUi.id,
            name = filterCountryUi.name
        )
    }

    /** FilterRegion map */
    fun toFilterRegionUi(filterRegion: FilterRegion): FilterRegionUi {
        return FilterRegionUi(
            id = filterRegion.id,
            name = filterRegion.name,
            parentId = filterRegion.parentId
        )
    }

    fun toFilterRegionUiList(filterRegions: List<FilterRegion>): List<FilterRegionUi> {
        return filterRegions.map { toFilterRegionUi(it) }
    }

    fun toFilterRegion(filterRegionUi: FilterRegionUi): FilterRegion {
        return FilterRegion(
            id = filterRegionUi.id,
            name = filterRegionUi.name,
            parentId = filterRegionUi.parentId
        )
    }

    /** FilterIndustry map */
    fun toFilterIndustryUi(filterIndustry: FilterIndustry): FilterIndustryUi {
        return FilterIndustryUi(
            id = filterIndustry.id,
            name = filterIndustry.name
        )
    }

    fun toFilterIndustry(filterIndustryUi: FilterIndustryUi): FilterIndustry {
        return FilterIndustry(
            id = filterIndustryUi.id,
            name = filterIndustryUi.name
        )
    }

    fun toFilterIndustryUiList(filterIndustries: List<FilterIndustry>): List<FilterIndustryUi> {
        return filterIndustries.map { toFilterIndustryUi(it) }
    }

    /** FilterAddress map */
    fun toFilterAddressUi(filterAddress: FilterAddress): FilterAddressUi {
        return FilterAddressUi(
            country = toFilterCountryUi(filterAddress.country),
            region = filterAddress.region?.let { toFilterRegionUi(it) }
        )
    }

    fun toFilterAddress(filterAddressUi: FilterAddressUi): FilterAddress {
        return FilterAddress(
            country = toFilterCountry(filterAddressUi.country),
            region = filterAddressUi.region?.let { toFilterRegion(it) }
        )
    }

    /** FilterSettings map */
    fun toFilterSettingsUi(filterSettings: FilterSettings): FilterSettingsUi {
        return FilterSettingsUi(
            address = filterSettings.address?.let { toFilterAddressUi(it) },
            industry = filterSettings.industry?.let { toFilterIndustryUi(it) },
            salary = filterSettings.salary,
            onlyWithSalary = filterSettings.onlyWithSalary
        )
    }
}
