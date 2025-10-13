package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.models.FilterAreaDto
import ru.practicum.android.diploma.data.dto.models.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.response.VacancyDetailResponse
import ru.practicum.android.diploma.data.dto.response.VacancyResponse

interface DiplomaApi {

    @GET("/areas")
    suspend fun getAreas(): List<FilterAreaDto>

    @GET("/industries")
    suspend fun getIndustries(): List<FilterIndustryDto>

    @GET("vacancies") // Тут мне кажется не надо палку ставить в начале. А то 2 получится в итоге
    suspend fun getVacancy(
        @QueryMap options: Map<String, Int>,
        @Query("text") text: String,
        @Query("only_with_salary") onlyWithSalary: Boolean
    ): VacancyResponse

    @GET("/vacancies/{id}")
    suspend fun getVacancyById(
        @Path("id") id: String
    ): VacancyDetailResponse
}
