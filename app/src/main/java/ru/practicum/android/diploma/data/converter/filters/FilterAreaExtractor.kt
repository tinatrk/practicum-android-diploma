package ru.practicum.android.diploma.data.converter.filters

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.models.FilterAreaDto
import ru.practicum.android.diploma.domain.models.filters.FilterCountry
import ru.practicum.android.diploma.domain.models.filters.FilterRegion
import ru.practicum.android.diploma.util.ResourceProvider

class FilterAreaExtractor(
    private val resourceProvider: ResourceProvider
) {
    private fun getCountry(country: FilterAreaDto): FilterCountry? {
        return if (country.parentId == null || country.parentId == OTHER_REGIONS_ID) {
            FilterCountry(
                id = country.id,
                name = country.name ?: resourceProvider.getString(R.string.country_unknown)
            )
        } else {
            null
        }
    }

    private fun getRegion(region: FilterAreaDto): FilterRegion? {
        return if (region.parentId != null && region.parentId != OTHER_REGIONS_ID) {
            FilterRegion(
                id = region.id,
                name = region.name ?: resourceProvider.getString(R.string.region_unknown),
                parentId = region.parentId
            )
        } else {
            null
        }
    }

    private fun getCountriesFromAreas(areas: List<FilterAreaDto>): List<FilterCountry> {
        return areas.mapNotNull { getCountry(it) }
    }

    private fun getRegionsFromAreas(areas: List<FilterAreaDto>): List<FilterRegion> {
        return areas.mapNotNull { getRegion(it) }
    }

    private fun getFullAreaList(areas: List<FilterAreaDto>): List<FilterAreaDto> {
        val list: MutableList<FilterAreaDto> = mutableListOf()
        areas.forEach { area ->
            list.add(area)
            list.addAll(area.areas?.let { getFullAreaList(it) } ?: emptyList<FilterAreaDto>())
        }
        return list
    }

    private fun getAllCountriesDto(areas: List<FilterAreaDto>): List<FilterAreaDto> {
        val list: MutableList<FilterAreaDto> = mutableListOf()
        list.addAll(areas)
        list.addAll(areas.find { it.id == OTHER_REGIONS_ID }?.areas ?: emptyList())
        return list
    }

    fun getCountriesShortList(areas: List<FilterAreaDto>): List<FilterCountry> {
        return getCountriesFromAreas(areas)
    }

    fun getOtherCountries(areas: List<FilterAreaDto>): List<FilterCountry> {
        return getCountriesFromAreas(
            areas.find { it.id == OTHER_REGIONS_ID }?.areas ?: emptyList()
        )
    }

    fun getCountryNameById(countryId: Int, areas: List<FilterAreaDto>): String {
        return getAllCountriesDto(areas).find { it.id == countryId }?.name
            ?: resourceProvider.getString(R.string.country_unknown)
    }

    fun getAllRegions(areas: List<FilterAreaDto>): List<FilterRegion> {
        return getRegionsFromAreas(getFullAreaList(areas))
    }

    fun getAllRegionsByCountry(areas: List<FilterAreaDto>, countryId: Int): List<FilterRegion> {
        var country = areas.find { it.id == countryId }
        if (country == null) {
            val countries = getAllCountriesDto(areas)
            country = countries.find { it.id == countryId }
        }
        return getRegionsFromAreas(
            getFullAreaList(country?.areas ?: emptyList())
        )
    }

    companion object {
        private const val OTHER_REGIONS_ID = 1001
    }
}
