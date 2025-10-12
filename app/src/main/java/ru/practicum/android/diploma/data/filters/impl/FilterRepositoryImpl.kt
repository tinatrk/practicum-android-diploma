package ru.practicum.android.diploma.data.filters.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converter.filters.FilterAreaExtractor
import ru.practicum.android.diploma.data.converter.filters.FilterIndustryConverter
import ru.practicum.android.diploma.data.dto.request.FilterAreaRequest
import ru.practicum.android.diploma.data.dto.request.FilterIndustryRequest
import ru.practicum.android.diploma.data.dto.response.FilterAreaResponse
import ru.practicum.android.diploma.data.dto.response.FilterIndustryResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.filters.api.repository.FilterRepository
import ru.practicum.android.diploma.domain.models.filters.FilterCountry
import ru.practicum.android.diploma.domain.models.filters.FilterIndustry
import ru.practicum.android.diploma.domain.models.filters.FilterRegion
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR
import java.net.HttpURLConnection.HTTP_OK
import java.net.HttpURLConnection.HTTP_UNAVAILABLE

class FilterRepositoryImpl(
    private val networkClient: NetworkClient,
    private val areaExtractor: FilterAreaExtractor,
    private val industryConverter: FilterIndustryConverter
) : FilterRepository {
    override fun getCountries(): Flow<Resource<List<FilterCountry>, Failure>> = flow {
        val response = networkClient.doRequest(
            FilterAreaRequest
        )

        if (response.resultCode == HTTP_OK) {
            emit(
                Resource.Success(
                    areaExtractor
                        .getCountriesShortList((response as FilterAreaResponse).areas)
                        .sortedWith(
                            compareBy(String.CASE_INSENSITIVE_ORDER) { it.name })
                        .run {
                            val (others, target) = partition { it.id != OTHER_REGIONS_ID }
                            others + target
                        }
                )
            )
        } else {
            emit(
                processError<List<FilterCountry>>(
                    resultCode = response.resultCode
                )
            )
        }
    }

    override fun getOtherCountries(): Flow<Resource<List<FilterCountry>, Failure>> = flow {
        val response = networkClient.doRequest(
            FilterAreaRequest
        )

        if (response.resultCode == HTTP_OK) {
            emit(
                Resource.Success(
                    areaExtractor
                        .getOtherCountries((response as FilterAreaResponse).areas)
                        .sortedWith(
                            compareBy(String.CASE_INSENSITIVE_ORDER) { it.name })
                )
            )
        } else {
            emit(
                processError<List<FilterCountry>>(
                    resultCode = response.resultCode
                )
            )
        }
    }

    override fun getCountryNameById(countryId: Int): Flow<Resource<String, Failure>> = flow {
        val response = networkClient.doRequest(
            FilterAreaRequest
        )

        if (response.resultCode == HTTP_OK) {
            emit(
                Resource.Success(
                    areaExtractor
                        .getCountryNameById(countryId = countryId, areas = (response as FilterAreaResponse).areas)
                )
            )
        } else {
            emit(
                processError<String>(
                    resultCode = response.resultCode
                )
            )
        }
    }

    override fun getRegions(countryId: Int?): Flow<Resource<List<FilterRegion>, Failure>> = flow {
        val response = networkClient.doRequest(
            FilterAreaRequest
        )

        if (response.resultCode == HTTP_OK) {
            if (countryId != null) {
                emit(
                    Resource.Success(
                        areaExtractor
                            .getAllRegionsByCountry(
                                areas = (response as FilterAreaResponse).areas,
                                countryId = countryId
                            )
                            .sortedWith(
                                compareBy(String.CASE_INSENSITIVE_ORDER) { it.name }
                            )
                    )
                )
            } else {
                emit(
                    Resource.Success(
                        areaExtractor
                            .getAllRegions((response as FilterAreaResponse).areas)
                            .sortedWith(
                                compareBy(String.CASE_INSENSITIVE_ORDER) { it.name }
                            )
                    )
                )
            }
        } else {
            emit(
                processError<List<FilterRegion>>(
                    resultCode = response.resultCode
                )
            )
        }
    }

    override fun getIndustries(): Flow<Resource<List<FilterIndustry>, Failure>> = flow {
        val response = networkClient.doRequest(
            FilterIndustryRequest
        )

        if (response.resultCode == HTTP_OK) {
            emit(
                Resource.Success(
                    industryConverter
                        .map((response as FilterIndustryResponse).industries)
                        .sortedWith(
                            compareBy(String.CASE_INSENSITIVE_ORDER) { it.name }
                        )
                )
            )
        } else {
            processError<List<FilterIndustry>>(
                resultCode = response.resultCode
            )
        }
    }

    private fun <T> processError(
        resultCode: Int,
    ): Resource<T, Failure> {
        return when (resultCode) {
            HTTP_UNAVAILABLE -> {
                Resource.Error(error = Failure.Network)
            }

            HTTP_BAD_REQUEST -> {
                Resource.Error(error = Failure.BadRequest)
            }

            HTTP_INTERNAL_ERROR -> {
                Resource.Error(error = Failure.Unknown())
            }

            else -> {
                Resource.Error(error = Failure.Unknown())
            }
        }
    }

    companion object {
        private const val OTHER_REGIONS_ID = 1001
    }
}
