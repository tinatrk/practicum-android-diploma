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
import ru.practicum.android.diploma.ui.navigation.util.DetailsSource

val viewModelModule = module {
    viewModelOf(::SearchViewModel)

    viewModelOf(::FavoritesViewModel)

    viewModel { (vacancyId: String, source: DetailsSource) ->
        VacancyDetailsViewModel(
            vacancyId = vacancyId,
            source = source,
            detailsInteractor = get(),
            favoritesInteractor = get(),
            externalNavigatorInteractor = get(),
            vacancyConverter = get()
        )
    }

    singleOf(::VacancyConverter)

    viewModelOf(::FilterSettingsViewModel)

    viewModel { (selectedIndustryId: Int?) ->
        FilterIndustryViewModel(
            savedStateHandle = get(),
            selectedIndustryId = selectedIndustryId,
            filterInteractor = get(),
            converter = get()
        )
    }

    viewModelOf(::WorkLocationViewModel)

    viewModelOf(::FilterCountryViewModel)

    viewModel { (selectedCountryId: Int?) ->
        FilterRegionViewModel(
            interactor = get(),
            savedStateHandle = get(),
            selectedCountryId = selectedCountryId,
            converter = get()
        )
    }

    single {
        SavedStateHandle()
    }
}
