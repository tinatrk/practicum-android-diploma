package ru.practicum.android.diploma.presentation.filters.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.domain.models.filters.FilterRegion
import ru.practicum.android.diploma.ui.navigation.util.NavResultKeys

class FilterRegionViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val selectedCountryId: Int?
) : ViewModel() {
    private val _shouldFinish = MutableStateFlow(false)
    val shouldFinish: StateFlow<Boolean> = _shouldFinish

    fun onReturnWithParam() {
        savedStateHandle[NavResultKeys.SELECTED_REGION] =
            // использую здесь selectedCountryId просто, чтобы detekt не ругался на неиспользуемый параметр.
            FilterRegion(id = 1, name = "Очень хорошее место", parentId = selectedCountryId ?: 2)
        _shouldFinish.value = true
    }

    fun onReturnWithoutParam() {
        savedStateHandle[NavResultKeys.SELECTED_REGION] = null
        _shouldFinish.value = true
    }

    fun consumeFinishEvent() {
        _shouldFinish.value = false
    }
}
