package ru.practicum.android.diploma.domain.models.filters

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class FilterRegion(
    val id: Int,
    val name: String,
    val parentId: Int
) : Parcelable
