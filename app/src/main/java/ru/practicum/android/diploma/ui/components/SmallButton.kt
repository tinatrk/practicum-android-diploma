package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography

@Composable
fun SmallButton(
    title: String,
    onClick: () -> Unit,
) {
    val colors = LocalCustomColors.current.buttonColors.smallButtonColors
    Button(
        onClick = onClick,
        colors = ButtonColors(
            containerColor = colors.background,
            contentColor = colors.text,
            disabledContainerColor = colors.background,
            disabledContentColor = colors.text,
        ),
        modifier = Modifier
            .padding(4.dp)
            .wrapContentSize()
    ) {
        Text(
            text = title,
            style = LocalTypography.current.buttonText,
        )
    }
}
