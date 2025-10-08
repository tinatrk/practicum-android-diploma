package ru.practicum.android.diploma.domain.favorites.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.favorites.api.interactor.FavoritesInteractor
import ru.practicum.android.diploma.domain.favorites.api.repository.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyBrief
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource

class FavoritesInteractorImpl(
    private val favoritesRepository: FavoritesRepository
) : FavoritesInteractor {
    override suspend fun saveFavoriteVacancy(vacancy: Vacancy) {
        return favoritesRepository.saveFavoriteVacancy(vacancy)
    }

    override suspend fun deleteFavoriteVacancy(vacancy: Vacancy) {
        return favoritesRepository.deleteFavoriteVacancy(vacancy)
    }

    override fun getAllFavoriteVacancies(): Flow<Resource<List<VacancyBrief>, Failure>> {
        return favoritesRepository.getAllFavoriteVacancies()
    }

    override fun getFavoriteVacancyById(vacancyId: String): Flow<Resource<Vacancy, Failure>> {
        return favoritesRepository.getFavoriteVacancyById(vacancyId)
    }

    override fun isVacancyFavorite(vacancyId: String): Flow<Boolean> {
        return favoritesRepository.isVacancyFavorite(vacancyId)
    }
}
