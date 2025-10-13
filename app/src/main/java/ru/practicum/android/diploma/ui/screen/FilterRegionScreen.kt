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
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.presentation.filters.viewmodel.FilterRegionViewModel

@Composable
fun FilterRegionScreen(
    selectedCountryId: Int?,
    viewModel: FilterRegionViewModel = koinViewModel() {
        parametersOf(selectedCountryId)
    },
    navigateBack: () -> Unit
) {
    val shouldFinish = viewModel.shouldFinish.collectAsState(initial = false)
    if (shouldFinish.value) {
        viewModel.consumeFinishEvent()
        navigateBack()
    }

    Column {
        Button(
            onClick = {
                viewModel.onReturnWithParam()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Вернуться на предыдущий экран с параметром"
            )
        }

        Button(
            onClick = {
                viewModel.onReturnWithoutParam()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Вернуться на предыдущий экран без параметра"
            )
        }

        Text(
            text = "Selected country id ${selectedCountryId ?: "Страна не задана"}",
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
