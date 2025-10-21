package ru.practicum.android.diploma.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> toDebounce(
    delayMillis: Long,
    coroutineScope: CoroutineScope,
    useLastParam: Boolean,
    action: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null

    return { param: T ->
        if (useLastParam) {
            debounceJob?.cancel()
        }

        if (debounceJob?.isCompleted != false || useLastParam) {
            debounceJob = coroutineScope.launch {
                delay(delayMillis)
                action(param)
            }
        }
    }
}

fun <A, B> afterDebounce(
    delayMillis: Long,
    coroutineScope: CoroutineScope,
    useLastParam: Boolean,
    action: (A, B?) -> Unit
): (A, B?) -> Unit {
    var debounceJob: Job? = null

    return { firstParam: A, secondParam: B?  ->
        if (useLastParam) {
            debounceJob?.cancel()
        }

        if (debounceJob?.isCompleted != false || useLastParam) {
            debounceJob = coroutineScope.launch {
                action(firstParam, secondParam)
                delay(delayMillis)
            }
        }
    }
}
