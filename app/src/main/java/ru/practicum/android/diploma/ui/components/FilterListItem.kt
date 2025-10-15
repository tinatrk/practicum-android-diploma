package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.topbar.SimpleTopBarWithBackIcon
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.Gray
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography

@Composable
fun FilterListItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    onClearSelected: () -> Unit = {}
) {
    val colors = LocalCustomColors.current
    val typography = LocalTypography.current

    ListItem(
        headlineContent = {
            Text(
                text = value,
                style = typography.filterListItemText
            )
        },
        modifier = modifier
            .height(60.dp)
            .clickable(onClick = onClick),
        overlineContent = {
            if (isSelected) {
                Text(
                    text = label,
                    style = typography.filterListItemLabel
                )
            }
        },
        trailingContent = {
            if (isSelected) {
                Icon(
                    painter = painterResource(R.drawable.ic_close_24px),
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = onClearSelected)
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_forward_24px),
                    contentDescription = null
                )
            }
        },
        colors = ListItemDefaults.colors(
            containerColor = colors.filterListItemColors.background,
            headlineColor = if (isSelected) colors.filterListItemColors.headlineContent else Gray,
            overlineColor = colors.filterListItemColors.overlineContent,
            trailingIconColor = colors.filterListItemColors.trailingIcon,
        )
    )
}

@Preview(
    name = "Light list Item",
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun LightFilterListItemPreview() {
    AppTheme(
        darkTheme = false
    ) {
        Scaffold(
            topBar = { SimpleTopBarWithBackIcon("Выбор места работы") { } }
        ) { innerPadding ->
            FilterListItem(
                modifier = Modifier.padding(innerPadding),
                label = "Страна",
                value = "Россия",
                isSelected = true,
                onClick = {},
                onClearSelected = {}
            )
        }
    }
}

@Preview(
    name = "Dark list Item",
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun DarkFilterListItemPreview() {
    AppTheme(
        darkTheme = true
    ) {
        Scaffold(
            topBar = { SimpleTopBarWithBackIcon("Выбор места работы") { } }
        ) { innerPadding ->
            FilterListItem(
                modifier = Modifier.padding(innerPadding),
                label = "Страна",
                value = "Россия",
                isSelected = true,
                onClick = {},
                onClearSelected = {}
            )
        }
    }
}
