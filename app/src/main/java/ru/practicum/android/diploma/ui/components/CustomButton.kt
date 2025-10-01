package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography

@Composable
fun CustomButton(
    text: String,
    isPositiveType: Boolean = true,
    onClick: () -> Unit
) {
    val colors =
        if (isPositiveType) LocalCustomColors.current.buttonColors.commonButtonColors
        else LocalCustomColors.current.buttonColors.resetFilterButtonColors

    Button(
        onClick = onClick,
        colors = ButtonColors(
            containerColor = colors.background,
            contentColor = colors.text,
            disabledContainerColor = colors.background,
            disabledContentColor = colors.text,

            ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Text(
            text = text,
            style = LocalTypography.current.buttonText,
            textAlign = TextAlign.Center
        )
    }
}

@Preview("positiveBtn lightTheme", showSystemUi = true)
@Composable
fun PositiveBtnPreviewLight() {
    AppTheme(darkTheme = false) {
        CustomButton(
            text = stringResource(R.string.btn_choose)
        ) { }
    }
}

@Preview("positiveBtn darkTheme", showSystemUi = true)
@Composable
fun PositiveBtnPreviewDark() {
    AppTheme(darkTheme = true) {
        CustomButton(
            text = stringResource(R.string.btn_choose)
        ) { }
    }
}

@Preview("negativeBtn lightTheme", showSystemUi = true)
@Composable
fun NegativeBtnPreviewLight() {
    AppTheme(darkTheme = false) {
        CustomButton(
            text = stringResource(R.string.btn_apply),
            isPositiveType = false
        ) { }
    }
}

@Preview("negativeBtn darkTheme", showSystemUi = true)
@Composable
fun NegativeBtnPreviewDark() {
    AppTheme(darkTheme = false) {
        CustomButton(
            text = stringResource(R.string.btn_apply),
            isPositiveType = false
        ) { }
    }
}
