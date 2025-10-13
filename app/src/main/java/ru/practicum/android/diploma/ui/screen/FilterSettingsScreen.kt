package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.presentation.filters.viewmodel.FilterSettingsViewModel

@Composable
fun FilterSettingsScreen(
    viewModel: FilterSettingsViewModel = koinViewModel(),
    navigateToFilterIndustry: () -> Unit
) {
    val selectedIndustry = viewModel.selectedIndustry.collectAsState()

    Column {
        Button(
            onClick = {
                navigateToFilterIndustry()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Перейти на экран отрасли"
            )
        }

        Text(
            text = "Выбрана отрасль: ${
                selectedIndustry.value
                    ?.name ?: "отрасль не задана"
            }",
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
