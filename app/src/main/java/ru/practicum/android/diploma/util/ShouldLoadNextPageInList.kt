package ru.practicum.android.diploma.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf

fun shouldLoadNextPageInList(listState: LazyListState): State<Boolean> {
    return derivedStateOf {
        val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        val totalItemsCount = listState.layoutInfo.totalItemsCount

        // true, если последний видимый элемент — последний в списке
        lastVisibleItemIndex != null && lastVisibleItemIndex >= totalItemsCount - 1
    }
}
