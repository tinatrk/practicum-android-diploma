package ru.practicum.android.diploma.ui.navigation

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.navigation.favorites.Favorite
import ru.practicum.android.diploma.ui.navigation.home.Home
import ru.practicum.android.diploma.ui.navigation.team.Team
import kotlin.reflect.KClass

data class BottomTab<T : Any>(
    val destination: KClass<T>,
    val iconRes: Int,
    val labelRes: Int
)

val bottomTabs = listOf(
    BottomTab(
        Home::class,
        R.drawable.main_24px,
        R.string.theme_use_example_bottom_bor_label_main
    ),
    BottomTab(
        Favorite::class,
        R.drawable.favorites_24px,
        R.string.theme_use_example_bottom_bor_label_favorite
    ),
    BottomTab(
        Team::class,
        R.drawable.team_24px,
        R.string.theme_use_example_bottom_bor_label_team
    )
)

