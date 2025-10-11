package ru.practicum.android.diploma.data.favorites.impl

import android.database.sqlite.SQLiteException
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.converter.VacancyConverter
import ru.practicum.android.diploma.data.db.AppDatabase
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

    override fun getAllFavoriteVacancies(): Flow<Resource<List<VacancyBrief>, Failure>> =
        appDatabase.vacancyDao().getVacanciesPreview()
            .map { vacancies ->
                if (!vacancies.isNullOrEmpty()) {
                    Resource.Success(data = vacancies.map { vacancyConverter.map(it) })
                } else {
                    Resource.Error(error = Failure.NotFound as Failure)
                }
            }
            .catch {
                Log.e(DATABASE_TAG, "Ошибка при получении списка вакансий")
                emit(Resource.Error(error = Failure.BadRequest as Failure))
            }

    override fun isVacancyFavorite(vacancyId: String): Flow<Boolean> {
        return appDatabase.vacancyDao().exists(vacancyId)
            .catch {
                Log.e(DATABASE_TAG, "Ошибка при проверке наличия вакансии в избранных")
                emit(false)
            }
    }

    override fun getFavoriteVacancyById(vacancyId: String): Flow<Resource<Vacancy, Failure>> =
        appDatabase.vacancyDao().getVacancyById(vacancyId)
            .map { vacancy ->
                if (vacancy != null) {
                    Resource.Success(data = vacancyConverter.map(vacancy))
                } else {
                    Resource.Error(error = Failure.NotFound as Failure)
                }
            }
            .catch {
                Log.e(DATABASE_TAG, "Ошибка при получении вакансии по id")
                emit(Resource.Error(error = Failure.BadRequest as Failure))
            }

    companion object {
        private const val DATABASE_TAG = "DatabaseError"
    }
}
