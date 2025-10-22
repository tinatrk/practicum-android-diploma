package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filters.models.FilterIndustryUi
import ru.practicum.android.diploma.presentation.filters.models.FilterIndustryUiState
import ru.practicum.android.diploma.presentation.filters.viewmodel.FilterIndustryViewModel
import ru.practicum.android.diploma.ui.components.CustomButton
import ru.practicum.android.diploma.ui.components.CustomSearchBar
import ru.practicum.android.diploma.ui.components.ErrorMessage
import ru.practicum.android.diploma.ui.components.ProgressBar
import ru.practicum.android.diploma.ui.components.ToggleIcon
import ru.practicum.android.diploma.ui.components.topbar.SimpleTopBarWithBackIcon
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography
import ru.practicum.android.diploma.util.common.Failure

@Composable
fun FilterIndustryScreen(
    selectedIndustryId: Int?,
    viewModel: FilterIndustryViewModel = koinViewModel() {
        parametersOf(selectedIndustryId)
    },
    navigateBack: () -> Unit
) {
    val shouldFinish = viewModel.shouldFinish.collectAsState(initial = false)
    if (shouldFinish.value) {
        viewModel.consumeFinishEvent()
        navigateBack()
    }

    val screenState = viewModel.screenState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    val isNeedShowSearchBar = (screenState.value as? FilterIndustryUiState.Error)?.error !is Failure.Network

    Scaffold(
        topBar = {
            SimpleTopBarWithBackIcon(
                title = stringResource(R.string.filter_industry_screen_title),
                onNavigationIconClick = viewModel::onBackNavigate
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(LocalCustomColors.current.screenBackground)
        ) {
            if (isNeedShowSearchBar) {
                CustomSearchField(
                    placeholderText = stringResource(R.string.industry_search_bar_placeholder),
                    onQueryChanged = viewModel::onSearchTextChanged,
                    onClearQueryClick = viewModel::onClearSearchQuery,
                    onSearch = viewModel::onSearchTextChanged,
                )
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                        .background(LocalCustomColors.current.screenBackground)
                )
            }

            when (val curState = screenState.value) {
                is FilterIndustryUiState.Loading -> {
                    keyboardController?.hide()
                    ProgressBar()
                }

                is FilterIndustryUiState.Content -> {
                    FilterIndustryContent(
                        industries = curState.data.toImmutableList(),
                        checkedIndustryId = curState.curChoice,
                        onIndustryClick = viewModel::onIndustryItemClick,
                        onSaveChoiceClick = viewModel::onSaveChoiceClick
                    )
                }

                is FilterIndustryUiState.Error -> {
                    FilterIndustryError(
                        error = curState.error
                    )
                }
            }
        }
    }
}

@Composable
fun FilterIndustryContent(
    industries: ImmutableList<FilterIndustryUi>,
    checkedIndustryId: Int?,
    onIndustryClick: (FilterIndustryUi) -> Unit,
    onSaveChoiceClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 8.dp)
                .background(LocalCustomColors.current.screenBackground)
                .weight(1f)
        ) {
            items(items = industries, key = { it.id }, contentType = { it }) { item ->
                RoundToggleListItem(
                    item = item,
                    isActive = item.id == checkedIndustryId,
                    onItemClick = onIndustryClick
                )
            }
        }
        if (checkedIndustryId != null) {
            CustomButton(
                text = stringResource(R.string.btn_choose),
                onClick = onSaveChoiceClick
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun FilterIndustryError(error: Failure) {
    when (error) {
        is Failure.Network -> {
            ErrorMessage(
                title = stringResource(R.string.im_bad_connection_description),
                imageId = R.drawable.im_bad_connection,
                modifier = Modifier.fillMaxSize()
            )
        }

        is Failure.NotFound -> {
            ErrorMessage(
                title = stringResource(R.string.filter_industry_empty_content_message),
                imageId = R.drawable.im_lack_of_list_cat,
                modifier = Modifier.fillMaxSize()
            )
        }

        else -> {
            ErrorMessage(
                title = stringResource(R.string.filter_industry_error_message),
                imageId = R.drawable.im_lack_of_list_android,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun RoundToggleListItem(
    item: FilterIndustryUi,
    isActive: Boolean = false,
    onItemClick: (FilterIndustryUi) -> Unit
) {
    val colors = LocalCustomColors.current.filterListItemColors.filterItemWithCheckBox
    val typography = LocalTypography.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(colors.background)
            .clickable { onItemClick(item) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.name,
            style = typography.filterListItemText,
            color = colors.text,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)

        )

        ToggleIcon(
            isChecked = isActive,
            checkedIconId = R.drawable.ic_radio_button_on_24px,
            uncheckedIconId = R.drawable.ic_radio_button_off_24px,
            onIconClick = { onItemClick(item) },
            iconDescription = stringResource(R.string.filter_industry_item_description),
            iconUncheckedTint = colors.iconTint,
            modifier = Modifier.padding(end = 8.dp),
        )
    }
}

@Composable
fun CustomSearchField(
    onSearch: (String) -> Unit,
    onQueryChanged: (String) -> Unit,
    onClearQueryClick: () -> Unit,
    placeholderText: String,
) {
    var query by rememberSaveable { mutableStateOf("") }
    val latestOnSearch by rememberUpdatedState(onSearch)

    CustomSearchBar(
        text = query,
        placeholderText = placeholderText,
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
