package ru.practicum.android.diploma.domain.details.api.interactor

interface ExternalNavigatorInteractor {

    fun shareVacancy(vacancyUrl: String)

    fun sendEmail(email: String)

    fun callToThePhone(phone: String)
}
