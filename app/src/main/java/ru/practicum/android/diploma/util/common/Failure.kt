package ru.practicum.android.diploma.util.common

sealed interface Failure {
    data object Network : Failure
    data object BadRequest : Failure
    data object NotFound : Failure
    data class Unknown(val cause: Throwable? = null) : Failure
}
