package ru.practicum.android.diploma.util.common

import androidx.compose.runtime.Immutable

@Immutable
sealed interface Failure {
    data object Network : Failure
    data object BadRequest : Failure
    data object NotFound : Failure
    data class Unknown(val cause: Throwable? = null) : Failure
}
