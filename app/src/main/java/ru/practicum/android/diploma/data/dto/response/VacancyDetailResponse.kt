package ru.practicum.android.diploma.data.dto.response

import ru.practicum.android.diploma.data.dto.models.AddressDto
import ru.practicum.android.diploma.data.dto.models.ContactsDto
import ru.practicum.android.diploma.data.dto.models.EmployerDto
import ru.practicum.android.diploma.data.dto.models.EmploymentDto
import ru.practicum.android.diploma.data.dto.models.ExperienceDto
import ru.practicum.android.diploma.data.dto.models.FilterAreaDto
import ru.practicum.android.diploma.data.dto.models.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.models.SalaryDto
import ru.practicum.android.diploma.data.dto.models.ScheduleDto
import ru.practicum.android.diploma.data.network.Response

data class VacancyDetailResponse(
    val id: String,
    val name: String?,
    val description: String?,
    val salary: SalaryDto?,
    val address: AddressDto?,
    val experience: ExperienceDto?,
    val schedule: ScheduleDto?,
    val employment: EmploymentDto?,
    val contacts: ContactsDto?,
    val employer: EmployerDto?,
    val area: FilterAreaDto?,
    val skills: List<String>?,
    val url: String?,
    val industry: FilterIndustryDto?
) : Response()
