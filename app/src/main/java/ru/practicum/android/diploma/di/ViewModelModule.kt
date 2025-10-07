package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.converter.VacancyConverter
import ru.practicum.android.diploma.presentation.favorites.viewmodel.FavoritesViewModel

val viewModelModule = module {
    viewModel {
        FavoritesViewModel(
            favoritesInteractor = get(),
            vacancyConverter = get()
        )
    }

    single {
        VacancyConverter(
            resourceProvider = get()
        )
    }
}
