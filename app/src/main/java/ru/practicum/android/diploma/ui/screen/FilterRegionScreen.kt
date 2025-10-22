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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filters.models.FilterRegionUi
import ru.practicum.android.diploma.presentation.filters.models.RegionUiState
import ru.practicum.android.diploma.presentation.filters.viewmodel.FilterRegionViewModel
import ru.practicum.android.diploma.ui.components.CustomSearchBar
import ru.practicum.android.diploma.ui.components.OptionListItem
import ru.practicum.android.diploma.ui.components.ProgressBar
import ru.practicum.android.diploma.ui.components.ScreenMessage
import ru.practicum.android.diploma.ui.components.topbar.RegionTopBar
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.util.common.Failure

@Composable
fun FilterRegionScreen(
    selectedCountryId: Int?,
    viewModel: FilterRegionViewModel = koinViewModel() {
        parametersOf(selectedCountryId)
    },
    navigateBack: () -> Unit
) {
    val shouldFinish by viewModel.shouldFinish.collectAsState(initial = false)
    LaunchedEffect(shouldFinish) {
        if (shouldFinish) {
            viewModel.consumeFinishEvent()
            navigateBack()
        }
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    LaunchedEffect(Unit) {
        viewModel.hideKeyboardEvent.collect {
            focusManager.clearFocus(force = true)
            keyboardController?.hide()
        }
    }

    val state by viewModel.regionUiState.collectAsStateWithLifecycle()
    val query = viewModel.query.collectAsState().value
    val isNeedShowSearchBar = (state as? RegionUiState.Error)?.failure !is Failure.Network

    Scaffold(
        containerColor = LocalCustomColors.current.screenBackground,
        topBar = { RegionTopBar { viewModel.onReturnWithoutParam() } }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (isNeedShowSearchBar) {
                CustomSearchBar(
                    text = query,
                    placeholderText = stringResource(R.string.search_bar_region_hint),
                    onTextChanged = { query ->
                        viewModel.onSearchTextChange(query)
                    },
                    onClearTextClick = {
                        viewModel.onQueryCleared()
                    },
                    onSearch = { query ->
                        viewModel.onSearchTextChange(query)
                    }
                )
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                RegionStateContent(
                    state = state,
                    onSelect = { region ->
                        viewModel.onReturnWithParam(
                            id = region.id,
                            name = region.name,
                            parentId = region.parentId
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun RegionStateContent(
    state: RegionUiState,
    onSelect: (FilterRegionUi) -> Unit
) {
    when (state) {
        RegionUiState.Loading -> ProgressBar()
        is RegionUiState.Success -> {
            val items = state.data
            if (items.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(items = items, key = { it.id }, contentType = { it }) { region ->
                        OptionListItem(
                            text = region.name,
                            onClick = { onSelect(region) }
                        )
                    }
                }
            } else {
                ScreenMessage(
                    title = stringResource(R.string.region_not_found),
                    imageId = R.drawable.im_lack_of_list_cat
                )
            }
        }

        is RegionUiState.Error -> FailureMessage(state.failure)
    }
}

@Composable
private fun FailureMessage(failure: Failure) {
    val (titleRes, imageRes) = when (failure) {
        Failure.Network -> R.string.im_bad_connection_description to R.drawable.im_bad_connection
        is Failure.Unknown -> R.string.unknown_error to R.drawable.im_server_error_android
        else -> R.string.region_request_error to R.drawable.im_lack_of_list_android
    }
    ScreenMessage(title = stringResource(titleRes), imageId = imageRes)
}

@Preview
@Composable
private fun RegionScreenPreviewLight() {
    AppTheme(darkTheme = false) {
        FilterRegionScreen(
            selectedCountryId = 1,
            navigateBack = {}
        )
    }
}

@Preview
@Composable
private fun RegionScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        FilterRegionScreen(
            selectedCountryId = 1,
            navigateBack = {}
        )
    }
}
