package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun VacancyList(
    // заменить VacancyPreviewInfo на реальную модель, описывающую элемент списка вакансий на уровне UI слоя
    vacancies: ImmutableList<VacancyPreviewInfo>,
    onVacancyClick: (VacancyPreviewInfo) -> Unit,
    topPadding: Dp
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(top = topPadding)
    ) {
        items(vacancies) { vacancy ->
            VacancyListItem(vacancy, onVacancyClick)
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
        VacancyList(vacancies = list.toImmutableList(), onVacancyClick = {}, topPadding = 8.dp)
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
        VacancyList(vacancies = list.toImmutableList(), onVacancyClick = {}, topPadding = 8.dp)
    }
}
