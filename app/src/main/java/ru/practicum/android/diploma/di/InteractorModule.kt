package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.details.api.interactor.ExternalNavigatorInteractor
import ru.practicum.android.diploma.domain.details.api.interactor.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.details.impl.ExternalNavigatorInteractorImpl
import ru.practicum.android.diploma.domain.details.impl.VacancyDetailsInteractorImpl
import ru.practicum.android.diploma.domain.favorites.api.interactor.FavoritesInteractor
import ru.practicum.android.diploma.domain.favorites.impl.FavoritesInteractorImpl
import ru.practicum.android.diploma.domain.filters.api.interactor.FilterInteractor
import ru.practicum.android.diploma.domain.filters.api.interactor.FilterStorageInteractor
import ru.practicum.android.diploma.domain.filters.impl.FilterInteractorImpl
import ru.practicum.android.diploma.domain.filters.impl.FilterStorageInteractorImpl
import ru.practicum.android.diploma.domain.search.api.interactor.VacancySearchInteractor
import ru.practicum.android.diploma.domain.search.impl.VacancySearchInteractorImpl

val interactorModule = module {
    factory<VacancySearchInteractor> {
        VacancySearchInteractorImpl(
            repository = get()
        )
    }

    factory<FavoritesInteractor> {
        FavoritesInteractorImpl(
            favoritesRepository = get()
        )
    }

    factory<VacancyDetailsInteractor> {
        VacancyDetailsInteractorImpl(
            repository = get()
        )
    }

    factory<ExternalNavigatorInteractor> {
        ExternalNavigatorInteractorImpl(
            externalNavigator = get()
        )
    }

    factory<FilterStorageInteractor> {
        FilterStorageInteractorImpl(
            filterStorageRepository = get()
        )
    }

    factory<FilterInteractor> {
        FilterInteractorImpl(
            filterRepository = get()
        )
    }
}
