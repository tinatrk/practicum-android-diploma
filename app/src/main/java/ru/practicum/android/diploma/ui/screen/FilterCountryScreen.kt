package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filters.models.CountryUiState
import ru.practicum.android.diploma.presentation.filters.viewmodel.FilterCountryViewModel
import ru.practicum.android.diploma.ui.components.OptionListItem
import ru.practicum.android.diploma.ui.components.ProgressBar
import ru.practicum.android.diploma.ui.components.ScreenMessage
import ru.practicum.android.diploma.ui.components.topbar.CountryTopBar
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.util.common.Failure

@Composable
fun FilterCountryScreen(
    viewModel: FilterCountryViewModel = koinViewModel(),
    navigateBack: () -> Unit
) {
    ObserveFinish(viewModel, navigateBack)

    val state by viewModel.countryUiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = LocalCustomColors.current.screenBackground,
        topBar = { CountryTopBar { viewModel.onReturnWithoutParam() } }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CountryStateContent(
                    state = state,
                    onSelect = { id, name ->
                        viewModel.onReturnWithParam(id = id, name = name)
                    }
                )
            }
        }
    }
}

@Composable
private fun ObserveFinish(
    viewModel: FilterCountryViewModel,
    navigateBack: () -> Unit
) {
    val shouldFinish by viewModel.shouldFinish.collectAsState(initial = false)
    androidx.compose.runtime.LaunchedEffect(shouldFinish) {
        if (shouldFinish) {
            viewModel.consumeFinishEvent()
            navigateBack()
        }
    }
}

@Composable
private fun CountryStateContent(
    state: CountryUiState,
    onSelect: (id: Int, name: String) -> Unit
) {
    when (state) {
        CountryUiState.Loading -> ProgressBar()

        is CountryUiState.Success -> {
            val countries = state.data
            if (countries.isEmpty()) {
                ScreenMessage(
                    title = stringResource(R.string.country_not_found),
                    imageId = R.drawable.im_lack_of_list_cat
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(items = countries, key = { it.id }) { country ->
                        OptionListItem(
                            text = country.name,
                            onClick = { onSelect(country.id, country.name) }
                        )
                    }
                }
            }
        }

        is CountryUiState.Error -> FailureMessage(state.failure)
    }
}

@Composable
private fun FailureMessage(failure: Failure) {
    val (titleRes, imageRes) = when (failure) {
        Failure.Network -> R.string.im_bad_connection_description to R.drawable.im_bad_connection
        is Failure.Unknown -> R.string.unknown_error to R.drawable.im_server_error_android
        else -> R.string.country_request_error to R.drawable.im_lack_of_list_android
    }
    ScreenMessage(title = stringResource(titleRes), imageId = imageRes)
}
