package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    text: String,
    placeholderText: String,
    onTextChanged: (String) -> Unit,
    onClearTextClick: () -> Unit,
    onSearch: (String) -> Unit
) {
    val colors = LocalCustomColors.current
    val typography = LocalTypography.current
    val keyboardController = LocalSoftwareKeyboardController.current

    SearchBar(
        inputField = {
            CompositionLocalProvider(LocalTextStyle provides typography.searchEditTextText) {
                SearchBarDefaults.InputField(
                    query = text,
                    onQueryChange = {
                        onTextChanged(it)
                    },
                    onSearch = {
                        if (it.isNotEmpty()) {
                            onSearch(it.trim())
                            keyboardController?.hide()
                        }
                    },
                    expanded = false,
                    onExpandedChange = {},

                    placeholder = {
                        Text(
                            text = placeholderText,
                            style = typography.searchEditTextText,
                            color = colors.searchEditTextColors.hint
                        )
                    },

                    trailingIcon = {
                        if (text.isEmpty()) {
                            Icon(
                                painter = painterResource(R.drawable.ic_search_24px),
                                contentDescription = stringResource(R.string.search_bar_icon_description),
                            )
                        } else {
                            Icon(
                                painter = painterResource(R.drawable.ic_close_24px),
                                contentDescription = stringResource(R.string.search_bar_clear_btn_description),
                                modifier = Modifier.clickable { onClearTextClick() }
                            )
                        }
                    },

                    colors = SearchBarDefaults.inputFieldColors(
                        focusedTextColor = colors.searchEditTextColors.text,
                        unfocusedTextColor = colors.searchEditTextColors.text,
                        disabledTextColor = colors.searchEditTextColors.text,

                        focusedPlaceholderColor = colors.searchEditTextColors.hint,
                        unfocusedPlaceholderColor = colors.searchEditTextColors.hint,
                        disabledPlaceholderColor = colors.searchEditTextColors.hint,

                        focusedTrailingIconColor = colors.searchEditTextColors.iconTint,
                        unfocusedTrailingIconColor = colors.searchEditTextColors.text,
                        cursorColor = colors.searchEditTextColors.cursor
                    ),
                )
            }
        },
        windowInsets = WindowInsets(top = 0),
        expanded = false,
        onExpandedChange = {},
        shape = RoundedCornerShape(12.dp),
        colors = SearchBarDefaults.colors(
            containerColor = colors.searchEditTextColors.background
        ),
        modifier = Modifier
            .background(colors.screenBackground)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {}
}

@Preview("lightTheme", showSystemUi = true)
@Composable
private fun CustomSearchBarPreviewLight() {
    AppTheme(darkTheme = false) {
        CustomSearchBar(
            text = "",
            placeholderText = stringResource(R.string.search_bar_hint),
            onTextChanged = {},
            onClearTextClick = {},
            onSearch = {}
        )
    }
}

@Preview("darkTheme", showSystemUi = true)
@Composable
private fun CustomSearchBarPreviewDark() {
    AppTheme(darkTheme = true) {
        CustomSearchBar(
            text = "",
            placeholderText = stringResource(R.string.search_bar_hint),
            onTextChanged = {},
            onClearTextClick = {},
            onSearch = {}
        )
    }
}
