package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import ru.practicum.android.diploma.data.db.dto.VacancyBriefDto
import ru.practicum.android.diploma.data.db.entity.VacancyEntity

@Dao
interface VacancyDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertVacancy(vacancy: VacancyEntity)

    @Delete
    suspend fun deleteVacancy(vacancy: VacancyEntity)

    @Query("SELECT * FROM vacancy WHERE vacancyId = :vacancyId")
    suspend fun getVacancyById(vacancyId: String): VacancyEntity?

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
    suspend fun getVacanciesPreview(): List<VacancyBriefDto>?

    @Query("SELECT EXISTS (SELECT 1 FROM vacancy WHERE vacancyId = :id)")
    fun exists(id: String): Boolean
}
