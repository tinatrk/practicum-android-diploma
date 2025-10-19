package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography

@Composable
fun ErrorMessage(title: String, imageId: Int, modifier: Modifier) {
    Column(
        modifier = modifier.background(LocalCustomColors.current.screenBackground),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = title,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = title,
            style = LocalTypography.current.errorMessageText,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp),
            textAlign = TextAlign.Center,
            color = LocalCustomColors.current.commonTextColor
        )
    }
}

@Preview(name = "light theme", showSystemUi = true)
@Composable
private fun ErrorMessagePreviewLight() {
    AppTheme(darkTheme = false) {
        ErrorMessage(
            title = stringResource(R.string.im_bad_connection_description),
            imageId = R.drawable.im_bad_connection,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(name = "dark theme", showSystemUi = true)
@Composable
private fun ErrorMessagePreviewDark() {
    AppTheme(darkTheme = true) {
        ErrorMessage(
            title = stringResource(R.string.im_bad_connection_description),
            imageId = R.drawable.im_bad_connection,
            modifier = Modifier.fillMaxSize()
        )
    }
}
