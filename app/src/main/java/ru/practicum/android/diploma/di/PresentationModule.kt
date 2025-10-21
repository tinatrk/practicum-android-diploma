package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.mappers.FilterConverter

val presentationModule = module {
    singleOf(::FilterConverter)
}
