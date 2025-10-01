package ru.practicum.android.diploma.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Stable
data class Dimens(
    val fontSize32: TextUnit = 32.sp,
    val fontSize22: TextUnit = 22.sp,
    val fontSize16: TextUnit = 16.sp,
    val fontSize12: TextUnit = 12.sp,

    val letterSpacing: TextUnit = 0.sp,

    val padding46: Dp = 46.dp,
    val padding32: Dp = 32.dp,
    val padding28: Dp = 28.dp,
    val padding24: Dp = 24.dp,
    val padding22: Dp = 22.dp,
    val padding20: Dp = 20.dp,
    val padding18: Dp = 18.dp,
    val padding16: Dp = 16.dp,
    val padding12: Dp = 12.dp,
    val padding8: Dp = 8.dp,
    val padding4: Dp = 4.dp,

    val componentSize194: Dp = 194.dp,
    val componentSize108: Dp = 108.dp,
    val componentSize80: Dp = 80.dp,
    val componentSize64: Dp = 64.dp,
    val componentSize60: Dp = 60.dp,
    val componentSize56: Dp = 56.dp,
    val componentSize48: Dp = 48.dp,
    val componentSize36: Dp = 36.dp,
    val componentSize28: Dp = 28.dp,
    val componentSize24: Dp = 24.dp,
    val componentSize22: Dp = 22.dp,
    val componentSize20: Dp = 20.dp,
    val componentSize2: Dp = 2.dp,

    val cornerRadius15: Dp = 15.dp,
    val cornerRadius12: Dp = 12.dp,
)

val dimens = Dimens()
