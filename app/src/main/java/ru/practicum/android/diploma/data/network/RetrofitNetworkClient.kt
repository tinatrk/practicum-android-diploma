package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.request.FilterAreaRequest
import ru.practicum.android.diploma.data.dto.request.FilterIndustryRequest
import ru.practicum.android.diploma.data.dto.request.VacancyDetailRequest
import ru.practicum.android.diploma.data.dto.request.VacancyRequest
import ru.practicum.android.diploma.util.NetworkInfoProvider
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR
import java.net.HttpURLConnection.HTTP_OK
import java.net.HttpURLConnection.HTTP_UNAVAILABLE

class RetrofitNetworkClient(
    private val diplomaApi: DiplomaApi,
    private val networkInfoProvider: NetworkInfoProvider
) : NetworkClient {

    override suspend fun doRequest(dto: Request): Response {
        if (!networkInfoProvider.isConnected()) {
            return Response().apply { resultCode = HTTP_UNAVAILABLE }
        }

        return withContext(Dispatchers.IO) {
            runCatching {
                fetchResponse(dto)
            }.getOrElse {
                Response().apply { resultCode = HTTP_INTERNAL_ERROR }
            }
        }
    }

    private suspend fun fetchResponse(dto: Request): Response {
        return when (dto) {
            FilterAreaRequest -> {
                diplomaApi.getAreas().apply { resultCode = HTTP_OK }
            }

            FilterIndustryRequest -> {
                diplomaApi.getIndustries().apply { resultCode = HTTP_OK }
            }

            is VacancyRequest -> {
                diplomaApi.getVacancy(
                    options = dto.options,
                    text = dto.text,
                    onlyWithSalary = dto.onlyWithSalary
                ).apply { resultCode = HTTP_OK }
            }

            is VacancyDetailRequest -> {
                diplomaApi.getVacancyById(id = dto.id).apply { resultCode = HTTP_OK }
            }

            else -> {
                Response().apply { resultCode = HTTP_BAD_REQUEST }
            }
        }
    }
}
