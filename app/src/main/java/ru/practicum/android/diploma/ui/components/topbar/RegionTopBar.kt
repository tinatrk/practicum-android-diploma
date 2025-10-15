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
fun RegionTopBar(
    onNavigationIconClick: () -> Unit,
) {
    CustomTopBar(
        title = stringResource(id = R.string.filter_region_title),
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

@Preview(showSystemUi = true)
@Composable
private fun RegionTopBarPreviewLight() {
    AppTheme(darkTheme = false) {
        RegionTopBar(
            onNavigationIconClick = {},
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RegionTopBarPreviewDark() {
    AppTheme(darkTheme = true) {
        RegionTopBar(
            onNavigationIconClick = {},
        )
    }
}
