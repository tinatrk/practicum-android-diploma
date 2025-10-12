package ru.practicum.android.diploma.data.filters.impl

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.GsonBuilder
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.filters.api.repository.FilterStorageRepository
import ru.practicum.android.diploma.domain.models.filters.FilterSettings

class FilterStorageRepositoryImpl(
    private val sharedPrefs: SharedPreferences
) : FilterStorageRepository {
    override fun saveFilterSettings(filterSettings: FilterSettings) {
        val gson = GsonBuilder().serializeNulls().create()
        val json = gson.toJson(filterSettings)
        sharedPrefs.edit { putString(FILTER_SETTINGS_KEY, json) }
    }

    /*override fun saveFilterSettings(filterSettings: FilterSettings) {
        val json = Json.encodeToString(filterSettings)
        sharedPrefs.edit { putString(FILTER_SETTINGS_KEY, json) }
    }*/

    /*private fun getFilterSettingsInternal(): FilterSettings? {
        val json = sharedPrefs.getString(FILTER_SETTINGS_KEY, null)
        return if (json != null) {
            kotlin.runCatching { Json.decodeFromString<FilterSettings>(json) }.getOrDefault(null)
        } else {
            null
        }
    }*/

    private fun getFilterSettingsInternal(): FilterSettings? {
        val json = sharedPrefs.getString(FILTER_SETTINGS_KEY, null)
        val gson = GsonBuilder().serializeNulls().create()
        return if (json != null) {
            gson.fromJson(json, FilterSettings::class.java)
        } else {
            null
        }

    }

    override fun getFilterSettings(): Flow<FilterSettings?> = callbackFlow {
        trySend(getFilterSettingsInternal())

        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == FILTER_SETTINGS_KEY) trySend(getFilterSettingsInternal())
        }
        sharedPrefs.registerOnSharedPreferenceChangeListener(listener)
        awaitClose { sharedPrefs.unregisterOnSharedPreferenceChangeListener(listener) }
    }.map { it }

    override fun clearFilterSettings() {
        sharedPrefs.edit { remove(FILTER_SETTINGS_KEY) }
    }

    companion object {
        private const val FILTER_SETTINGS_KEY = "filter_settings_key"
    }
}
