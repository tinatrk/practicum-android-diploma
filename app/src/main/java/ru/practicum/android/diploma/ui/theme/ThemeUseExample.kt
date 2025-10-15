package ru.practicum.android.diploma.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.ToggleIcon
import ru.practicum.android.diploma.ui.components.topbar.CustomTopBar

@Composable
fun ScreenExample() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ToolbarExample() },
        containerColor = LocalCustomColors.current.screenBackground,
        bottomBar = { BottomBarExample() }
    ) {
        Button(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LocalCustomColors.current.buttonColors.commonButtonColors.background,
                contentColor = LocalCustomColors.current.buttonColors.commonButtonColors.text
            ),
            shape = RoundedCornerShape(12.dp),
            onClick = {}
        ) {
            Text(
                text = stringResource(R.string.theme_use_example_btn_name),
                style = LocalTypography.current.buttonText
            )
        }
    }
}

@Composable
fun ToolbarExample() {
    CustomTopBar(
        title = stringResource(id = R.string.search_screen_title),
        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back_24px),
                contentDescription = stringResource(R.string.ic_arrow_back_description),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable { }
            )
        },
        actions = {
            ToggleIcon(
                checkedIconId = R.drawable.ic_favorites_on_24px,
                uncheckedIconId = R.drawable.ic_favorites_off_24px,
                iconDescription = stringResource(R.string.ic_arrow_back_description),
                onIconClick = { },
                iconUncheckedTint = LocalCustomColors.current.topBarColors.iconBaseTint,
                modifier = Modifier.padding(start = 8.dp, end = 4.dp)
            )
            ToggleIcon(
                checkedIconId = R.drawable.ic_filter_on_24px,
                uncheckedIconId = R.drawable.ic_filter_off_24px,
                iconDescription = stringResource(R.string.ic_arrow_back_description),
                onIconClick = { },
                iconUncheckedTint = LocalCustomColors.current.topBarColors.iconBaseTint,
                modifier = Modifier.padding(start = 4.dp, end = 12.dp)
            )
        }
    )
}

@Composable
fun BottomBarExample() {
    NavigationBar(
        containerColor = LocalCustomColors.current.buttonColors.resetFilterButtonColors.text,
        tonalElevation = 0.dp,
        modifier = Modifier
            .navigationBarsPadding()
            .height(56.dp)
    ) {
        NavigationBarItem(
            onClick = {},
            label = null,
            icon = {
                Column(
                    // Тут используется красный цвет (текст кнопки для сброса фильтра) для наглядности
                    modifier = Modifier.background(LocalCustomColors.current.buttonColors.resetFilterButtonColors.text),
                    verticalArrangement = Arrangement.spacedBy(0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_main_24),
                        contentDescription = stringResource(R.string.theme_use_example_image_content_description),
                    )
                    Text(
                        stringResource(R.string.theme_use_example_bottom_bor_label_main),
                        style = LocalTypography.current.bottomNavigationText,
                    )
                }
            },
            selected = true,
            colors = NavigationBarItemColors(
                selectedIconColor = LocalCustomColors.current.bottomNavigationColors.activeIconAndText,
                selectedTextColor = LocalCustomColors.current.bottomNavigationColors.activeIconAndText,
                unselectedIconColor = LocalCustomColors.current.bottomNavigationColors.inactiveIconAndText,
                unselectedTextColor = LocalCustomColors.current.bottomNavigationColors.inactiveIconAndText,
                // Не изучала, что это за состояние, не знаю, какой тут цвет нужен
                disabledIconColor = LocalCustomColors.current.bottomNavigationColors.background,
                disabledTextColor = LocalCustomColors.current.bottomNavigationColors.background,
                // Тут используется красный цвет (текст кнопки для сброса фильтра) для наглядности
                selectedIndicatorColor = LocalCustomColors.current.buttonColors.resetFilterButtonColors.text,
            )
        )

        NavigationBarItem(
            onClick = {},
            label = null,
            icon = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.background(LocalCustomColors.current.buttonColors.resetFilterButtonColors.text)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_favorites_on_24px),
                        contentDescription = stringResource(R.string.theme_use_example_image_content_description),
                    )
                    Text(
                        stringResource(R.string.theme_use_example_bottom_bor_label_favorite),
                        style = LocalTypography.current.bottomNavigationText,
                    )
                }
            },
            selected = false,
            colors = NavigationBarItemColors(
                selectedIconColor = LocalCustomColors.current.bottomNavigationColors.activeIconAndText,
                selectedTextColor = LocalCustomColors.current.bottomNavigationColors.activeIconAndText,
                unselectedIconColor = LocalCustomColors.current.bottomNavigationColors.inactiveIconAndText,
                unselectedTextColor = LocalCustomColors.current.bottomNavigationColors.inactiveIconAndText,
                // Не изучала, что это за состояние, не знаю, какой тут цвет нужен
                disabledIconColor = LocalCustomColors.current.bottomNavigationColors.background,
                disabledTextColor = LocalCustomColors.current.bottomNavigationColors.background,
                // Тут используется красный цвет (текст кнопки для сброса фильтра) для наглядности
                selectedIndicatorColor = LocalCustomColors.current.buttonColors.resetFilterButtonColors.text,
            )
        )

        NavigationBarItem(
            onClick = {},
            label = null,
            icon = {
                Column(
                    modifier = Modifier.background(LocalCustomColors.current.buttonColors.resetFilterButtonColors.text),
                    verticalArrangement = Arrangement.spacedBy(0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_team_24),
                        contentDescription = stringResource(R.string.theme_use_example_image_content_description),
                    )
                    Text(
                        stringResource(R.string.theme_use_example_bottom_bor_label_team),
                        style = LocalTypography.current.bottomNavigationText,
                    )
                }
            },
            selected = false,
            colors = NavigationBarItemColors(
                selectedIconColor = LocalCustomColors.current.bottomNavigationColors.activeIconAndText,
                selectedTextColor = LocalCustomColors.current.bottomNavigationColors.activeIconAndText,
                unselectedIconColor = LocalCustomColors.current.bottomNavigationColors.inactiveIconAndText,
                unselectedTextColor = LocalCustomColors.current.bottomNavigationColors.inactiveIconAndText,
                // Не изучала, что это за состояние, не знаю, какой тут цвет нужен
                disabledIconColor = LocalCustomColors.current.bottomNavigationColors.background,
                disabledTextColor = LocalCustomColors.current.bottomNavigationColors.background,
                // Тут используется красный цвет (текст кнопки для сброса фильтра) для наглядности
                selectedIndicatorColor = LocalCustomColors.current.buttonColors.resetFilterButtonColors.text,
            )
        )
    }
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
