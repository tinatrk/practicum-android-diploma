package ru.practicum.android.diploma.data.search.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.convertor.VacancyConverter
import ru.practicum.android.diploma.data.dto.request.VacancyRequest
import ru.practicum.android.diploma.data.dto.response.VacancyResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.models.VacancyPage
import ru.practicum.android.diploma.domain.search.api.repository.VacancySearchRepository
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR
import java.net.HttpURLConnection.HTTP_OK
import java.net.HttpURLConnection.HTTP_UNAVAILABLE

class VacancySearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyConverter: VacancyConverter
) : VacancySearchRepository {

    override fun search(
        query: String,
        options: Map<String, Int>,
        onlyWithSalary: Boolean
    ): Flow<Resource<VacancyPage, Failure>> = flow {
        val response = networkClient.doRequest(
            VacancyRequest(
                text = query,
                options = options,
                onlyWithSalary = onlyWithSalary
            )
        )

        when (response.resultCode) {
            HTTP_OK -> {
                with(response as VacancyResponse) {
                    val vacancyPage = vacancyConverter.map(response)

                    if (vacancyPage.vacancies.isEmpty()) {
                        emit(Resource.Error(Failure.NotFound))
                    } else {
                        emit(Resource.Success(vacancyPage))
                    }
                }
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
}
