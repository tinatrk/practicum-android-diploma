package ru.practicum.android.diploma.domain.favorites.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.favorites.api.interactor.FavoritesInteractor
import ru.practicum.android.diploma.domain.favorites.api.repository.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyBrief

class FavoritesInteractorImpl(
    private val favoritesRepository: FavoritesRepository
) : FavoritesInteractor {
    override suspend fun saveFavoriteVacancy(vacancy: Vacancy) {
        return favoritesRepository.saveFavoriteVacancy(vacancy)
    }

    override suspend fun deleteFavoriteVacancy(vacancy: Vacancy) {
        return favoritesRepository.deleteFavoriteVacancy(vacancy)
    }

    override fun getAllFavoriteVacancies(): Flow<List<VacancyBrief>> {
        return favoritesRepository.getAllFavoriteVacancies()
    }

    override fun markFavoriteVacancies(vacancies: List<VacancyBrief>): Flow<List<VacancyBrief>> {
        return favoritesRepository.markFavoriteVacancies(vacancies)
    }

    override fun getFavoriteVacancyById(vacancyId: Int): Flow<Vacancy> {
        return favoritesRepository.getFavoriteVacancyById(vacancyId)
    }
}
