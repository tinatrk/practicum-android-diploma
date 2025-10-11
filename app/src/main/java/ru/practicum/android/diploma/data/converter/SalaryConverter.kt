package ru.practicum.android.diploma.data.converter

import ru.practicum.android.diploma.data.db.embedd.SalaryEmbeddable
import ru.practicum.android.diploma.data.dto.models.SalaryDto
import ru.practicum.android.diploma.domain.models.Salary

class SalaryConverter {
    fun map(salaryDto: SalaryDto?): Salary? {
        return salaryDto?.let {
            Salary(
                from = it.from,
                to = it.to,
                currency = it.currency
            )
        }
    }

    fun map(salary: Salary?): SalaryEmbeddable? {
        return salary?.let {
            SalaryEmbeddable(
                from = it.from,
                to = it.to,
                currency = it.currency
            )
        }
    }

    fun map(salary: SalaryEmbeddable?): Salary? {
        return salary?.let {
            Salary(
                from = it.from,
                to = it.to,
                currency = it.currency
            )
        }
    }
}
