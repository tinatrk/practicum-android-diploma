package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.topbar.TeamScreenTopBar
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography

@Composable
fun TeamScreen() {
    val people = stringArrayResource(R.array.persons_working)

    Scaffold(
        containerColor = LocalCustomColors.current.screenBackground,
        topBar = { TeamScreenTopBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 32.dp),
                text = stringResource(R.string.persons_title),
                color = LocalCustomColors.current.commonTextColor,
                style = LocalTypography.current.teamTitle
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(items = people, key = { it }, contentType = { it }) { fullName ->
                    Text(
                        text = fullName,
                        color = LocalCustomColors.current.commonTextColor,
                        style = LocalTypography.current.teamText
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TeamScreenPreviewLight() {
    AppTheme(darkTheme = false) {
        TeamScreen()
    }
}

@Preview
@Composable
private fun TeamScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        TeamScreen()
    }
}
