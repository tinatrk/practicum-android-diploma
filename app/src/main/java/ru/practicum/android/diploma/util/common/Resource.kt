package ru.practicum.android.diploma.util.common

sealed class Resource<out T, out E> {
    data class Success<T>(val data: T): Resource<T, Nothing>()
    data class Error<E>(val error: E): Resource<Nothing, E>()
}
