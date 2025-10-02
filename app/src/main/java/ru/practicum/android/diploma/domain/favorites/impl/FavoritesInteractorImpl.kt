package ru.practicum.android.diploma.domain.favorites.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.favorites.api.interactor.FavoritesInteractor
import ru.practicum.android.diploma.domain.favorites.api.repository.FavoritesRepository

class FavoritesInteractorImpl(
    private val favoritesRepository: FavoritesRepository
) : FavoritesInteractor {
    override suspend fun saveFavoriteVacancy(vacancy: VacancyDetails) {
        return favoritesRepository.saveFavoriteVacancy(vacancy)
    }

    override suspend fun deleteFavoriteVacancy(vacancy: VacancyDetails) {
        return favoritesRepository.deleteFavoriteVacancy(vacancy)
    }

    override fun getAllFavoriteVacancies(): Flow<List<Vacancy>> {
        return favoritesRepository.getAllFavoriteVacancies()
    }

    override fun markFavoriteVacancies(vacancies: List<Vacancy>): Flow<List<Vacancy>> {
        return favoritesRepository.markFavoriteVacancies(vacancies)
    }
}
