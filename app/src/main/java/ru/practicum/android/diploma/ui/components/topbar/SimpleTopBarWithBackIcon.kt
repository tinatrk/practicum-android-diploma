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
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun SimpleTopBarWithBackIcon(
    title: String,
    onNavigationIconClick: () -> Unit
) {
    CustomTopBar(
        title = title,
        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back_24px),
                contentDescription = stringResource(R.string.ic_arrow_back_description),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable { onNavigationIconClick() }
            )
        },
    )
}

@Preview("light theme", showSystemUi = true)
@Composable
private fun SimpleTopBarWithBackIconPreviewLight() {
    AppTheme(darkTheme = false) {
        SimpleTopBarWithBackIcon(stringResource(R.string.filter_settings_screen_title), onNavigationIconClick = {})
    }
}

@Preview("dark theme", showSystemUi = true)
@Composable
private fun SimpleTopBarWithBackIconPreviewDark() {
    AppTheme(darkTheme = true) {
        SimpleTopBarWithBackIcon(stringResource(R.string.filter_settings_screen_title), onNavigationIconClick = {})
    }
}
