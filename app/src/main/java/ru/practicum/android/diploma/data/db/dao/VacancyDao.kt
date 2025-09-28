package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.data.db.dto.VacancyPreview

@Dao
interface VacancyDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertVacancy(vacancy: VacancyEntity)

    @Delete
    suspend fun deleteVacancy(vacancy: VacancyEntity)

    @Query("SELECT * FROM vacancy WHERE vacancyId = :vacancyId")
    fun getVacancyById(vacancyId: String): Flow<VacancyEntity?>

    @Query(
        """
            SELECT 
                vacancyId, 
                name, 
                address_city AS city, 
                employer_name AS employerName, 
                employer_logo AS employerLogo, 
                salary_from AS salaryFrom, 
                salary_to AS salaryTo, 
                salary_currency AS salaryCurrency 
                FROM vacancy 
                ORDER BY addedTime DESC
                """
    )
    fun getVacanciesPreview(): Flow<List<VacancyPreview>?>
}
