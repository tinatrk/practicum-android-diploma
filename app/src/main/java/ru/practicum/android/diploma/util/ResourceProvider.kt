package ru.practicum.android.diploma.util

import android.content.Context
import androidx.annotation.StringRes

class ResourceProvider(private val context: Context) {
    fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }

    fun getString(@StringRes stringResId: Int, vararg strParams: String): String {
        return context.getString(stringResId, *strParams)
    }
}
