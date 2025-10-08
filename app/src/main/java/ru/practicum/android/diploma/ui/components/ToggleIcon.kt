package ru.practicum.android.diploma.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun ToggleIcon(
    isChecked: Boolean? = null,
    checkedIconId: Int,
    uncheckedIconId: Int,
    onIconClick: () -> Unit,
    iconDescription: String,
    iconUncheckedTint: Color,
    modifier: Modifier = Modifier
) {
    var checked by remember { mutableStateOf(false) }
    val currentChecked = isChecked ?: checked

    // размером 40*40 (имеет внутренние отступы 8.dp?)
    IconButton(
        onClick = onIconClick,
        modifier = modifier
    ) {
        IconToggleButton(
            checked = currentChecked,
            onCheckedChange = {
                checked = it
                onIconClick()
            },
        ) {
            if (currentChecked) {
                Icon(
                    painter = painterResource(id = checkedIconId),
                    contentDescription = iconDescription,
                    tint = Color.Unspecified
                )
            } else {
                Icon(
                    painter = painterResource(id = uncheckedIconId),
                    contentDescription = iconDescription,
                    tint = iconUncheckedTint
                )
            }
        }
    }
}
