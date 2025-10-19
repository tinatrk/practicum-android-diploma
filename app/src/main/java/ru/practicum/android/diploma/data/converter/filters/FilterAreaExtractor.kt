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
        return if (country.parentId == null) {
            FilterCountry(
                id = country.id,
                name = country.name ?: resourceProvider.getString(R.string.country_unknown)
            )
        } else {
            null
        }
    }

    private fun getRegion(region: FilterAreaDto): FilterRegion? {
        return if (region.parentId != null) {
            FilterRegion(
                id = region.id,
                name = region.name ?: resourceProvider.getString(R.string.region_unknown),
                parentId = region.parentId
            )
        } else {
            null
        }
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

    fun getCountriesFromAreas(areas: List<FilterAreaDto>): List<FilterCountry> {
        return areas.mapNotNull { getCountry(it) }
    }

    fun getCountryNameById(countryId: Int, areas: List<FilterAreaDto>): String {
        return getCountriesFromAreas(areas).find { it.id == countryId }?.name
            ?: resourceProvider.getString(R.string.country_unknown)
    }

    fun getAllRegions(areas: List<FilterAreaDto>): List<FilterRegion> {
        return getRegionsFromAreas(getFullAreaList(areas))
    }

    fun getAllRegionsByCountry(areas: List<FilterAreaDto>, countryId: Int): List<FilterRegion> {
        val country = areas.find { it.id == countryId }
        return getRegionsFromAreas(
            getFullAreaList(country?.areas ?: emptyList())
        )
    }
}
