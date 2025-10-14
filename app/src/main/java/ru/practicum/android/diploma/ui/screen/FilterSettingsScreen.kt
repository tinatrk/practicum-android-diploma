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
    navigateToWorkLocation: () -> Unit,
    navigateToFilterIndustry: (Int?) -> Unit
) {
    // Сделала так для простоты, по-хорошему нужен один state
    val selectedWorkLocation = viewModel.selectedWorkAddress.collectAsState()
    val selectedIndustry = viewModel.selectedIndustry.collectAsState()

    Column {
        Button(
            onClick = {
                navigateToWorkLocation()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Перейти на экран место работы"
            )
        }
        Text(
            text = "Выбрана страна: ${
                selectedWorkLocation.value
                    ?.country?.name ?: "страна не задана"
            }",
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "Выбран регион: ${
                selectedWorkLocation.value
                    ?.region?.name ?: "регион не задан"
            }",
            modifier = Modifier.padding(top = 16.dp)
        )
        Button(
            onClick = {
                navigateToFilterIndustry(-1)
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

        Button(
            onClick = {
                viewModel.setFilterSettings()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Задать настройки фильтрации"
            )
        }

        Button(
            onClick = {
                viewModel.clearFilterSettings()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Очистить настройки фильтрации"
            )
        }
    }
}
