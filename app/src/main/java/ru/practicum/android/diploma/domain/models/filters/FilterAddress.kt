package ru.practicum.android.diploma.domain.models.filters

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterAddress(
    val country: FilterCountry,
    val region: FilterRegion?
) : Parcelable
