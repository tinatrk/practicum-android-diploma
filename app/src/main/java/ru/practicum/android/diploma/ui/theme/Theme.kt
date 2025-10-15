package ru.practicum.android.diploma.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

// region ColorScheme
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = Tertiary,
    onTertiary = OnTertiary,
    surfaceTint = SurfaceTint
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = Tertiary,
    onTertiary = OnTertiary,
    surfaceTint = SurfaceTint
)
// endregion

// region LightCustomColors
private val LightCustomColors = CustomColors(
    screenBackground = PrimaryLight,

    commonTextColor = OnPrimaryLight,

    commonIconColor = OnPrimaryLight,

    topBarColors = TopBarColors(
        background = PrimaryLight,
        text = OnPrimaryLight,
        iconBaseTint = OnPrimaryLight,
    ),

    bottomNavigationColors = BottomNavigationColors(
        background = PrimaryLight,
        activeIconAndText = Tertiary,
        inactiveIconAndText = OnPrimaryContainer,
        divider = SecondaryContainer
    ),

    searchEditTextColors = SearchEditTextColors(
        background = SecondaryLight,
        hint = OnSecondaryLight,
        text = OnSecondaryContainer,
        cursor = Tertiary,
        iconTint = OnSecondaryContainer
    ),

    progressBarColor = Tertiary,

    searchCountResultChipColors = SearchCountResultChipColors(
        background = Tertiary,
        text = OnTertiary,
    ),

    vacancyListItemColors = VacancyListItemColors(
        background = PrimaryLight,
        title = OnPrimaryLight,
        textInfo = OnPrimaryLight,
        logoBorder = SecondaryContainer
    ),

    filterListItemColors = FilterListItemColors(
        background = PrimaryLight,
        headlineContent = OnPrimaryLight,
        overlineContent = OnPrimaryLight,
        trailingIcon = OnPrimaryLight,

        defaultFilterItem = FilterListItemStateColors(
            background = PrimaryLight,
            text = OnPrimaryContainer,
            iconTint = OnPrimaryLight,
        ),
        addressItem = FilterListItemStateColors(
            background = PrimaryLight,
            text = OnPrimaryLight,
            iconTint = OnPrimaryLight,
        ),
        userChoice = FilterListItemStateColors(
            background = PrimaryLight,
            text = OnPrimaryLight,
            iconTint = OnPrimaryLight,
        ),
        filterItemWithCheckBox = FilterListItemStateColors(
            background = PrimaryLight,
            text = OnPrimaryLight,
            iconTint = Tertiary,
        ),
    ),

    textFieldColors = TextFieldColors(
        background = SecondaryLight,
        hint = OnSecondaryLight,
        text = OnSecondaryContainer,
        labelState = TextFieldLabelStateColors(
            unfocusedAndTextEmpty = OnSecondaryLight,
            unfocusedAndTextNotEmpty = OnSecondaryContainer,
            focused = Tertiary,
        )
    ),

    vacancyInfoCard = VacancyInfoCard(
        background = SecondaryContainer,
        text = OnSecondaryContainer,
    ),

    buttonColors = ButtonColors(
        commonButtonColors = ButtonTypeColors(
            background = Tertiary,
            text = OnTertiary,
        ),
        resetFilterButtonColors = ButtonTypeColors(
            background = PrimaryLight,
            text = SurfaceTint,
        ),
    ),

    bottomSheetColors = BottomSheetColors(
        background = PrimaryLight,
        screenBlackout = Black50Alpha,
    ),

    toastColors = ToastColors(
        background = SurfaceTint,
        text = OnTertiary
    ),

    vacancyDetailsColors = VacancyDetailsColors(
        background = PrimaryLight,
        text = OnPrimaryLight
    )
)
// endregion

// region DarkCustomColors
private val DarkCustomColors = CustomColors(
    screenBackground = PrimaryDark,

    commonTextColor = OnPrimaryDark,

    commonIconColor = OnPrimaryDark,

    topBarColors = TopBarColors(
        background = PrimaryDark,
        text = OnPrimaryDark,
        iconBaseTint = OnPrimaryDark,
    ),

    bottomNavigationColors = BottomNavigationColors(
        background = PrimaryDark,
        activeIconAndText = Tertiary,
        inactiveIconAndText = OnPrimaryContainer,
        divider = SecondaryContainer
    ),

    searchEditTextColors = SearchEditTextColors(
        background = SecondaryDark,
        hint = OnSecondaryDark,
        text = OnSecondaryContainer,
        cursor = Tertiary,
        iconTint = OnSecondaryContainer
    ),

    progressBarColor = Tertiary,

    searchCountResultChipColors = SearchCountResultChipColors(
        background = Tertiary,
        text = OnTertiary,
    ),

    vacancyListItemColors = VacancyListItemColors(
        background = PrimaryDark,
        title = OnPrimaryDark,
        textInfo = OnPrimaryDark,
        logoBorder = SecondaryContainer
    ),

    filterListItemColors = FilterListItemColors(
        background = PrimaryDark,
        headlineContent = OnPrimaryDark,
        overlineContent = OnPrimaryDark,
        trailingIcon = OnPrimaryDark,
        defaultFilterItem = FilterListItemStateColors(
            background = PrimaryDark,
            text = OnPrimaryContainer,
            iconTint = OnPrimaryDark,
        ),
        addressItem = FilterListItemStateColors(
            background = PrimaryDark,
            text = OnPrimaryDark,
            iconTint = OnPrimaryDark,
        ),
        userChoice = FilterListItemStateColors(
            background = PrimaryDark,
            text = OnPrimaryDark,
            iconTint = OnPrimaryDark,
        ),
        filterItemWithCheckBox = FilterListItemStateColors(
            background = PrimaryDark,
            text = OnPrimaryDark,
            iconTint = Tertiary,
        ),
    ),

    textFieldColors = TextFieldColors(
        background = SecondaryDark,
        hint = OnSecondaryDark,
        text = OnSecondaryContainer,
        labelState = TextFieldLabelStateColors(
            unfocusedAndTextEmpty = OnSecondaryDark,
            unfocusedAndTextNotEmpty = OnSecondaryContainer,
            focused = Tertiary,
        )
    ),

    vacancyInfoCard = VacancyInfoCard(
        background = SecondaryContainer,
        text = OnSecondaryContainer,
    ),

    buttonColors = ButtonColors(
        commonButtonColors = ButtonTypeColors(
            background = Tertiary,
            text = OnTertiary,
        ),
        resetFilterButtonColors = ButtonTypeColors(
            background = PrimaryDark,
            text = SurfaceTint,
        ),
    ),

    bottomSheetColors = BottomSheetColors(
        background = PrimaryDark,
        screenBlackout = Black50Alpha,
    ),

    toastColors = ToastColors(
        background = SurfaceTint,
        text = OnTertiary
    ),

    vacancyDetailsColors = VacancyDetailsColors(
        background = PrimaryDark,
        text = OnPrimaryDark
    )
)
// endregion

// region CustomTypograpty
private val customTypography = CustomTypography(
    topBarTitle = AppTypography.title22Medium,
    bottomNavigationText = AppTypography.body12Regular,
    searchEditTextText = AppTypography.body16Regular,
    searchCountResultChipText = AppTypography.body16Regular,
    vacancyListItemTitle = AppTypography.title22Medium,
    vacancyListItemText = AppTypography.body16Regular,
    filterListItemText = AppTypography.body16Regular,
    filterListItemLabel = AppTypography.body12Regular,
    textFieldText = AppTypography.body16Regular,
    textFieldLabel = AppTypography.body12Regular,
    vacancyInfoCardTitle = AppTypography.title22Medium,
    vacancyInfoCardText = AppTypography.body16Regular,
    buttonText = AppTypography.body16Medium,
    toastText = AppTypography.body16Regular,
    errorMessageText = AppTypography.title22Medium,
    vacancyDetailBigTitle = AppTypography.title32Bold,
    vacancyDetailTitle = AppTypography.title22Medium,
    vacancyDetailSmallTitle = AppTypography.body16Medium,
    vacancyDetailText = AppTypography.body16Regular,
    teamTitle = AppTypography.title32Bold,
    teamText = AppTypography.body16Medium
)
// endregion

val LocalCustomColors = staticCompositionLocalOf<CustomColors> {
    error("No CustomColors provided")
}

val LocalTypography = staticCompositionLocalOf<CustomTypography> {
    error("No Typography provided")
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val customColors = if (darkTheme) DarkCustomColors else LightCustomColors

    CompositionLocalProvider(
        LocalCustomColors provides customColors,
        LocalTypography provides customTypography
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}
