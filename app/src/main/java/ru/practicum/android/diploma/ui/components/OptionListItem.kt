package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography

@Composable
fun OptionListItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    val colors = LocalCustomColors.current
    val typography = LocalTypography.current

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable {
                onClick()
            }
            .background(colors.filterListItemColors.addressItem.background)
            .padding(horizontal = 16.dp)

    ) {
        Text(
            text = text,
            color = colors.filterListItemColors.addressItem.text,
            style = typography.filterListItemText
        )
        Icon(
            painter = painterResource(R.drawable.ic_arrow_forward_24px),
            contentDescription = null,
            tint = colors.filterListItemColors.addressItem.text
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun LightOptionListItemPreview() {
    AppTheme(
        darkTheme = false
    ) {
        OptionListItem(
            text = "Россия",
            onClick = {}
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun DarkOptionListItemPreview() {
    AppTheme(
        darkTheme = true
    ) {
        OptionListItem(
            text = "Россия",
            onClick = {}
        )
    }
}
