package ru.practicum.android.diploma.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.ui.navigation.AppScreen
import ru.practicum.android.diploma.ui.navigation.bottomTabs
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.Blue
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography

@Composable
fun BottomNavigationBar(
    tabs: List<AppScreen>,
    currentRoute: String?,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        BottomNavigationDivider()

        NavigationBar(
            modifier = modifier
                .navigationBarsPadding()
                .height(56.dp),
            containerColor = LocalCustomColors.current.bottomNavigationColors.background,
            tonalElevation = 0.dp,
        ) {
            tabs.forEach { screen ->
                val selected: Boolean = currentRoute == screen.route

                NavigationBarItem(
                    selected = selected,
                    onClick = { onItemSelected(screen.route) },
                    icon = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(0.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(screen.iconRes),
                                contentDescription = stringResource(screen.labelRes)
                            )
                            Text(
                                text = stringResource(id = screen.labelRes),
                                style = LocalTypography.current.bottomNavigationText
                            )
                        }
                    },

                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = LocalCustomColors.current.bottomNavigationColors.activeIconAndText,
                        selectedTextColor = LocalCustomColors.current.bottomNavigationColors.activeIconAndText,
                        unselectedIconColor = LocalCustomColors.current.bottomNavigationColors.inactiveIconAndText,
                        unselectedTextColor = LocalCustomColors.current.bottomNavigationColors.inactiveIconAndText,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}

@Composable
fun BottomNavigationDivider(
    modifier: Modifier = Modifier
) {
    HorizontalDivider(
        modifier = modifier.fillMaxWidth(),
        thickness = 1.dp,
        color = LocalCustomColors.current.bottomNavigationColors.divider
    )
}

@Preview(
    name = "Light Bottom Navigation Bar",
    showSystemUi = true,
    showBackground = true,
)
@Composable
fun LightBottomNavigationBarPreview() {
    AppTheme(
        darkTheme = false
    ) {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    tabs = bottomTabs,
                    currentRoute = "Home",
                    onItemSelected = { },
                    modifier = Modifier.fillMaxWidth()
                )
            }

        ) { innerPadding ->
            Box(Modifier
                .fillMaxSize()
                .padding(innerPadding)) {
            }
        }
    }
}

@Preview(
    name = "Dark Bottom Navigation Bar",
    showSystemUi = true,
    showBackground = true,
)
@Composable
fun DarkBottomNavigationBarPreview() {
    AppTheme(
        darkTheme = true
    ) {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    tabs = bottomTabs,
                    currentRoute = "Home",
                    onItemSelected = { },
                    modifier = Modifier.fillMaxWidth()
                )
            }

        ) { innerPadding ->
            Box(Modifier
                .fillMaxSize()
                .padding(innerPadding)) {
            }
        }
    }
}
