package ru.practicum.android.diploma.data.favorites.impl

import android.database.sqlite.SQLiteException
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.coverter.VacancyConverter
import ru.practicum.android.diploma.domain.favorites.api.repository.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyBrief
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource

class FavoritesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val vacancyConverter: VacancyConverter
) : FavoritesRepository {

    override suspend fun saveFavoriteVacancy(vacancy: Vacancy) {
        try {
            appDatabase.vacancyDao().insertVacancy(vacancyConverter.map(vacancy))
        } catch (e: SQLiteException) {
            Log.e(DATABASE_TAG, "Ошибка при вставке вакансии")
        }

    }

    override suspend fun deleteFavoriteVacancy(vacancy: Vacancy) {
        try {
            appDatabase.vacancyDao().deleteVacancy(vacancyConverter.map(vacancy))
        } catch (e: SQLiteException) {
            Log.e(DATABASE_TAG, "Ошибка при удалении вакансии")
        }
    }

    override fun getAllFavoriteVacancies(): Flow<Resource<List<VacancyBrief>, Failure>> = flow {
        try {
            val vacancyBriefDtoList = appDatabase.vacancyDao().getVacanciesPreview()
            if (vacancyBriefDtoList != null) {
                emit(Resource.Success(data = vacancyBriefDtoList.map { vacancyConverter.map(it) }))
            } else {
                emit(Resource.Error(error = Failure.NotFound))
            }
        } catch (e: SQLiteException) {
            Log.e(DATABASE_TAG, "Ошибка при получении списка вакансий")
            emit(Resource.Error(error = Failure.BadRequest))
        }
    }

    override fun isVacancyFavorite(vacancyId: String): Boolean {
        try {
            return appDatabase.vacancyDao().exists(vacancyId)
        } catch (e: SQLiteException) {
            Log.e(DATABASE_TAG, "Ошибка при проверке наличия вакансии в избранных")
            return false
        }

    }

    override fun getFavoriteVacancyById(vacancyId: String): Flow<Resource<Vacancy, Failure>> =
        flow {
            try {
                val vacancyEntity = appDatabase.vacancyDao().getVacancyById(vacancyId = vacancyId)
                if (vacancyEntity != null) {
                    emit(Resource.Success(data = vacancyConverter.map(vacancyEntity)))
                } else {
                    emit(Resource.Error(error = Failure.NotFound))
                }
            } catch (e: SQLiteException) {
                Log.e(DATABASE_TAG, "Ошибка при получении вакансии по id")
                emit(Resource.Error(error = Failure.BadRequest))
            }
        }

    companion object {
        private const val DATABASE_TAG = "DatabaseError"
    }
}
