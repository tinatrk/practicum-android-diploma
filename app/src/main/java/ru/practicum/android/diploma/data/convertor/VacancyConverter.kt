package ru.practicum.android.diploma.data.convertor

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

class VacancyConverter {

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
            area = mapArea(vacancyDetailsResponse.area),
            skills = vacancyDetailsResponse.skills,
            url = vacancyDetailsResponse.url,
            industry = vacancyDetailsResponse.industry?.name,
            isFavorite = false
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

    private fun mapEmployer(employerDto: EmployerDto?): Employer? {
        return employerDto?.let {
            Employer(
                id = it.id,
                name = it.name,
                logo = it.logo
            )
        }
    }

    private fun mapArea(areaDto: FilterAreaDto?): FilterArea? {
        return areaDto?.let {
            FilterArea(
                id = it.id,
                name = it.name,
                parentId = it.parentId,
                areas = it.areas?.mapNotNull { areaDto ->
                    mapArea(areaDto)
                }
            )
        }
    }
}
