package ru.practicum.android.diploma.ui.screen

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.SharedFlow
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.worklocation.models.WorkLocationNavEvent
import ru.practicum.android.diploma.presentation.worklocation.models.WorkLocationNavEvent.NavigateBack
import ru.practicum.android.diploma.presentation.worklocation.models.WorkLocationNavEvent.NavigateToCountryScreen
import ru.practicum.android.diploma.presentation.worklocation.models.WorkLocationNavEvent.NavigateToRegionScreen
import ru.practicum.android.diploma.presentation.worklocation.viewmodel.WorkLocationViewModel
import ru.practicum.android.diploma.ui.components.FilterListItem
import ru.practicum.android.diploma.ui.components.topbar.SimpleTopBarWithBackIcon
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography

@Composable
fun WorkLocationScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    navigateToFilterCountry: () -> Unit,
    navigateToFilterRegion: (Int?) -> Unit,
) {
    val vm: WorkLocationViewModel = koinViewModel()

    val workLocationNavEvent = vm.workLocationNavEvent
    HandleWorkLocationNavigation(
        workLocationNavEvent = workLocationNavEvent,
        navigateBack = navigateBack,
        navigateToFilterCountry = navigateToFilterCountry,
        navigateToFilterRegion = navigateToFilterRegion
    )

    val workLocationUiState by vm.workLocationUiState.collectAsStateWithLifecycle()

    val onWorkLocationData = vm::clearWorkLocationData
    val onClearRegionData = vm::clearRegionData

    val onCountryClick = vm::navigateToCountryScreen
    val onRegionClick = vm::navigateToRegionScreen
    val onFinishButtonClick = vm::finishWithResult

    Scaffold(
        topBar = {
            SimpleTopBarWithBackIcon(
                title = stringResource(R.string.work_location_title),
                onNavigationIconClick = navigateBack
            )
        }
    ) { innerPadding ->
        WorkLocationScreen(
            modifier = modifier.padding(innerPadding),
            countryData = workLocationUiState.country?.name,
            regionData = workLocationUiState.region?.name,
            onClearWorkLocationData = onWorkLocationData,
            onClearRegionData = onClearRegionData,
            onCountryClick = onCountryClick,
            onRegionClick = onRegionClick,
            onFinishButtonClick = onFinishButtonClick
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
    onFinishButtonClick: () -> Unit
) {
    val context = LocalContext.current

    val countryText = countryData ?: stringResource(R.string.country)
    val regionText = regionData ?: stringResource(R.string.region)
    val isCountryDataSelected = countryData != null
    val isRegionDataSelected = regionData != null

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilterLocationItem(
            label = stringResource(R.string.country),
            locationText = countryText,
            isLocationSelected = isCountryDataSelected,
            onLocationClick = onCountryClick,
            onClearLocationData = onClearWorkLocationData
        )
        FilterLocationItem(
            label = stringResource(R.string.region),
            locationText = regionText,
            isLocationSelected = isRegionDataSelected,
            onLocationClick = {
                if (countryData != null) {
                    onRegionClick()
                } else {
                    Toast
                        .makeText(
                            context,
                            R.string.country_unknown,
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
            },
            onClearLocationData = onClearRegionData
        )

        Spacer(modifier = Modifier.weight(1f))

        if (isCountryDataSelected) {
            FinishButton(
                onFinishButtonClick = onFinishButtonClick
            )
        }
    }
}

@Composable
fun FilterLocationItem(
    label: String,
    locationText: String,
    isLocationSelected: Boolean,
    onLocationClick: () -> Unit = {},
    onClearLocationData: () -> Unit
) {
    FilterListItem(
        label = label,
        value = locationText,
        isSelected = isLocationSelected,
        onClick = onLocationClick,
        onClearSelected = onClearLocationData
    )
}

@Composable
fun FinishButton(
    onFinishButtonClick: () -> Unit
) {
    val colors = LocalCustomColors.current
    val typography = LocalTypography.current

    Button(
        onClick = onFinishButtonClick,
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

@Composable
private fun HandleWorkLocationNavigation(
    workLocationNavEvent: SharedFlow<WorkLocationNavEvent>,
    navigateBack: () -> Unit,
    navigateToFilterCountry: () -> Unit,
    navigateToFilterRegion: (Int?) -> Unit
) {
    LaunchedEffect(Unit) {
        workLocationNavEvent.collect { event ->
            when (event) {
                NavigateBack -> navigateBack()
                NavigateToCountryScreen -> navigateToFilterCountry()
                is NavigateToRegionScreen -> navigateToFilterRegion(event.id)
            }
        }
    }
}

@Preview(
    name = "Light list Item",
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun LightWorkLocationScreenPreview() {
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
                onFinishButtonClick = {}
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
private fun DarkWorkLocationScreenPreview() {
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
                onFinishButtonClick = {}
            )
        }
    }
}
