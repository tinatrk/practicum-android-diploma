package ru.practicum.android.diploma.ui.navigation

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.navigation.bottom.BottomNavDestination
import kotlin.reflect.KClass

data class BottomTab<T : Any>(
    val destination: KClass<T>,
    val iconRes: Int,
    val labelRes: Int
)

val bottomTabs = listOf(
    BottomTab(
        BottomNavDestination.Home::class,
        R.drawable.main_24px,
        R.string.theme_use_example_bottom_bor_label_main
    ),
    BottomTab(
        BottomNavDestination.Favorite::class,
        R.drawable.favorites_24px,
        R.string.theme_use_example_bottom_bor_label_favorite
    ),
    BottomTab(
        BottomNavDestination.Team::class,
        R.drawable.team_24px,
        R.string.theme_use_example_bottom_bor_label_team
    )
)

