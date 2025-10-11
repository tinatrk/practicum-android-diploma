package ru.practicum.android.diploma.ui.components.topbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,
    navigationIcon:
    @Composable
    () -> Unit = {},
    actions:
    @Composable()
    (RowScope.() -> Unit) = {},
) {
    val colors = LocalCustomColors.current
    val typography = LocalTypography.current

    TopAppBar(
        modifier = Modifier
            .statusBarsPadding(),

        title = {
            Text(
                text = title,
                style = typography.topBarTitle,
            )
        },

        navigationIcon = {
            navigationIcon()
        },

        actions = {
            actions()
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colors.topBarColors.background,
            titleContentColor = colors.topBarColors.text,
            navigationIconContentColor = colors.topBarColors.iconBaseTint,
            actionIconContentColor = Color.Unspecified
        )
    )
}
