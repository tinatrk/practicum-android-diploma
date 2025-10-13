package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.worklocation.viewmodel.WorkLocationViewModel
import ru.practicum.android.diploma.ui.components.FilterListItem
import ru.practicum.android.diploma.ui.components.topbar.SimpleTopBarWithBackIcon
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography

// колбэки пока нереализованы, потому что они связаны с навигациями на экраны,
// которых пока нет. По ходу дела нужно будет доработать
@Composable
fun WorkLocationScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onCountryClick: () -> Unit,
    onRegionClick: () -> Unit,
    onChoiceClick: (String?, String?) -> Unit // Передавать на экран или сохранять в SP?
) {
    val vm: WorkLocationViewModel = koinViewModel()

    val countryData by vm.countryData.collectAsStateWithLifecycle()
    val regionData by vm.regionData.collectAsStateWithLifecycle()

    val onWorkLocationData = vm::clearWorkLocationData
    val onClearRegionData = vm::clearRegionData

    Scaffold(
        topBar = {
            SimpleTopBarWithBackIcon(
                title = "Выбор места работы",
                onNavigationIconClick = onBackClick
            )
        }
    ) { innerPadding ->
        WorkLocationScreen(
            modifier = modifier.padding(innerPadding),
            countryData = countryData,
            regionData = regionData,
            onClearWorkLocationData = onWorkLocationData,
            onClearRegionData = onClearRegionData,
            onCountryClick = onCountryClick,
            onRegionClick = onRegionClick,
            onChoiceClick = onChoiceClick
        )
    }
}

@Composable
fun WorkLocationScreen(
    modifier: Modifier = Modifier,
    countryData: String?,
    regionData: String?,
    onClearWorkLocationData: () -> Unit,
    onClearRegionData: () -> Unit,
    onCountryClick: () -> Unit,
    onRegionClick: () -> Unit,
    onChoiceClick: (String?, String?) -> Unit
) {
    val countryText = countryData ?: stringResource(R.string.country)
    val regionText = regionData ?: stringResource(R.string.region)
    val isCountryDataSelected = countryData != null
    val isRegionDataSelected = regionData != null


    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilterCountryItem(
            countryText = countryText,
            isCountryDataSelected = isCountryDataSelected,
            onCountryClick = onCountryClick,
            onClearWorkLocationData = onClearWorkLocationData
        )
        FilterRegionItem(
            regionText = regionText,
            isRegionDataSelected = isRegionDataSelected,
            onRegionClick = { if (countryData != null) onRegionClick() },
            onClearRegionData = onClearRegionData
        )

        Spacer(modifier = Modifier.weight(1f))

        if (isCountryDataSelected) {
            ChoiceButton(
                countryData = countryData,
                regionData = regionData,
                onChoiceClick = onChoiceClick
            )
        }
    }
}

@Composable
fun FilterCountryItem(
    countryText: String,
    isCountryDataSelected: Boolean,
    onCountryClick: () -> Unit,
    onClearWorkLocationData: () -> Unit
) {
    FilterListItem(
        label = stringResource(R.string.country),
        value = countryText,
        isSelected = isCountryDataSelected,
        onClick = onCountryClick,
        onClearSelected = onClearWorkLocationData
    )
}

@Composable
fun FilterRegionItem(
    regionText: String,
    isRegionDataSelected: Boolean,
    onRegionClick: () -> Unit = {},
    onClearRegionData: () -> Unit
) {
    FilterListItem(
        label = stringResource(R.string.region),
        value = regionText,
        isSelected = isRegionDataSelected,
        onClick = onRegionClick,
        onClearSelected = onClearRegionData
    )
}

@Composable
fun ChoiceButton(
    countryData: String?,
    regionData: String?,
    onChoiceClick: (String?, String?) -> Unit,
) {
    val colors = LocalCustomColors.current
    val typography = LocalTypography.current

    Button(
        onClick = { onChoiceClick(countryData, regionData) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 17.dp, end = 17.dp, bottom = 24.dp)
            .height(59.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.buttonColors.commonButtonColors.background,
            contentColor = colors.buttonColors.commonButtonColors.text
        )
    ) {
        Text(
            text = stringResource(R.string.btn_choose),
            style = typography.buttonText
        )
    }
}

@Preview(
    name = "Light list Item",
    showSystemUi = true,
    showBackground = true
)
@Composable
fun LightWorkLocationScreenPreview() {
    AppTheme(
        darkTheme = false
    ) {
        Scaffold(
            topBar = { SimpleTopBarWithBackIcon("Выбор места работы") { } }
        ) { innerPadding ->
            WorkLocationScreen(
                modifier = Modifier.padding(innerPadding),
                countryData = "Россия",
                regionData = null,
                onClearWorkLocationData = {},
                onClearRegionData = {},
                onCountryClick = {},
                onRegionClick = {},
                onChoiceClick = { _,_ -> }
            )
        }
    }
}

@Preview(
    name = "Dark list Item",
    showSystemUi = true,
    showBackground = true
)
@Composable
fun DarkWorkLocationScreenPreview() {
    AppTheme(
        darkTheme = true
    ) {
        Scaffold(
            topBar = { SimpleTopBarWithBackIcon("Выбор места работы") { } }
        ) { innerPadding ->
            WorkLocationScreen(
                modifier = Modifier.padding(innerPadding),
                countryData = "Россия",
                regionData = "Пермский край",
                onClearWorkLocationData = {},
                onClearRegionData = {},
                onCountryClick = {},
                onRegionClick = {},
                onChoiceClick = { _,_ -> }
            )
        }
    }
}
