package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.details.api.interactor.ExternalNavigatorInteractor
import ru.practicum.android.diploma.domain.details.api.interactor.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.details.impl.ExternalNavigatorInteractorImpl
import ru.practicum.android.diploma.domain.details.impl.VacancyDetailsInteractorImpl
import ru.practicum.android.diploma.domain.favorites.api.interactor.FavoritesInteractor
import ru.practicum.android.diploma.domain.favorites.impl.FavoritesInteractorImpl
import ru.practicum.android.diploma.domain.search.api.interactor.VacancySearchInteractor
import ru.practicum.android.diploma.domain.search.impl.VacancySearchInteractorImpl

val interactorModule = module {
    single<VacancySearchInteractor> {
        VacancySearchInteractorImpl(
            repository = get()
        )
    }

    single<FavoritesInteractor> {
        FavoritesInteractorImpl(
            favoritesRepository = get()
        )
    }

    single<VacancyDetailsInteractor> {
        VacancyDetailsInteractorImpl(
            repository = get()
        )
    }

    single<ExternalNavigatorInteractor> {
        ExternalNavigatorInteractorImpl(
            externalNavigator = get()
        )
    }
}
