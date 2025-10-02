package ru.practicum.android.diploma.ui.components.topbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun SimpleTopBar(
    title: String
) {
    CustomTopBar(
        title = title,
    )
}

@Preview("light theme", showSystemUi = true)
@Composable
fun SimpleTopBarPreviewLight() {
    AppTheme(darkTheme = false) {
        SimpleTopBar(stringResource(R.string.favorites_screen_title))
    }
}

@Preview("dark theme", showSystemUi = true)
@Composable
fun SimpleTopBarPreviewDark() {
    AppTheme(darkTheme = true) {
        SimpleTopBar(stringResource(R.string.favorites_screen_title))
    }
}
