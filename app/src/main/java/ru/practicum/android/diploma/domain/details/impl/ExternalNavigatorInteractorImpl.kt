package ru.practicum.android.diploma.domain.details.impl

import ru.practicum.android.diploma.domain.details.api.interactor.ExternalNavigatorInteractor
import ru.practicum.android.diploma.domain.details.api.repository.ExternalNavigatorRepository

class ExternalNavigatorInteractorImpl(
    private val externalNavigator: ExternalNavigatorRepository
) : ExternalNavigatorInteractor {

    override fun shareVacancy(vacancyUrl: String) {
        externalNavigator.shareUrl(vacancyUrl)
    }

    override fun sendEmail(email: String) {
        externalNavigator.openEmail(email)
    }

    override fun callToThePhone(phone: String) {
        externalNavigator.openPhone(phone)
    }
}
