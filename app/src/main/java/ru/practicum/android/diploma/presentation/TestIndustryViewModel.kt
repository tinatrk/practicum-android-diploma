package ru.practicum.android.diploma.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.domain.models.filters.FilterIndustry
import ru.practicum.android.diploma.ui.navigation.util.NavResultKeys

class TestIndustryViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // к сожалению, эту часть нужно повторять для каждого экрана с возвратом параметра
    private val _shouldFinish = MutableStateFlow(false)
    val shouldFinish: StateFlow<Boolean> = _shouldFinish

    fun onReturnWithParam() {
        savedStateHandle[NavResultKeys.SELECTED_INDUSTRY] = FilterIndustry(id = 1, name = "MyIndustry")
        _shouldFinish.value = true
    }

    fun onReturnWithoutParam() {
        savedStateHandle[NavResultKeys.SELECTED_INDUSTRY] = null
        _shouldFinish.value = true
    }

    fun consumeFinishEvent() {
        _shouldFinish.value = false
    }
}
