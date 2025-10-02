package ru.practicum.android.diploma.domain.favorites.api.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetails

interface FavoritesInteractor {
    suspend fun saveFavoriteVacancy(vacancy: VacancyDetails)

    suspend fun deleteFavoriteVacancy(vacancy: VacancyDetails)

    fun getAllFavoriteVacancies(): Flow<List<Vacancy>>

    fun markFavoriteVacancies(vacancies: List<Vacancy>): Flow<List<Vacancy>>

    fun getFavoriteVacancyById(vacancyId: Int): Flow<VacancyDetails>
}
