package ru.practicum.android.diploma.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.models.VacancyBriefInfo
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun VacancyList(
    vacancies: ImmutableList<VacancyBriefInfo>,
    onLoadNextPage: () -> Unit,
    isNextPageError: Boolean,
    isLastPage: Boolean,
    onVacancyClick: (String) -> Unit,
) {
    val listState = rememberLazyListState()
    val context = LocalContext.current

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

    LaunchedEffect(isNextPageError) {
        if (isNextPageError) {
            Toast.makeText(
                context,
                context.getString(R.string.pagination_load_error),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
    ) {
        items(items = vacancies, key = { it.id }, contentType = { it }) { vacancy ->
            VacancyListItem(vacancy, onVacancyClick)
        }

        if (!isNextPageError && !isLastPage) {
            item {
                ProgressBar()
            }
        }
    }
}

@Preview(name = "lightTheme", showSystemUi = true)
@Composable
private fun VacancyListPreviewLight() {
    val list = listOf(
        getVacancyPreviewItem(),
        getVacancyPreviewItem(),
        getVacancyPreviewItem()
    )

    AppTheme(darkTheme = false) {
        VacancyList(
            vacancies = list.toImmutableList(),
            onLoadNextPage = {},
            isNextPageError = false,
            isLastPage = false,
            onVacancyClick = {}
        )
    }
}

@Preview(name = "darkTheme", showSystemUi = true)
@Composable
private fun VacancyListPreviewDark() {
    val list = listOf(
        getVacancyPreviewItem(),
        getVacancyPreviewItem(),
        getVacancyPreviewItem()
    )

    AppTheme(darkTheme = true) {
        VacancyList(
            vacancies = list.toImmutableList(),
            onLoadNextPage = {},
            isNextPageError = false,
            isLastPage = false,
            onVacancyClick = {}
        )
    }
}
