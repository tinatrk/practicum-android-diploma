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
    val shouldFinish = viewModel.shouldFinish.collectAsState(initial = false)
    if (shouldFinish.value) {
        viewModel.consumeFinishEvent()
        navigateBack()
    }

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
                when (val s = state) {
                    CountryUiState.Loading -> {
                        ProgressBar()
                    }

                    is CountryUiState.Success -> {
                        val items = s.data
                        if (items.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(items = items, key = { it.id }) { region ->
                                    OptionListItem(
                                        text = region.name,
                                        onClick = {
                                            viewModel.onReturnWithParam(
                                                id = region.id,
                                                name = region.name,
                                            )
                                        }
                                    )
                                }
                            }
                        } else {
                            ScreenMessage(
                                title = stringResource(R.string.country_not_found),
                                imageId = R.drawable.im_lack_of_list_cat
                            )
                        }
                    }

                    is CountryUiState.Error -> {
                        when (s.failure) {
                            Failure.Network -> {
                                ScreenMessage(
                                    title = stringResource(R.string.im_bad_connection_description),
                                    imageId = R.drawable.im_bad_connection
                                )
                            }

                            is Failure.Unknown -> {
                                ScreenMessage(
                                    title = stringResource(R.string.unknown_error),
                                    imageId = R.drawable.im_server_error_android
                                )
                            }

                            else -> {
                                ScreenMessage(
                                    title = stringResource(R.string.country_request_error),
                                    imageId = R.drawable.im_lack_of_list_android
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
