package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import ru.practicum.android.diploma.presentation.models.VacancyBriefInfo
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun SimpleVacancyList(
    vacancies: ImmutableList<VacancyBriefInfo>,
    onVacancyClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {
        items(vacancies) { vacancy ->
            VacancyListItem(vacancy, onVacancyClick)
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
        VacancyList(vacancies = list.toImmutableList(), onVacancyClick = {}, Modifier.padding(top = 8.dp))
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
        VacancyList(vacancies = list.toImmutableList(), onVacancyClick = {}, Modifier.padding(top = 8.dp))
    }
}
