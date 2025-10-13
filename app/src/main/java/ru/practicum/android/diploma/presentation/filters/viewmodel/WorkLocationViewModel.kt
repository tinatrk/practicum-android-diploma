package ru.practicum.android.diploma.presentation.filters.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.domain.models.filters.FilterAddress
import ru.practicum.android.diploma.domain.models.filters.FilterCountry
import ru.practicum.android.diploma.domain.models.filters.FilterRegion
import ru.practicum.android.diploma.ui.navigation.util.NavResultKeys

class WorkLocationViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Сделала так для простоты, по-хорошему нужен один state
    val selectedCountry: StateFlow<FilterCountry?> =
        savedStateHandle.getStateFlow(NavResultKeys.SELECTED_COUNTRY, null)
    val selectedRegion: StateFlow<FilterRegion?> =
        savedStateHandle.getStateFlow(NavResultKeys.SELECTED_REGION, null)

    private val _shouldFinish = MutableStateFlow(false)
    val shouldFinish: StateFlow<Boolean> = _shouldFinish

    // здесь я передаю параметер из ui для простоты, по-хорошему параметр нужно брать из viewModel
    fun onReturnWithParam(param: FilterAddress) {
        savedStateHandle[NavResultKeys.SELECTED_WORK_ADDRESS] = param
        _shouldFinish.value = true
    }

    fun onReturnWithoutParam() {
        savedStateHandle[NavResultKeys.SELECTED_WORK_ADDRESS] = null
        _shouldFinish.value = true
    }

    fun consumeFinishEvent() {
        _shouldFinish.value = false
    }
}
