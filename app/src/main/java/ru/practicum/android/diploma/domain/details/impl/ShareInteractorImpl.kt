package ru.practicum.android.diploma.domain.details.impl

import ru.practicum.android.diploma.domain.details.api.ExternalNavigator
import ru.practicum.android.diploma.domain.details.api.interactor.ShareInteractor

class ShareInteractorImpl(
    private val externalNavigator: ExternalNavigator
) : ShareInteractor {

    override fun shareVacancy(vacancyUrl: String) {
        externalNavigator.shareUrl(vacancyUrl)
    }
}
