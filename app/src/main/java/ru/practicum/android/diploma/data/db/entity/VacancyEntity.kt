package ru.practicum.android.diploma.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.data.db.embedd.AddressEmbeddable
import ru.practicum.android.diploma.data.db.embedd.ContactsEmbeddable
import ru.practicum.android.diploma.data.db.embedd.EmployerEmbeddable
import ru.practicum.android.diploma.data.db.embedd.EmploymentEmbeddable
import ru.practicum.android.diploma.data.db.embedd.ExperienceEmbeddable
import ru.practicum.android.diploma.data.db.embedd.FilterAreaEmbeddable
import ru.practicum.android.diploma.data.db.embedd.FilterIndustryEmbeddable
import ru.practicum.android.diploma.data.db.embedd.SalaryEmbeddable
import ru.practicum.android.diploma.data.db.embedd.ScheduleEmbeddable

@Entity(
    tableName = "vacancy"
)
data class VacancyEntity(
    @PrimaryKey val vacancyId: String,
    val name: String,
    val description: String?,
    @Embedded val salary: SalaryEmbeddable,
    @Embedded val address: AddressEmbeddable,
    @Embedded val experience: ExperienceEmbeddable,
    @Embedded val schedule: ScheduleEmbeddable,
    @Embedded val employment: EmploymentEmbeddable,
    @Embedded val contacts: ContactsEmbeddable,
    @Embedded val employer: EmployerEmbeddable,
    @Embedded val area: FilterAreaEmbeddable,
    val skills: String?,
    val url: String?,
    @Embedded val industry: FilterIndustryEmbeddable,
    val addedTime: Long = System.currentTimeMillis()
)
