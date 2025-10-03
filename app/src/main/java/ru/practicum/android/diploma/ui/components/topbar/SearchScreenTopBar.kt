package ru.practicum.android.diploma.ui.components.topbar

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.ToggleIcon
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.LocalCustomColors

@Composable
fun SearchScreenTopBar(
    onFilterIconClick: () -> Unit
) {
    CustomTopBar(
        title = stringResource(id = R.string.search_screen_title),
        actions = {
            ToggleIcon(
                checkedIconId = R.drawable.ic_filter_on_24px,
                uncheckedIconId = R.drawable.ic_filter_off_24px,
                iconDescription = stringResource(R.string.ic_arrow_back_description),
                onIconClick = onFilterIconClick,
                iconUncheckedTint = LocalCustomColors.current.topBarColors.iconBaseTint,
                modifier = Modifier.padding(end = 12.dp)
            )
        }
    )
}

@Preview("light theme", showSystemUi = true)
@Composable
fun SearchScreenTopBarPreviewLight() {
    AppTheme(darkTheme = false) {
        SearchScreenTopBar(onFilterIconClick = {})
    }
}

@Preview("dark theme", showSystemUi = true)
@Composable
fun SearchScreenTopBarPreviewDark() {
    AppTheme(darkTheme = true) {
        SearchScreenTopBar(onFilterIconClick = {})
    }
}
