package ru.practicum.android.diploma.domain.details.impl

import ru.practicum.android.diploma.domain.details.api.repository.ExternalNavigatorRepository
import ru.practicum.android.diploma.domain.details.api.interactor.ShareInteractor

class ShareInteractorImpl(
    private val externalNavigator: ExternalNavigatorRepository
) : ShareInteractor {

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
