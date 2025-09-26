package ru.practicum.android.diploma.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

val AppTypography = CustomTypography(
    title32Bold = TextStyle(
        fontFamily = ysDisplayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = FontSize32,
        letterSpacing = LetterSpacing
    ),
    title22Medium = TextStyle(
        fontFamily = ysDisplayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = FontSize22,
        letterSpacing = LetterSpacing
    ),
    body16Medium = TextStyle(
        fontFamily = ysDisplayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = FontSize16,
        letterSpacing = LetterSpacing
    ),
    body16Regular = TextStyle(
        fontFamily = ysDisplayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize16,
        letterSpacing = LetterSpacing
    ),
    body12Regular = TextStyle(
        fontFamily = ysDisplayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize12,
        letterSpacing = LetterSpacing
    ),
)


data class CustomTypography(
    val title32Bold: TextStyle,
    val title22Medium: TextStyle,
    val body16Medium: TextStyle,
    val body16Regular: TextStyle,
    val body12Regular: TextStyle
)
