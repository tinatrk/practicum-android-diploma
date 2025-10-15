package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.presentation.models.VacancyBriefInfo
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography

@Composable
fun VacancyListItem(
    vacancyInfo: VacancyBriefInfo,
    onVacancyClick: (String) -> Unit
) {
    val colors = LocalCustomColors.current
    val typography = LocalTypography.current

    val vacancyTitle = remember { mutableStateOf("${vacancyInfo.name}, ${vacancyInfo.city}") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.vacancyListItemColors.background)
            .clickable { onVacancyClick(vacancyInfo.id) }
            .padding(horizontal = 16.dp, vertical = 9.dp)
            .wrapContentHeight()
    ) {
        Logo(vacancyInfo.employerLogo)
        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            Text(
                text = vacancyTitle.value,
                style = typography.vacancyListItemTitle,
                color = colors.vacancyListItemColors.title
            )
            Text(
                text = vacancyInfo.employerName,
                style = typography.vacancyListItemText,
                color = colors.vacancyListItemColors.textInfo
            )
            Text(
                text = vacancyInfo.salary,
                style = typography.vacancyListItemText,
                color = colors.vacancyListItemColors.textInfo
            )
        }
    }
}

fun getVacancyPreviewItem(): VacancyBriefInfo {
    return VacancyBriefInfo(
        id = "-1",
        name = "Андроид-разработчик",
        city = "Москва",
        employerName = "Яндекс",
        employerLogo = null,
        salary = "1000 $"
    )
}

@Preview(name = "lightTheme", showSystemUi = true)
@Composable
fun VacancyListItemPreviewLight() {
    AppTheme(darkTheme = false) {
        VacancyListItem(
            vacancyInfo = getVacancyPreviewItem(),
            onVacancyClick = {}
        )
    }
}

@Preview(name = "darkTheme", showSystemUi = true)
@Composable
fun VacancyListItemPreviewDark() {
    AppTheme(darkTheme = true) {
        VacancyListItem(
            vacancyInfo = getVacancyPreviewItem(),
            onVacancyClick = {}
        )
    }
}
