package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import ru.practicum.android.diploma.presentation.models.VacancyBriefInfo
import ru.practicum.android.diploma.ui.navigation.util.DetailsSource
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun SimpleVacancyList(
    vacancies: ImmutableList<VacancyBriefInfo>,
    source: DetailsSource,
    onVacancyClick: (String, DetailsSource) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {
        items(
            items = vacancies,
            key = { it.id },
            contentType = { it },
        ) { vacancy ->
            VacancyListItem(vacancy, source, onVacancyClick)
        }
    }
}

@Preview(name = "lightTheme", showSystemUi = true)
@Composable
private fun SimpleVacancyListPreviewLight() {
    val list = listOf(
        getVacancyPreviewItem(),
        getVacancyPreviewItem(),
        getVacancyPreviewItem()
    )

    AppTheme(darkTheme = false) {
        VacancyList(
            vacancies = list.toImmutableList(),
            source = DetailsSource.SEARCH,
            onVacancyClick = { _, _ -> },
            onLoadNextPage = {},
            isNextPageError = false,
            isLastPage = true
        )
    }
}

@Preview(name = "darkTheme", showSystemUi = true)
@Composable
private fun SimpleVacancyListPreviewDark() {
    val list = listOf(
        getVacancyPreviewItem(),
        getVacancyPreviewItem(),
        getVacancyPreviewItem()
    )

    AppTheme(darkTheme = true) {
        VacancyList(
            vacancies = list.toImmutableList(),
            onVacancyClick = { _, _ -> },
            source = DetailsSource.SEARCH,
            onLoadNextPage = {},
            isNextPageError = false,
            isLastPage = true
        )
    }
}
