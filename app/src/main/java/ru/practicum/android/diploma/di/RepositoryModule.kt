package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.search.impl.VacancySearchRepositoryImpl
import ru.practicum.android.diploma.domain.search.api.repository.VacancySearchRepository

val repositoryModule = module {
    single<VacancySearchRepository> {
        VacancySearchRepositoryImpl(
            networkClient = get(),
            vacancyConverter = get()
        )
    }
}
