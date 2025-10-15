package ru.practicum.android.diploma.domain.models.filters

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterCountry(
    val id: Int,
    val name: String
) : Parcelable
