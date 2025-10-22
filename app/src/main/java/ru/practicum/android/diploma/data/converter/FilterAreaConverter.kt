package ru.practicum.android.diploma.data.converter

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.data.db.embedd.FilterAreaEmbeddable
import ru.practicum.android.diploma.data.dto.models.FilterAreaDto
import ru.practicum.android.diploma.domain.models.vacancy.FilterArea
import java.lang.reflect.Type

class FilterAreaConverter(private val gson: Gson) {
    fun map(areaDto: FilterAreaDto?): FilterArea? {
        return areaDto?.let {
            FilterArea(
                id = it.id,
                name = it.name,
                parentId = it.parentId,
                areas = it.areas?.mapNotNull { areaDto ->
                    map(areaDto)
                }
            )
        }
    }

    fun map(filterArea: FilterAreaEmbeddable?): FilterArea? {
        return filterArea?.let {
            FilterArea(
                id = filterArea.id,
                name = filterArea.name,
                parentId = filterArea.parentId,
                areas = getListFromString(filterArea.areas)
            )
        }
    }

    fun map(filterArea: FilterArea?): FilterAreaEmbeddable? {
        return filterArea?.let {
            FilterAreaEmbeddable(
                id = filterArea.id,
                name = filterArea.name ?: EMPTY_STRING,
                parentId = filterArea.parentId ?: UNKNOWN_ID,
                areas = convertListToString(filterArea.areas) ?: EMPTY_STRING
            )
        }
    }

    private fun convertListToString(list: List<FilterArea>?): String? {
        return list?.let { gson.toJson(list) }
    }

    private fun getListFromString(str: String): List<FilterArea>? {
        val type: Type = object : TypeToken<List<FilterArea>>() {}.type
        return try {
            gson.fromJson(str, type)
        } catch (e: JsonSyntaxException) {
            Log.e(VACANCY_CONVERTER_TAG, "Не удалось получить список из строки (json)")
            null
        }
    }

    companion object {
        private const val EMPTY_STRING = ""
        private const val UNKNOWN_ID = -1
        private const val VACANCY_CONVERTER_TAG = "VacancyConverter"
    }
}
