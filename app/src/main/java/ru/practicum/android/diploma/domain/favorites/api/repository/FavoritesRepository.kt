package ru.practicum.android.diploma.domain.favorites.api.repository

import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun saveFavoriteVacancy(vacancy: VacancyDetails)

    suspend fun deleteFavoriteVacancy(vacancy: VacancyDetails)

    fun getAllFavoriteVacancies(): Flow<List<Vacancy>>

    fun markFavoriteVacancies(vacancies: List<Vacancy>): Flow<List<Vacancy>>
}
