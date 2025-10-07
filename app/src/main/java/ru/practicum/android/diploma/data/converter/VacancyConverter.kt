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
import ru.practicum.android.diploma.data.dto.models.AddressDto
import ru.practicum.android.diploma.data.dto.models.ContactsDto
import ru.practicum.android.diploma.data.dto.models.EmployerDto
import ru.practicum.android.diploma.data.dto.models.FilterAreaDto
import ru.practicum.android.diploma.data.dto.models.SalaryDto
import ru.practicum.android.diploma.data.dto.response.VacancyDetailResponse
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

    fun map(vacancyDetailsResponse: VacancyDetailResponse): Vacancy {
        return Vacancy(
            id = vacancyDetailsResponse.id,
            name = vacancyDetailsResponse.name,
            description = vacancyDetailsResponse.description,
            salary = mapSalary(vacancyDetailsResponse.salary),
            address = mapAddress(vacancyDetailsResponse.address),
            experience = vacancyDetailsResponse.experience?.name,
            schedule = vacancyDetailsResponse.schedule?.name,
            employment = vacancyDetailsResponse.employment?.name,
            contacts = mapContacts(vacancyDetailsResponse.contacts),
            employer = mapEmployer(vacancyDetailsResponse.employer),
            area = mapFilterArea(vacancyDetailsResponse.area),
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
            salary = mapSalary(vacancy.salary),
            address = mapAddress(vacancy.address),
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            employment = vacancy.employment,
            contacts = mapContacts(vacancy.contacts),
            employer = mapEmployer(vacancy.employer),
            area = mapFilterArea(vacancy.area),
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
            salary = mapSalary(vacancy.salary),
            address = mapAddress(vacancy.address),
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            employment = vacancy.employment,
            contacts = mapContacts(vacancy.contacts),
            employer = mapEmployer(vacancy.employer),
            area = mapFilterArea(vacancy.area),
            skills = vacancy.skills?.let { getListFromString(it) },
            url = vacancy.url,
            industry = vacancy.industry,
            isFavorite = true
        )
    }

    private fun mapSalary(salaryDto: SalaryDto?): Salary? {
        return salaryDto?.let {
            Salary(
                from = it.from,
                to = it.to,
                currency = it.currency
            )
        }
    }

    private fun mapSalary(salary: Salary?): SalaryEmbeddable? {
        return salary?.let {
            SalaryEmbeddable(
                from = it.from,
                to = it.to,
                currency = it.currency
            )
        }
    }

    private fun mapSalary(salary: SalaryEmbeddable?): Salary? {
        return salary?.let {
            Salary(
                from = it.from,
                to = it.to,
                currency = it.currency
            )
        }
    }

    private fun mapAddress(addressDto: AddressDto?): Address? {
        return addressDto?.let {
            Address(
                city = it.city,
                street = it.street,
                building = it.building,
                fullAddress = it.fullAddress
            )
        }
    }

    private fun mapAddress(address: Address?): AddressEmbeddable? {
        return address?.let {
            AddressEmbeddable(
                city = address.city ?: EMPTY_STRING,
                street = address.street ?: EMPTY_STRING,
                building = address.building ?: EMPTY_STRING,
                fullAddress = address.fullAddress ?: EMPTY_STRING
            )
        }
    }

    private fun mapAddress(address: AddressEmbeddable?): Address? {
        return address?.let {
            Address(
                city = address.city,
                street = address.street,
                building = address.building,
                fullAddress = address.fullAddress
            )
        }
    }

    private fun mapContacts(contactsDto: ContactsDto?): Contacts? {
        return contactsDto?.let {
            Contacts(
                id = it.id,
                name = it.name,
                email = it.email,
                phone = it.phone
            )
        }
    }

    private fun mapContacts(contacts: ContactsEmbeddable?): Contacts? {
        return contacts?.let {
            Contacts(
                id = contacts.id,
                name = contacts.name,
                email = contacts.email,
                phone = getListFromString(contacts.phone)
            )
        }
    }

    private fun mapContacts(contacts: Contacts?): ContactsEmbeddable? {
        return contacts?.let {
            ContactsEmbeddable(
                id = contacts.id,
                name = contacts.name ?: EMPTY_STRING,
                email = contacts.email ?: EMPTY_STRING,
                phone = convertListToString(contacts.phone) ?: EMPTY_STRING
            )
        }
    }

    private fun mapEmployer(employerDto: EmployerDto?): Employer? {
        return employerDto?.let {
            Employer(
                id = it.id,
                name = it.name,
                logo = it.logo
            )
        }
    }

    private fun mapEmployer(employer: EmployerEmbeddable?): Employer? {
        return employer?.let {
            Employer(
                id = employer.id,
                name = employer.name,
                logo = employer.logo
            )
        }
    }

    private fun mapEmployer(employer: Employer?): EmployerEmbeddable? {
        return employer?.let {
            EmployerEmbeddable(
                id = employer.id,
                name = employer.name ?: EMPTY_STRING,
                logo = employer.logo ?: EMPTY_STRING
            )
        }
    }

    private fun mapFilterArea(areaDto: FilterAreaDto?): FilterArea? {
        return areaDto?.let {
            FilterArea(
                id = it.id,
                name = it.name,
                parentId = it.parentId,
                areas = it.areas?.mapNotNull { areaDto ->
                    mapFilterArea(areaDto)
                }
            )
        }
    }

    private fun mapFilterArea(filterArea: FilterAreaEmbeddable?): FilterArea? {
        return filterArea?.let {
            FilterArea(
                id = filterArea.id,
                name = filterArea.name,
                parentId = filterArea.parentId,
                areas = getListFromString(filterArea.areas)
            )
        }
    }

    private fun mapFilterArea(filterArea: FilterArea?): FilterAreaEmbeddable? {
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
