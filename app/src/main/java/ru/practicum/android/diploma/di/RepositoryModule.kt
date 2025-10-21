package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.details.impl.ExternalNavigatorRepositoryImpl
import ru.practicum.android.diploma.data.details.impl.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.data.favorites.impl.FavoritesRepositoryImpl
import ru.practicum.android.diploma.data.filters.impl.FilterRepositoryImpl
import ru.practicum.android.diploma.data.filters.impl.FilterStorageRepositoryImpl
import ru.practicum.android.diploma.data.search.impl.VacancySearchRepositoryImpl
import ru.practicum.android.diploma.domain.details.api.repository.ExternalNavigatorRepository
import ru.practicum.android.diploma.domain.details.api.repository.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.favorites.api.repository.FavoritesRepository
import ru.practicum.android.diploma.domain.filters.api.repository.FilterRepository
import ru.practicum.android.diploma.domain.filters.api.repository.FilterStorageRepository
import ru.practicum.android.diploma.domain.search.api.repository.VacancySearchRepository

val repositoryModule = module {
    factory<VacancySearchRepository> {
        VacancySearchRepositoryImpl(
            networkClient = get(),
            vacancyConverter = get()
        )
    }

    factory<FavoritesRepository> {
        FavoritesRepositoryImpl(
            vacancyDao = get(),
            vacancyConverter = get()
        )
    }

    factory<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(
            networkClient = get(),
            vacancyConverter = get()
        )
    }

    factory<ExternalNavigatorRepository> {
        ExternalNavigatorRepositoryImpl(
            context = androidContext()
        )
    }

    factory<FilterStorageRepository> {
        FilterStorageRepositoryImpl(
            sharedPrefs = get(),
            gson = get()
        )
    }

    factory<FilterRepository> {
        FilterRepositoryImpl(
            networkClient = get(),
            areaExtractor = get(),
            industryConverter = get()
        )
    }
}
