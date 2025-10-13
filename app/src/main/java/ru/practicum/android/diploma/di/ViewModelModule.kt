package ru.practicum.android.diploma.di

import androidx.lifecycle.SavedStateHandle
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.converter.VacancyConverter
import ru.practicum.android.diploma.presentation.details.viewmodel.VacancyDetailsViewModel
import ru.practicum.android.diploma.presentation.favorites.viewmodel.FavoritesViewModel
import ru.practicum.android.diploma.presentation.filters.viewmodel.FilterCountryViewModel
import ru.practicum.android.diploma.presentation.filters.viewmodel.FilterIndustryViewModel
import ru.practicum.android.diploma.presentation.filters.viewmodel.FilterRegionViewModel
import ru.practicum.android.diploma.presentation.filters.viewmodel.FilterSettingsViewModel
import ru.practicum.android.diploma.presentation.filters.viewmodel.WorkLocationViewModel
import ru.practicum.android.diploma.presentation.search.viewmodel.SearchViewModel

val viewModelModule = module {
    viewModelOf(::SearchViewModel)

    viewModelOf(::FavoritesViewModel)

    viewModel { (vacancyId: String) ->
        VacancyDetailsViewModel(
            vacancyId = vacancyId,
            detailsInteractor = get(),
            favoritesInteractor = get(),
            externalNavigatorInteractor = get(),
            vacancyConverter = get()
        )
    }

    singleOf(::VacancyConverter)

    viewModel {
        FilterSettingsViewModel(
            savedStateHandle = get()
        )
    }

    viewModel { (selectedIndustryId: Int?) ->
        FilterIndustryViewModel(
            savedStateHandle = get(),
            selectedIndustryId = selectedIndustryId
        )
    }

    viewModel {
        WorkLocationViewModel(
            savedStateHandle = get()
        )
    }

    viewModel {
        FilterCountryViewModel(
            savedStateHandle = get()
        )
    }

    viewModel { (selectedCountryId: Int?) ->
        FilterRegionViewModel(
            savedStateHandle = get(),
            selectedCountryId = selectedCountryId
        )
    }

    single {
        SavedStateHandle()
    }
}
