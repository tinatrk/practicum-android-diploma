package ru.practicum.android.diploma.data.dto.response

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.models.VacancyDto
import ru.practicum.android.diploma.data.network.Response

data class VacancyResponse(
    @SerializedName("found") val found: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("items") val vacancies: List<VacancyDto>
) : Response()
