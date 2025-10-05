package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.search.api.interactor.VacancySearchInteractor
import ru.practicum.android.diploma.domain.search.impl.VacancySearchInteractorImpl

val interactorModule = module {
    single<VacancySearchInteractor> {
        VacancySearchInteractorImpl(
            repository = get()
        )
    }
}
