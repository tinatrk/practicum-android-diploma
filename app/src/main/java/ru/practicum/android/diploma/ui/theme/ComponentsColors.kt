package ru.practicum.android.diploma.ui.theme

import androidx.compose.ui.graphics.Color

data class CustomColors(
    val screenBackground: Color,
    val searchEditTextColors: SearchEditTextColors,
    val bottomNavigationColors: BottomNavigationColors,
    val progressBarColor: Color,
    val searchCountResultMessageColors: SearchCountResultMessageColors,
    val vacancyListItemColors: VacancyListItemColors,
    val filterTextWithIconBlockColors: FilterTextWithIconBlockColors,
    val salaryEditTextColors: SalaryEditTextColors,
    val topBarColors: TopBarColors,
    val buttonColors: ButtonColors,
    // плашка на странице деталей вакансии
    val vacancyInfoCard: VacancyInfoCard,
    val bottomSheetColors: BottomSheetColors,
    val commonTextColor: Color
)

data class SearchEditTextColors(
    val background: Color,
    val hint: Color,
    val text: Color,
    val label: Color,
    val cursor: Color,
    val iconTint: Color
)

data class BottomNavigationColors(
    val background: Color,
    val activeIconAndText: Color,
    val inactiveIconAndText: Color
)

data class SearchCountResultMessageColors(
    val background: Color,
    val text: Color
)

data class VacancyListItemColors(
    val background: Color,
    val title: Color,
    val textInfo: Color
)

// Блоки с текстом и иконкой на экранах фильтрации
data class FilterTextWithIconBlockColors(
    // это текст, нажатие на который ведет на экран для выбора страны/региона/отрасли (на макетах в светлой теме он сервый)
    val defaultFilterItem: FilterTextWithIconBlockStateColors,
    // текст с флагом "не показывать без зарплаты"
    val showWithoutSalaryStateColors: FilterTextWithIconBlockStateColors,
    // это текст с конкретным выбором пользователя (на макетах в светлой теме он черный)
    val userChoiceAddressState: FilterTextWithIconBlockStateColors,
    val userChoiceIndustryState: FilterTextWithIconBlockStateColors,
)

data class FilterTextWithIconBlockStateColors(
    val background: Color,
    val text: Color,
    val iconTint: Color
)

data class SalaryEditTextColors(
    val background: Color,
    val hint: Color,
    val text: Color,
    val labelState: SalaryEditTextLabelStateColors
)

data class SalaryEditTextLabelStateColors(
    val unfocusedAndTextEmpty: Color,
    val unfocusedAndTextNotEmpty: Color,
    val focused: Color
)

data class TopBarColors(
    val background: Color,
    val text: Color,
    val iconType: IconTypeColors
)

data class IconTypeColors(
    val commonIconTint: Color,
    val filterStateColors: IconState,
    val favoriteStateColors: IconState
)

data class IconState(
    val activeIcon: IconStateColors,
    val inactiveIcon: IconStateColors
)

data class IconStateColors(
    val background: Color,
    val tint: Color
)

data class ButtonColors(
    val commonButtonColors: ButtonTypeColors,
    val resetFilterButtonColors: ButtonTypeColors
)

data class ButtonTypeColors(
    val background: Color,
    val text: Color
)

// плашка на странице деталей вакансии
data class VacancyInfoCard(
    val background: Color,
    val text: Color
)

data class BottomSheetColors(
    val background: Color,
    val screenBlackout: Color
)
