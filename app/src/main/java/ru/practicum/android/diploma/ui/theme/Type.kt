package ru.practicum.android.diploma.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val AppTypography = BaseTypography(
    title32Bold = TextStyle(
        fontFamily = ysDisplayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        letterSpacing = 0.sp
    ),
    title22Medium = TextStyle(
        fontFamily = ysDisplayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        letterSpacing = 0.sp
    ),
    body16Medium = TextStyle(
        fontFamily = ysDisplayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 0.sp
    ),
    body16Regular = TextStyle(
        fontFamily = ysDisplayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.sp
    ),
    body12Regular = TextStyle(
        fontFamily = ysDisplayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.sp
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

