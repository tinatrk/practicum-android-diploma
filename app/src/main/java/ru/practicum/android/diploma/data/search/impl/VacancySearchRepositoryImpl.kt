package ru.practicum.android.diploma.data.search.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converter.VacancyConverter
import ru.practicum.android.diploma.data.dto.request.VacancyRequest
import ru.practicum.android.diploma.data.dto.response.VacancyResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RequestOptionKeys
import ru.practicum.android.diploma.domain.models.filters.FilterSettings
import ru.practicum.android.diploma.domain.models.vacancy.VacancyPage
import ru.practicum.android.diploma.domain.search.api.repository.VacancySearchRepository
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_OK
import java.net.HttpURLConnection.HTTP_UNAVAILABLE

class VacancySearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyConverter: VacancyConverter
) : VacancySearchRepository {

    override fun search(
        query: String,
        page: Int,
        filterSettings: FilterSettings?
    ): Flow<Resource<VacancyPage, Failure>> = flow {
        val response = networkClient.doRequest(
            VacancyRequest(
                text = query,
                options = getOptionsMap(page = page, filterSettings = filterSettings),
                onlyWithSalary = filterSettings?.onlyWithSalary ?: false
            )
        )

        when (response.resultCode) {
            HTTP_OK -> {
                with(response as VacancyResponse) {
                    val vacancyPage = vacancyConverter.map(response)
                    emit(Resource.Success(vacancyPage))
                }
            }

            HTTP_NOT_FOUND -> {
                emit(Resource.Error(Failure.NotFound))
            }

            HTTP_BAD_REQUEST -> {
                emit(Resource.Error(Failure.BadRequest))
            }

            HTTP_INTERNAL_ERROR -> {
                emit(Resource.Error(Failure.Unknown()))
            }

            HTTP_UNAVAILABLE -> {
                emit(Resource.Error(Failure.Network))
            }
        }
    }

    private fun getOptionsMap(
        page: Int,
        filterSettings: FilterSettings?
    ): Map<String, String> {
        val options: MutableMap<String, String> = mutableMapOf()
        options[RequestOptionKeys.PAGE] = page.toString()
        if (filterSettings?.salary != null) options[RequestOptionKeys.SALARY] = filterSettings.salary.toString()
        if (filterSettings?.industry != null) {
            options[RequestOptionKeys.INDUSTRY] =
                filterSettings.industry.id.toString()
        }
        if (filterSettings?.address != null) {
            val areaId = if (filterSettings.address.region != null) {
                filterSettings.address.region.id
            } else {
                filterSettings.address.country.id
            }
            options[RequestOptionKeys.AREA] = areaId.toString()
        }
        return options
    }
}
