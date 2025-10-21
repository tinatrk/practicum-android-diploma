package ru.practicum.android.diploma.data.converter

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.data.db.dto.VacancyBriefDto
import ru.practicum.android.diploma.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.data.dto.response.VacancyDetailResponse
import ru.practicum.android.diploma.data.dto.response.VacancyResponse
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy
import ru.practicum.android.diploma.domain.models.vacancy.VacancyBrief
import ru.practicum.android.diploma.domain.models.vacancy.VacancyPage
import java.lang.reflect.Type

class VacancyConverter(
    private val gson: Gson,
    private val salaryConverter: SalaryConverter,
    private val addressConverter: AddressConverter,
    private val contactsConverter: ContactsConverter,
    private val employerConverter: EmployerConverter,
    private val filterAreaConverter: FilterAreaConverter
) {
    fun map(vacancyResponse: VacancyResponse): VacancyPage {
        return VacancyPage(
            found = vacancyResponse.found,
            pages = vacancyResponse.pages,
            page = vacancyResponse.page,
            vacancies = vacancyResponse.vacancies.map { vacancyDto ->
                VacancyBrief(
                    id = vacancyDto.id,
                    name = vacancyDto.name,
                    city = vacancyDto.address?.city,
                    employerName = vacancyDto.employer?.name,
                    employerLogo = vacancyDto.employer?.logo,
                    salaryFrom = vacancyDto.salary?.from,
                    salaryTo = vacancyDto.salary?.to,
                    salaryCurrency = vacancyDto.salary?.currency
                )
            }
        )
    }

    fun map(vacancyDetailsResponse: VacancyDetailResponse): Vacancy {
        return Vacancy(
            id = vacancyDetailsResponse.id,
            name = vacancyDetailsResponse.name,
            description = vacancyDetailsResponse.description,
            salary = salaryConverter.map(vacancyDetailsResponse.salary),
            address = addressConverter.map(vacancyDetailsResponse.address),
            experience = vacancyDetailsResponse.experience?.name,
            schedule = vacancyDetailsResponse.schedule?.name,
            employment = vacancyDetailsResponse.employment?.name,
            contacts = contactsConverter.map(vacancyDetailsResponse.contacts),
            employer = employerConverter.map(vacancyDetailsResponse.employer),
            area = filterAreaConverter.map(vacancyDetailsResponse.area),
            skills = vacancyDetailsResponse.skills,
            url = vacancyDetailsResponse.url,
            industry = vacancyDetailsResponse.industry?.name,
            isFavorite = false
        )
    }

    fun map(vacancyBrief: VacancyBriefDto): VacancyBrief {
        return VacancyBrief(
            id = vacancyBrief.vacancyId,
            name = vacancyBrief.name,
            city = vacancyBrief.city,
            employerName = vacancyBrief.employerName,
            employerLogo = vacancyBrief.employerLogo,
            salaryFrom = vacancyBrief.salaryFrom,
            salaryTo = vacancyBrief.salaryTo,
            salaryCurrency = vacancyBrief.salaryCurrency,
        )
    }

    fun map(vacancy: Vacancy): VacancyEntity {
        return VacancyEntity(
            vacancyId = vacancy.id,
            name = vacancy.name,
            description = vacancy.description,
            salary = salaryConverter.map(vacancy.salary),
            address = addressConverter.map(vacancy.address),
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            employment = vacancy.employment,
            contacts = contactsConverter.map(vacancy.contacts),
            employer = employerConverter.map(vacancy.employer),
            area = filterAreaConverter.map(vacancy.area),
            skills = convertListToString(vacancy.skills),
            url = vacancy.url,
            industry = vacancy.industry,
        )
    }

    fun map(vacancy: VacancyEntity): Vacancy {
        return Vacancy(
            id = vacancy.vacancyId,
            name = vacancy.name,
            description = vacancy.description,
            salary = salaryConverter.map(vacancy.salary),
            address = addressConverter.map(vacancy.address),
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            employment = vacancy.employment,
            contacts = contactsConverter.map(vacancy.contacts),
            employer = employerConverter.map(vacancy.employer),
            area = filterAreaConverter.map(vacancy.area),
            skills = vacancy.skills?.let { getListFromString(it) },
            url = vacancy.url,
            industry = vacancy.industry,
            isFavorite = true
        )
    }

    private fun convertListToString(list: List<String>?): String? {
        return list?.let { gson.toJson(list) }
    }

    private fun getListFromString(str: String): List<String>? {
        val type: Type = object : TypeToken<List<String>>() {}.type
        return try {
            gson.fromJson(str, type)
        } catch (e: JsonSyntaxException) {
            Log.e(VACANCY_CONVERTER_TAG, "Не удалось получить список из строки (json)")
            null
        }
    }

    companion object {
        private const val VACANCY_CONVERTER_TAG = "VacancyConverter"
    }
}
