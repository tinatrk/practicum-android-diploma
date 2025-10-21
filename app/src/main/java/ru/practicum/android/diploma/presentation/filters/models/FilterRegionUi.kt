package ru.practicum.android.diploma.presentation.filters.models

import androidx.compose.runtime.Immutable

@Immutable
data class FilterRegionUi(
    val id: Int,
    val name: String,
    val parentId: Int
)
