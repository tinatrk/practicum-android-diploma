package ru.practicum.android.diploma.ui.navigation

import androidx.compose.runtime.Immutable
import okhttp3.internal.immutableListOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.navigation.util.AppNavDestination
import kotlin.reflect.KClass

@Immutable
data class BottomTab<T : Any>(
    val destination: KClass<T>,
    val iconRes: Int,
    val labelRes: Int
)

val bottomTabs = immutableListOf(
    BottomTab(
        AppNavDestination.Home::class,
        R.drawable.main_24px,
        R.string.theme_use_example_bottom_bor_label_main
    ),
    BottomTab(
        AppNavDestination.Favorite::class,
        R.drawable.favorites_24px,
        R.string.theme_use_example_bottom_bor_label_favorite
    ),
    BottomTab(
        AppNavDestination.Team::class,
        R.drawable.team_24px,
        R.string.theme_use_example_bottom_bor_label_team
    )
)
