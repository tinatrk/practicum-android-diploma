package ru.practicum.android.diploma.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.data.db.embedd.AddressEmbeddable
import ru.practicum.android.diploma.data.db.embedd.ContactsEmbeddable
import ru.practicum.android.diploma.data.db.embedd.EmployerEmbeddable
import ru.practicum.android.diploma.data.db.embedd.FilterAreaEmbeddable
import ru.practicum.android.diploma.data.db.embedd.SalaryEmbeddable

@Entity(
    tableName = "vacancy"
)
data class VacancyEntity(
    @PrimaryKey val vacancyId: String,
    val name: String?,
    val description: String?,
    @Embedded(prefix = "salary_") val salary: SalaryEmbeddable?,
    @Embedded(prefix = "address_") val address: AddressEmbeddable?,
    val experience: String?,
    val schedule: String?,
    val employment: String?,
    @Embedded(prefix = "contacts_") val contacts: ContactsEmbeddable?,
    @Embedded(prefix = "employer_") val employer: EmployerEmbeddable?,
    @Embedded(prefix = "area_") val area: FilterAreaEmbeddable?,
    val skills: String?,
    val url: String?,
    val industry: String?,
    val addedTime: Long = System.currentTimeMillis()
)
