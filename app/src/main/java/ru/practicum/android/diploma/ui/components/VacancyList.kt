package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import ru.practicum.android.diploma.domain.models.VacancyBrief
import ru.practicum.android.diploma.domain.models.VacancyBrief
import ru.practicum.android.diploma.presentation.models.VacancyBriefInfo
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun VacancyList(
    vacancies: ImmutableList<VacancyBriefInfo>,
    onVacancyClick: (VacancyBriefInfo) -> Unit, // у Тани String. Возможно нужен Int
    onLoadNextPage: () -> Unit,
    isLastPage: Boolean
) {
    val listState = rememberLazyListState()

    val shouldLoadNext = remember {
        derivedStateOf {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val totalItemsCount = listState.layoutInfo.totalItemsCount

            // true, если последний видимый элемент — последний в списке
            lastVisibleItemIndex != null && lastVisibleItemIndex >= totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadNext.value) {
        if (shouldLoadNext.value) {
            onLoadNextPage()
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
    ) {
        items(vacancies) { vacancy ->
            VacancyListItem(vacancy, onVacancyClick)
        }
        if (!isLastPage) {
            item {
                ProgressBar()
            }
        }
    }
}

@Preview(name = "lightTheme", showSystemUi = true)
@Composable
fun VacancyListPreviewLight() {
    val list = listOf(
        getVacancyPreviewItem(),
        getVacancyPreviewItem(),
        getVacancyPreviewItem()
    )

    AppTheme(darkTheme = false) {
        VacancyList(vacancies = list.toImmutableList(), onVacancyClick = {}, {}, false)
    }
}

@Preview(name = "darkTheme", showSystemUi = true)
@Composable
fun VacancyListPreviewDark() {
    val list = listOf(
        getVacancyPreviewItem(),
        getVacancyPreviewItem(),
        getVacancyPreviewItem()
    )

    AppTheme(darkTheme = true) {
        VacancyList(vacancies = list.toImmutableList(), onVacancyClick = {}, {}, false)
    }
}
