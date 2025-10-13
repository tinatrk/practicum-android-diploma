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
import ru.practicum.android.diploma.domain.models.filters.FilterAddress
import ru.practicum.android.diploma.domain.models.filters.FilterCountry
import ru.practicum.android.diploma.domain.models.filters.FilterRegion
import ru.practicum.android.diploma.presentation.filters.viewmodel.WorkLocationViewModel

@Composable
fun WorkLocationScreen(
    viewModel: WorkLocationViewModel = koinViewModel(),
    navigateToFilterCountry: () -> Unit,
    navigateToFilterRegion: (Int?) -> Unit,
    navigateBack: () -> Unit
) {
    // Сделала так для простоты, по-хорошему нужен один state
    val selectedCountry = viewModel.selectedCountry.collectAsState()
    val selectedRegion = viewModel.selectedRegion.collectAsState()

    val shouldFinish = viewModel.shouldFinish.collectAsState(initial = false)
    if (shouldFinish.value) {
        viewModel.consumeFinishEvent()
        navigateBack()
    }

    Column {
        Button(
            onClick = {
                navigateToFilterCountry()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Перейти на экран страны"
            )
        }

        Button(
            onClick = {
                navigateToFilterRegion(1)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Перейти на экран региона с заданной странной"
            )
        }

        Button(
            onClick = {
                navigateToFilterRegion(null)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Перейти на экран региона без заданной странны"
            )
        }

        Text(
            text = "Выбрана страна: ${
                selectedCountry.value
                    ?.name ?: "страна не задана"
            }",
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = "Выбран регион: ${
                selectedRegion.value
                    ?.name ?: "регион не задан"
            }",
            modifier = Modifier.padding(top = 16.dp)
        )

        Button(
            onClick = {
                viewModel.onReturnWithParam(
                    FilterAddress(
                        country = FilterCountry(id = 1, name = "Испания")
                    )
                )
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Вернуться на предыдущий экран с параметром страны"
            )
        }

        Button(
            onClick = {
                viewModel.onReturnWithParam(
                    FilterAddress(
                        country = FilterCountry(id = 1, name = "Россия"),
                        region = FilterRegion(id = 2, name = "Нижегородская область", parentId = 1)
                    )
                )
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Вернуться на предыдущий экран с параметром страны и региона"
            )
        }

        Button(
            onClick = {
                viewModel.onReturnWithoutParam()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Вернуться на предыдущий экран без параметров"
            )
        }
    }
}
