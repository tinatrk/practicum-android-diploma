package ru.practicum.android.diploma.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

val AppTypography = BaseTypography(
    title32Bold = TextStyle(
        fontFamily = ysDisplayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = dimens.fontSize32,
        letterSpacing = dimens.letterSpacing
    ),
    title22Medium = TextStyle(
        fontFamily = ysDisplayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = dimens.fontSize22,
        letterSpacing = dimens.letterSpacing
    ),
    body16Medium = TextStyle(
        fontFamily = ysDisplayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = dimens.fontSize16,
        letterSpacing = dimens.letterSpacing
    ),
    body16Regular = TextStyle(
        fontFamily = ysDisplayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = dimens.fontSize16,
        letterSpacing = dimens.letterSpacing
    ),
    body12Regular = TextStyle(
        fontFamily = ysDisplayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = dimens.fontSize16,
        letterSpacing = dimens.letterSpacing
    ),
)


data class BaseTypography(
    val title32Bold: TextStyle,
    val title22Medium: TextStyle,
    val body16Medium: TextStyle,
    val body16Regular: TextStyle,
    val body12Regular: TextStyle
)

data class CustomTypography(
    val topBarTitle: TextStyle,
    val bottomNavigationText: TextStyle,
    val searchEditTextText: TextStyle,
    val searchCountResultChipText: TextStyle,
    val vacancyListItemTitle: TextStyle,
    val vacancyListItemText: TextStyle,
    val filterListItemText: TextStyle,
    val filterListItemLabel: TextStyle,
    val textFieldText: TextStyle,
    val textFieldLabel: TextStyle,
    val vacancyInfoCardTitle: TextStyle,
    val vacancyInfoCardText: TextStyle,
    val buttonText: TextStyle,
    val toastText: TextStyle,
    val errorMessageText: TextStyle,
    val vacancyDetailBigTitle: TextStyle,
    val vacancyDetailTitle: TextStyle,
    val vacancyDetailSmallTitle: TextStyle,
    val vacancyDetailText: TextStyle,
    val teamTitle: TextStyle,
    val teamText: TextStyle
)

