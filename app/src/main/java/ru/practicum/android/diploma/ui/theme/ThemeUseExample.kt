package ru.practicum.android.diploma.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R

@Composable
fun ScreenExample() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ToolbarExample() },
        containerColor = LocalCustomColors.current.screenBackground
    ) {
        Button(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = dimens.padding16)
                .fillMaxWidth()
                .height(dimens.componentSize60),
            colors = ButtonDefaults.buttonColors(
                containerColor = LocalCustomColors.current.buttonColors.commonButtonColors.background,
                contentColor = LocalCustomColors.current.buttonColors.commonButtonColors.text
            ),
            shape = RoundedCornerShape(dimens.cornerRadius12),
            onClick = {}
        ) {
            Text(
                text = stringResource(R.string.theme_use_example_btn_name),
                style = LocalTypography.current.buttonText
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarExample() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.theme_use_example_topbar_title),
                style = LocalTypography.current.topBarTitle
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LocalCustomColors.current.topBarColors.background,
            titleContentColor = LocalCustomColors.current.topBarColors.text,
            navigationIconContentColor = LocalCustomColors.current.topBarColors.iconType.commonIconTint
        ),
    )
}

@ExperimentalMaterial3Api
@Preview(name = "lightTheme", showSystemUi = true)
@Composable
fun ThemeUseExampleLight() {
    AppTheme(darkTheme = false) {
        ScreenExample()
    }
}

@ExperimentalMaterial3Api
@Preview(name = "darkTheme", showSystemUi = true)
@Composable
fun ThemeUseExampleDark() {
    AppTheme(darkTheme = true) {
        ScreenExample()
    }
}
