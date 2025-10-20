package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filters.models.FilterSettingsEvent
import ru.practicum.android.diploma.presentation.filters.models.FilterSettingsUiState
import ru.practicum.android.diploma.presentation.filters.viewmodel.FilterSettingsViewModel
import ru.practicum.android.diploma.ui.components.CustomButton
import ru.practicum.android.diploma.ui.components.FilterListItem
import ru.practicum.android.diploma.ui.components.topbar.SimpleTopBarWithBackIcon
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.CustomTypography
import ru.practicum.android.diploma.ui.theme.FilterListItemColors
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography
import ru.practicum.android.diploma.ui.theme.TextFieldColors

@Composable
fun FilterSettingsScreen(
    viewModel: FilterSettingsViewModel = koinViewModel(),
    navigateBack: () -> Unit,
    navigateToWorkLocation: () -> Unit,
    navigateToFilterIndustry: (Int?) -> Unit,
) {
    val colors = LocalCustomColors.current
    val typography = LocalTypography.current
    val state by viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.events.collect { event ->
            when (event) {
                FilterSettingsEvent.NavigateBack -> navigateBack()
                FilterSettingsEvent.NavigateToWorkLocation -> navigateToWorkLocation()
            }
        }
    }

    Scaffold(
        topBar = {
            SimpleTopBarWithBackIcon(
                title = stringResource(R.string.settings_filter),
                onNavigationIconClick = navigateBack
            )
        },
        bottomBar = {
            if (state.showActionButtons) {
                Column(modifier = Modifier.padding(bottom = 24.dp)) {
                    CustomButton(
                        text = stringResource(R.string.btn_apply),
                        isPositiveType = true,
                        onClick = { viewModel.onApply() }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    CustomButton(
                        text = stringResource(R.string.btn_throw_off),
                        isPositiveType = false,
                        onClick = { viewModel.onReset() }
                    )
                }
            }
        },
        containerColor = colors.screenBackground,
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                SettingsContent(
                    state = state,
                    onPlaceClick = { viewModel.navigateToWorkLocation() },
                    onIndustryClick = navigateToFilterIndustry,
                    onClearPlaceSelected = { viewModel.onClearAddress() },
                    onClearIndustrySelected = { viewModel.onClearIndustry() },
                    onValueChange = { viewModel.onSalaryChange(it) },
                    onClearTextClick = { viewModel.onSalaryChange("") },
                    onCheckedChange = { viewModel.onShowWithoutSalaryChange(it) },
                    colors = colors.filterListItemColors,
                    typography = typography
                )
            }
        }
    )
}

@Composable
private fun SettingsContent(
    modifier: Modifier = Modifier,
    state: FilterSettingsUiState,
    onPlaceClick: () -> Unit,
    onIndustryClick: (Int?) -> Unit,
    onClearPlaceSelected: () -> Unit,
    onClearIndustrySelected: () -> Unit,
    onValueChange: (String) -> Unit,
    onClearTextClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    colors: FilterListItemColors,
    typography: CustomTypography
) {
    Column {
        FilterListItem(
            label = stringResource(R.string.work_place),
            value = state.address?.let {
                listOfNotNull(it.country.name, it.region?.name).joinToString(", ")
            } ?: stringResource(
                R.string.work_place
            ),
            isSelected = state.address != null,
            onClick = onPlaceClick,
            onClearSelected = onClearPlaceSelected
        )
        FilterListItem(
            label = stringResource(R.string.industry),
            value = state.industry?.name ?: stringResource(R.string.industry),
            isSelected = state.industry != null,
            onClick = {
                onIndustryClick(state.industry?.id)
            },
            onClearSelected = onClearIndustrySelected
        )

        Spacer(modifier = modifier.height(24.dp))

        SalaryTextField(
            value = state.salary,
            onValueChange = onValueChange,
            onClearTextClick = onClearTextClick,
            typography = typography
        )

        Spacer(modifier = modifier.height(24.dp))

        SalaryCheckbox(
            isChecked = state.onlyWithSalary,
            onCheckedChange = { checked ->
                onCheckedChange(checked)
            },
            colors = colors,
            typography = typography
        )
    }
}

@Composable
private fun SalaryTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onClearTextClick: () -> Unit,
    colors: TextFieldColors = LocalCustomColors.current.textFieldColors,
    typography: CustomTypography,
    focusManager: FocusManager = LocalFocusManager.current
) {
    var isFocused by remember { mutableStateOf(false) }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        value = value,
        onValueChange = { newValue ->
            var filteredValue = newValue.filter { it.isDigit() }
            if (filteredValue.length > 1 && filteredValue.startsWith('0')) {
                filteredValue = filteredValue.trimStart('0')
            }
            onValueChange(filteredValue)
        },
        singleLine = true,
        placeholder = {
            Text(
                text = stringResource(R.string.enter_sum),
                style = typography.textFieldText
            )
        },
        textStyle = typography.textFieldText,
        trailingIcon = {
            if (value.isNotEmpty()) {
                Icon(
                    painter = painterResource(R.drawable.ic_close_24px),
                    contentDescription = stringResource(R.string.search_bar_clear_btn_description),
                    modifier = Modifier.clickable(onClick = onClearTextClick)
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colors.background,
            unfocusedContainerColor = colors.background,
            focusedPlaceholderColor = colors.labelState.unfocusedAndTextEmpty,
            unfocusedPlaceholderColor = colors.labelState.unfocusedAndTextEmpty,
            focusedLabelColor = colors.labelState.focused,
            unfocusedLabelColor = colors.labelState.unfocusedAndTextEmpty,
            disabledLabelColor = colors.labelState.unfocusedAndTextNotEmpty,
            cursorColor = colors.labelState.focused,
            focusedTrailingIconColor = colors.text,
            unfocusedTrailingIconColor = colors.text,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedTextColor = colors.text,
            unfocusedTextColor = colors.text
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        shape = RoundedCornerShape(12.dp),
        label = {
            Column {
                Text(
                    text = stringResource(R.string.expected_salary),
                    style = typography.textFieldLabel
                )

                if (!isFocused && value.isEmpty()) {
                    Text(
                        text = stringResource(R.string.enter_sum),
                        style = typography.textFieldText
                    )
                }
            }
        }
    )
}

@Composable
private fun SalaryCheckbox(
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
    colors: FilterListItemColors,
    typography: CustomTypography
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.5.dp, horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.do_not_show_without_salary),
            style = typography.filterListItemText.copy(color = colors.filterItemWithCheckBox.text),
            modifier = Modifier.weight(1f)
        )

        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            modifier = modifier.size(18.dp),
            colors = CheckboxDefaults.colors(
                checkedColor = colors.filterItemWithCheckBox.iconTint,
                uncheckedColor = colors.filterItemWithCheckBox.iconTint,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsContentPreview() {
    AppTheme(darkTheme = false) {
        SettingsContent(
            state = FilterSettingsUiState(),
            onPlaceClick = {},
            onIndustryClick = {},
            colors = LocalCustomColors.current.filterListItemColors,
            typography = LocalTypography.current,
            onClearPlaceSelected = {},
            onClearIndustrySelected = {},
            onValueChange = {},
            onClearTextClick = {},
            onCheckedChange = {}
        )

        Button(
            onClick = {},
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Задать настройки фильтрации"
            )
        }

        Button(
            onClick = {},
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Очистить настройки фильтрации"
            )
        }
    }
}
