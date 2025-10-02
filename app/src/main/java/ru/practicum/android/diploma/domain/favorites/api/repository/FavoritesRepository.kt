package ru.practicum.android.diploma.domain.favorites.api.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyBrief

interface FavoritesRepository {
    suspend fun saveFavoriteVacancy(vacancy: Vacancy)

    suspend fun deleteFavoriteVacancy(vacancy: Vacancy)

    fun getAllFavoriteVacancies(): Flow<List<VacancyBrief>>

    fun markFavoriteVacancies(vacancies: List<VacancyBrief>): Flow<List<VacancyBrief>>

    fun getFavoriteVacancyById(vacancyId: Int): Flow<Vacancy>
}
