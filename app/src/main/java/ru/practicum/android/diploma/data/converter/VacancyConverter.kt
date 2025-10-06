package ru.practicum.android.diploma.data.converter

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.data.db.dto.VacancyBriefDto
import ru.practicum.android.diploma.data.db.embedd.AddressEmbeddable
import ru.practicum.android.diploma.data.db.embedd.ContactsEmbeddable
import ru.practicum.android.diploma.data.db.embedd.EmployerEmbeddable
import ru.practicum.android.diploma.data.db.embedd.FilterAreaEmbeddable
import ru.practicum.android.diploma.data.db.embedd.SalaryEmbeddable
import ru.practicum.android.diploma.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.data.dto.response.VacancyResponse
import ru.practicum.android.diploma.domain.models.Address
import ru.practicum.android.diploma.domain.models.Contacts
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.FilterArea
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyBrief
import ru.practicum.android.diploma.domain.models.VacancyPage
import java.lang.reflect.Type

class VacancyConverter(private val gson: Gson) {

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
            salary = getSalary(vacancy.salary),
            address = getAddress(vacancy.address),
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            employment = vacancy.employment,
            contacts = getContacts(vacancy.contacts),
            employer = getEmployer(vacancy.employer),
            area = getFilterArea(vacancy.area),
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
            salary = getSalary(vacancy.salary),
            address = getAddress(vacancy.address),
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            employment = vacancy.employment,
            contacts = getContacts(vacancy.contacts),
            employer = getEmployer(vacancy.employer),
            area = getFilterArea(vacancy.area),
            skills = vacancy.skills?.let { getListFromString(it) },
            url = vacancy.url,
            industry = vacancy.industry,
            isFavorite = true
        )
    }

    private fun getSalary(salary: Salary?): SalaryEmbeddable? {
        return salary?.let {
            SalaryEmbeddable(
                from = it.from,
                to = it.to,
                currency = it.currency
            )
        }
    }

    private fun getSalary(salary: SalaryEmbeddable?): Salary? {
        return salary?.let {
            Salary(
                from = it.from,
                to = it.to,
                currency = it.currency
            )
        }
    }

    private fun getAddress(address: Address?): AddressEmbeddable? {
        return address?.let {
            AddressEmbeddable(
                city = address.city ?: EMPTY_STRING,
                street = address.street ?: EMPTY_STRING,
                building = address.building ?: EMPTY_STRING,
                fullAddress = address.fullAddress ?: EMPTY_STRING
            )
        }
    }

    private fun getAddress(address: AddressEmbeddable?): Address? {
        return address?.let {
            Address(
                city = address.city,
                street = address.street,
                building = address.building,
                fullAddress = address.fullAddress
            )
        }
    }

    private fun getContacts(contacts: ContactsEmbeddable?): Contacts? {
        return contacts?.let {
            Contacts(
                id = contacts.id,
                name = contacts.name,
                email = contacts.email,
                phone = getListFromString(contacts.phone)
            )
        }
    }

    private fun getContacts(contacts: Contacts?): ContactsEmbeddable? {
        return contacts?.let {
            ContactsEmbeddable(
                id = contacts.id,
                name = contacts.name ?: EMPTY_STRING,
                email = contacts.email ?: EMPTY_STRING,
                phone = convertListToString(contacts.phone) ?: EMPTY_STRING
            )
        }
    }

    private fun getEmployer(employer: EmployerEmbeddable?): Employer? {
        return employer?.let {
            Employer(
                id = employer.id,
                name = employer.name,
                logo = employer.logo
            )
        }
    }

    private fun getEmployer(employer: Employer?): EmployerEmbeddable? {
        return employer?.let {
            EmployerEmbeddable(
                id = employer.id,
                name = employer.name ?: EMPTY_STRING,
                logo = employer.logo ?: EMPTY_STRING
            )
        }
    }

    private fun getFilterArea(filterArea: FilterAreaEmbeddable?): FilterArea? {
        return filterArea?.let {
            FilterArea(
                id = filterArea.id,
                name = filterArea.name,
                parentId = filterArea.parentId,
                areas = getListFromString(filterArea.areas)
            )
        }
    }

    private fun getFilterArea(filterArea: FilterArea?): FilterAreaEmbeddable? {
        return filterArea?.let {
            FilterAreaEmbeddable(
                id = filterArea.id,
                name = filterArea.name ?: EMPTY_STRING,
                parentId = filterArea.parentId ?: UNKNOWN_ID,
                areas = convertListToString(filterArea.areas) ?: EMPTY_STRING
            )
        }
    }

    private fun <T> convertListToString(list: List<T>?): String? {
        return list?.let { gson.toJson(list) }
    }

    private fun <T> getListFromString(str: String): List<T>? {
        val type: Type = object : TypeToken<List<T>>() {}.type
        return try {
            gson.fromJson(str, type)
        } catch (e: JsonSyntaxException) {
            Log.e(VACANCY_CONVERTER_TAG, "Не удалось получить список из строки (json)")
            null
        }
    }

    companion object {
        private const val EMPTY_STRING = ""
        private const val UNKNOWN_ID = -1
        private const val VACANCY_CONVERTER_TAG = "VacancyConverter"
    }
}
