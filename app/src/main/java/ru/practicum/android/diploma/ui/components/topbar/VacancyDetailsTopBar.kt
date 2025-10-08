package ru.practicum.android.diploma.ui.components.topbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.ToggleIcon
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.LocalCustomColors

@Composable
fun VacancyDetailsTopBar(
    isFavorite: Boolean,
    onNavigationIconClick: () -> Unit,
    onShareIconClick: () -> Unit,
    onFavoriteIconClick: () -> Unit,
) {
    CustomTopBar(
        title = stringResource(id = R.string.vacancy_details_screen_title),
        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back_24px),
                contentDescription = stringResource(R.string.ic_arrow_back_description),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable { onNavigationIconClick() }
            )
        },
        actions = {
            Icon(
                painter = painterResource(R.drawable.ic_sharing_24px),
                contentDescription = stringResource(R.string.ic_share_description),
                tint = LocalCustomColors.current.topBarColors.iconBaseTint,
                modifier = Modifier
                    .padding(start = 8.dp, end = 4.dp)
                    .clickable { onShareIconClick() }
            )
            ToggleIcon(
                isChecked = isFavorite,
                checkedIconId = R.drawable.ic_favorites_on_24px,
                uncheckedIconId = R.drawable.ic_favorites_off_24px,
                iconDescription = stringResource(R.string.ic_arrow_back_description),
                onIconClick = onFavoriteIconClick,
                iconUncheckedTint = LocalCustomColors.current.topBarColors.iconBaseTint,
                modifier = Modifier.padding(start = 4.dp, end = 12.dp)
            )
        }
    )
}

@Preview("light theme", showSystemUi = true)
@Composable
fun VacancyDetailsTopBarPreviewLight() {
    AppTheme(darkTheme = false) {
        VacancyDetailsTopBar(
            isFavorite = false,
            onNavigationIconClick = {},
            onShareIconClick = {},
            onFavoriteIconClick = {}
        )
    }
}

@Preview("dark theme", showSystemUi = true)
@Composable
fun VacancyDetailsTopBarPreviewDark() {
    AppTheme(darkTheme = true) {
        VacancyDetailsTopBar(
            isFavorite = true,
            onNavigationIconClick = {},
            onShareIconClick = {},
            onFavoriteIconClick = {}
        )
    }
}
