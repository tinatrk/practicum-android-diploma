package ru.practicum.android.diploma.ui.components.topbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun TeamScreenTopBar() {
    CustomTopBar(
        title = stringResource(id = R.string.team_screen_title),
    )
}

@Preview("light theme", showSystemUi = true)
@Composable
private fun TeamScreenTopBarPreviewLight() {
    AppTheme(darkTheme = false) {
        TeamScreenTopBar()
    }
}

@Preview("dark theme", showSystemUi = true)
@Composable
private fun TeamScreenTopBarPreviewDark() {
    AppTheme(darkTheme = true) {
        TeamScreenTopBar()
    }
}
