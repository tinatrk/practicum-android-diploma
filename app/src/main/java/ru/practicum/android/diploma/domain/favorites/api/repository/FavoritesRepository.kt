package ru.practicum.android.diploma.domain.favorites.api.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyBrief
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource

interface FavoritesRepository {
    suspend fun saveFavoriteVacancy(vacancy: Vacancy)

    suspend fun deleteFavoriteVacancy(vacancy: Vacancy)

    fun getAllFavoriteVacancies(): Flow<Resource<List<VacancyBrief>, Failure>>

    fun getFavoriteVacancyById(vacancyId: String): Flow<Resource<Vacancy, Failure>>

    fun isVacancyFavorite(vacancyId: String): Boolean
}
