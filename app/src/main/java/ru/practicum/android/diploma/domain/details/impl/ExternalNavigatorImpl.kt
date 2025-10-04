package ru.practicum.android.diploma.domain.details.impl

import android.content.Context
import android.content.Intent
import ru.practicum.android.diploma.domain.details.api.ExternalNavigator

class ExternalNavigatorImpl(
    private val context: Context
) : ExternalNavigator {

    override fun shareUrl(url: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}
