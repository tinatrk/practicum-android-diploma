package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.converter.VacancyConverter
import ru.practicum.android.diploma.presentation.favorites.viewmodel.FavoritesViewModel
import ru.practicum.android.diploma.presentation.search.viewmodel.SearchViewModel

val viewModelModule = module {
    viewModelOf(::SearchViewModel)

    viewModelOf(::FavoritesViewModel)

    singleOf(::VacancyConverter)
}
