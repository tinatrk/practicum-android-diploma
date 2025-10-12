package ru.practicum.android.diploma.domain.models.filters

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

//@Serializable
@Parcelize
data class FilterIndustry(
    val id: Int,
    val name: String
) : Parcelable
