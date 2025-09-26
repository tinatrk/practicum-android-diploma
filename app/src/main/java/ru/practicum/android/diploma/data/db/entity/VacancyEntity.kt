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
    @Embedded(prefix = "salary") val salary: SalaryEmbeddable,
    @Embedded(prefix = "address") val address: AddressEmbeddable,
    @Embedded(prefix = "experience") val experience: ExperienceEmbeddable,
    @Embedded(prefix = "schedule") val schedule: ScheduleEmbeddable,
    @Embedded(prefix = "employment") val employment: EmploymentEmbeddable,
    @Embedded(prefix = "contacts") val contacts: ContactsEmbeddable,
    @Embedded(prefix = "employer") val employer: EmployerEmbeddable,
    @Embedded(prefix = "area") val area: FilterAreaEmbeddable,
    val skills: String?,
    val url: String?,
    @Embedded(prefix = "industry") val industry: FilterIndustryEmbeddable,
    val addedTime: Long = System.currentTimeMillis()
)
