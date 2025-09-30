package ru.practicum.android.diploma.ui.component

import android.annotation.SuppressLint
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
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import ru.practicum.android.diploma.ui.navigation.BottomTab
import ru.practicum.android.diploma.ui.navigation.bottomTabs
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography
import kotlin.reflect.KClass

@SuppressLint("RestrictedApi")
@Composable
fun BottomNavigationBar(
    tabs: List<BottomTab<out Any>>,
    currentDestination: NavDestination?,
    onItemSelected: (KClass<out Any>) -> Unit,
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
            tabs.forEach { tab ->
                val selected: Boolean = currentDestination?.hierarchy?.any {
                    it.hasRoute(tab.destination)
                } == true

                NavigationBarItem(
                    selected = selected,
                    onClick = { if (!selected) onItemSelected(tab.destination) },
                    icon = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(0.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(tab.iconRes),
                                contentDescription = stringResource(tab.labelRes)
                            )
                            Text(
                                text = stringResource(id = tab.labelRes),
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
                    currentDestination = null,
                    onItemSelected = { },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Hello World")
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
                    currentDestination = null,
                    onItemSelected = { },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Hello World")
            }
        }
    }
}
