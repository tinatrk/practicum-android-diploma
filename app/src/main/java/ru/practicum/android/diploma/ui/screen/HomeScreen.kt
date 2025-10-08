package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(modifier: Modifier = Modifier, onVacancyClick: (String) -> Unit) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Главная",
            fontSize = 48.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.clickable {
                onVacancyClick("b4cb93e5-1266-45b1-a1dd-43d193bd0631")
            }
        )
    }
}
