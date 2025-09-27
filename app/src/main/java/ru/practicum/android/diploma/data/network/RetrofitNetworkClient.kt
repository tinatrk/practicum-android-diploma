package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.request.FilterAreaRequest
import ru.practicum.android.diploma.data.dto.request.FilterIndustryRequest
import ru.practicum.android.diploma.data.dto.request.VacancyDetailRequest
import ru.practicum.android.diploma.data.dto.request.VacancyRequest

class RetrofitNetworkClient(
    private val diplomaApi: DiplomaApi
) : NetworkClient {

    override suspend fun doRequest(dto: Request): Response {
        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    FilterAreaRequest -> {
                        diplomaApi.getAreas().apply { resultCode = 200 }
                    }

                    FilterIndustryRequest -> {
                        diplomaApi.getIndustries().apply { resultCode = 200 }
                    }

                    is VacancyRequest -> {
                        diplomaApi.getVacancy(
                            options = dto.options,
                            text = dto.text,
                            onlyWithSalary = dto.onlyWithSalary
                        ).apply { resultCode = 200 }
                    }

                    is VacancyDetailRequest -> {
                        diplomaApi.getVacancyById(id = dto.id).apply { resultCode = 200 }
                    }

                    else -> {
                        Response().apply { resultCode = 400 }
                    }
                }
            } catch (e: Exception) {
                Response().apply {
                    resultCode = 500
                }
            }
        }
    }
}
