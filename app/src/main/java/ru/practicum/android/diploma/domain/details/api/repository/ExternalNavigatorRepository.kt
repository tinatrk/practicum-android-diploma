package ru.practicum.android.diploma.domain.details.api.repository

interface ExternalNavigatorRepository {

    fun shareUrl(url: String)

    fun openEmail(email: String)

    fun openPhone(phone: String)
}
