package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.toImmutableList
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.models.VacancyBriefInfo
import ru.practicum.android.diploma.presentation.search.models.SearchUiState
import ru.practicum.android.diploma.presentation.search.viewmodel.SearchViewModel
import ru.practicum.android.diploma.ui.components.CustomSearchBar
import ru.practicum.android.diploma.ui.components.ProgressBar
import ru.practicum.android.diploma.ui.components.ScreenMessage
import ru.practicum.android.diploma.ui.components.VacancyList
import ru.practicum.android.diploma.ui.components.topbar.SearchScreenTopBar
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.Blue
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography
import ru.practicum.android.diploma.ui.theme.White
import ru.practicum.android.diploma.util.common.Failure

@Composable
fun HomeScreen(
    modifier: Modifier,
    navigateToVacancy: (String) -> Unit,
    navigateToFilterSettings: () -> Unit
) {
    val vm: SearchViewModel = koinViewModel()
    val state by vm.searchUiState.collectAsStateWithLifecycle()
    val isNextPageError by vm.isNextPageError.collectAsStateWithLifecycle()

    val onSearch = vm::searchVacancies
    val onLoadNextPage = vm::loadNextPage
    val onQueryChanged = vm::setTypedQuery
    val onClearQueryClick = vm::clearTextClick

    Scaffold(
        containerColor = LocalCustomColors.current.screenBackground,
        topBar = {
            SearchScreenTopBar { navigateToFilterSettings() }
        }
    ) { innerPadding ->
        HomeScreen(
            state = state,
            onSearch = onSearch,
            onLoadNextPage = onLoadNextPage,
            isNextPageError = isNextPageError,
            onVacancyClick = navigateToVacancy,
            onQueryChanged = onQueryChanged,
            onClearQueryClick = onClearQueryClick,
            modifier = modifier.padding(innerPadding),
        )
    }
}

@Composable
fun HomeScreen(
    state: SearchUiState,
    onSearch: (String) -> Unit,
    onLoadNextPage: () -> Unit,
    isNextPageError: Boolean,
    onVacancyClick: (String) -> Unit,
    onQueryChanged: (String) -> Unit,
    onClearQueryClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchField(
            state = state,
            onSearch = onSearch,
            onQueryChanged = onQueryChanged,
            onClearQueryClick = onClearQueryClick
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                SearchUiState.Idle -> SearchIdleContent()
                SearchUiState.Loading -> ProgressBar()
                is SearchUiState.Success -> {
                    if (state.data.isEmpty()) {
                        InfoLabel(
                            count = state.count,
                            modifier = Modifier.align(Alignment.TopCenter)
                        )
                        SearchNoResultsContent()
                    } else {
                        Column(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            InfoLabel(state.count)
                            FoundVacanciesList(
                                vacancies = state.data,
                                isLastPage = state.isLastPage,
                                onLoadNextPage = onLoadNextPage,
                                isNextPageError = isNextPageError,
                                onVacancyClick = onVacancyClick
                            )
                        }
                    }
                }

                is SearchUiState.Error -> {
                    ErrorRouter(state.failure)
                }
            }
        }
    }
}

@Composable
fun SearchField(
    state: SearchUiState,
    onSearch: (String) -> Unit,
    onQueryChanged: (String) -> Unit,
    onClearQueryClick: () -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }
    val latestOnSearch by rememberUpdatedState(onSearch)

    val keyboardController = LocalSoftwareKeyboardController.current
    if (state is SearchUiState.Loading) keyboardController?.hide()

    CustomSearchBar(
        text = query,
        placeholderText = stringResource(R.string.search_bar_hint),
        onTextChanged = { newText ->
            query = newText
            if (query.isEmpty()) {
                onClearQueryClick()
            }
            onQueryChanged(newText)
        },
        onClearTextClick = {
            query = ""
            onClearQueryClick()
        },
        onSearch = latestOnSearch
    )
}

@Composable
fun InfoLabel(
    count: Int,
    modifier: Modifier = Modifier
) {
    val text = if (count == 0) {
        stringResource(R.string.no_vacancies_label)
    } else {
        stringResource(R.string.vacancies_count_text, count)
    }

    Surface(
        modifier = modifier
            .padding(start = 16.dp, top = 3.dp, end = 16.dp),
        shape = RoundedCornerShape(12.dp),
        color = Blue
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            color = White,
            style = LocalTypography.current.vacancyInfoCardText
        )
    }
}

@Composable
fun FoundVacanciesList(
    vacancies: List<VacancyBriefInfo>,
    isLastPage: Boolean,
    onLoadNextPage: () -> Unit,
    isNextPageError: Boolean,
    onVacancyClick: (String) -> Unit
) {
    VacancyList(
        vacancies = vacancies.toImmutableList(),
        onVacancyClick = onVacancyClick,
        onLoadNextPage = onLoadNextPage,
        isNextPageError = isNextPageError,
        isLastPage = isLastPage
    )
}

@Composable
fun SearchIdleContent() {
    ScreenMessage(
        imageId = R.drawable.im_search_vacancy
    )
}

@Composable
fun SearchNoResultsContent() {
    ScreenMessage(
        title = stringResource(R.string.vacancies_load_failed),
        imageId = R.drawable.im_lack_of_list_cat
    )
}

@Composable
fun ErrorRouter(errorState: Failure) {
    when (errorState) {
        Failure.Network -> NoInternetContent()
        is Failure.Unknown -> ErrorServerContent()
        else -> SearchNoResultsContent()
    }
}

@Composable
fun ErrorServerContent() {
    ScreenMessage(
        imageId = R.drawable.im_server_error_android
    )
}

@Composable
fun NoInternetContent() {
    ScreenMessage(
        title = stringResource(R.string.im_bad_connection_description),
        imageId = R.drawable.im_bad_connection
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    name = "Text Field",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SearchFieldPreview() {
    AppTheme(darkTheme = true) {
        Scaffold(
            containerColor = LocalCustomColors.current.screenBackground,
            topBar = {
                SearchScreenTopBar { }
            }
        ) { innerPadding ->
            HomeScreen(
                state = SearchUiState.Idle,
                { },
                { },
                false,
                { },
                { },
                { },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }
}

