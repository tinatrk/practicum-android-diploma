package ru.practicum.android.diploma.domain.models.filters

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterSettings(
    val address: FilterAddress? = null,
    val industry: FilterIndustry? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean? = null
) : Parcelable
