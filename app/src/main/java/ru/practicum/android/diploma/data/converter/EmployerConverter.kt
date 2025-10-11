package ru.practicum.android.diploma.data.converter

import ru.practicum.android.diploma.data.db.embedd.EmployerEmbeddable
import ru.practicum.android.diploma.data.dto.models.EmployerDto
import ru.practicum.android.diploma.domain.models.vacancy.Employer

class EmployerConverter {
    fun map(employerDto: EmployerDto?): Employer? {
        return employerDto?.let {
            Employer(
                id = it.id,
                name = it.name,
                logo = it.logo
            )
        }
    }

    fun map(employer: EmployerEmbeddable?): Employer? {
        return employer?.let {
            Employer(
                id = employer.id,
                name = employer.name,
                logo = employer.logo
            )
        }
    }

    fun map(employer: Employer?): EmployerEmbeddable? {
        return employer?.let {
            EmployerEmbeddable(
                id = employer.id,
                name = employer.name ?: EMPTY_STRING,
                logo = employer.logo ?: EMPTY_STRING
            )
        }
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}
