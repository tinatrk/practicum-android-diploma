package ru.practicum.android.diploma.data.details.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converter.VacancyConverter
import ru.practicum.android.diploma.data.dto.request.VacancyDetailRequest
import ru.practicum.android.diploma.data.dto.response.VacancyDetailResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.details.api.repository.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR
import java.net.HttpURLConnection.HTTP_OK
import java.net.HttpURLConnection.HTTP_UNAVAILABLE

class VacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyConverter: VacancyConverter
) : VacancyDetailsRepository {

    override fun getVacancyDetails(vacancyId: String): Flow<Resource<Vacancy, Failure>> = flow {
        val response = networkClient.doRequest(VacancyDetailRequest(vacancyId))

        when (response.resultCode) {
            HTTP_OK -> {
                with(response as VacancyDetailResponse) {
                    val vacancy = vacancyConverter.map(response)
                    emit(Resource.Success(vacancy))
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
