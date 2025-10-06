package ru.practicum.android.diploma.data.details.repository

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import ru.practicum.android.diploma.domain.details.api.repository.ExternalNavigatorRepository

class ExternalNavigatorRepositoryImpl(
    private val context: Context
) : ExternalNavigatorRepository {

    override fun shareUrl(url: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    override fun openEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.apply {
            data = "mailto:".toUri()
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    override fun openPhone(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.apply {
            data = "tel:$phone".toUri()
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}
