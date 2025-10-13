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
import ru.practicum.android.diploma.presentation.filters.viewmodel.FilterIndustryViewModel

@Composable
fun FilterIndustryScreen(
    viewModel: FilterIndustryViewModel = koinViewModel(),
    selectedIndustryId: Int?,
    onFinish: () -> Unit
) {
    val shouldFinish = viewModel.shouldFinish.collectAsState(initial = false)
    if (shouldFinish.value) {
        viewModel.consumeFinishEvent()
        onFinish()
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
            text = "IndustryId $selectedIndustryId",
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
