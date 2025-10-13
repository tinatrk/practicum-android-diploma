package ru.practicum.android.diploma.domain.models.filters

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterSettings(
    val address: FilterAddress?,
    val industry: FilterIndustry?,
    val salary: Int?,
    val onlyWithSalary: Boolean?
) : Parcelable
