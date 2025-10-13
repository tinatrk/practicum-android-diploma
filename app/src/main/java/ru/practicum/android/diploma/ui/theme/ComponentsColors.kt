package ru.practicum.android.diploma.ui.theme

import androidx.compose.ui.graphics.Color

data class CustomColors(
    val screenBackground: Color,
    val commonTextColor: Color,
    val commonIconColor: Color,
    val topBarColors: TopBarColors,
    val bottomNavigationColors: BottomNavigationColors,
    val searchEditTextColors: SearchEditTextColors,
    val progressBarColor: Color,
    val searchCountResultChipColors: SearchCountResultChipColors,
    val vacancyListItemColors: VacancyListItemColors,
    val filterListItemColors: FilterListItemColors,
    val textFieldColors: TextFieldColors,
    // плашка на странице деталей вакансии
    val vacancyInfoCard: VacancyInfoCard,
    val buttonColors: ButtonColors,
    val bottomSheetColors: BottomSheetColors,
    val toastColors: ToastColors,
    val vacancyDetailsColors: VacancyDetailsColors
)

data class SearchEditTextColors(
    val background: Color,
    val hint: Color,
    val text: Color,
    val cursor: Color,
    val iconTint: Color
)

data class BottomNavigationColors(
    val background: Color,
    val activeIconAndText: Color,
    val inactiveIconAndText: Color,
    val divider: Color
)

data class SearchCountResultChipColors(
    val background: Color,
    val text: Color
)

data class VacancyListItemColors(
    val background: Color,
    val title: Color,
    val textInfo: Color,
    val logoBorder: Color
)

// Блоки с текстом и иконкой на экранах фильтрации
data class FilterListItemColors(
    val background: Color,
    val headlineContent: Color,
    val overlineContent: Color,
    val trailingIcon: Color,
    // это текст, нажатие на который ведет на экран для выбора страны/региона/отрасли
    // (на макетах в светлой теме он сервый с иконкой >)
    val defaultFilterItem: FilterListItemStateColors,
    // это текст с экранов "выбор страны/региона" (на макетах в светлой теме он черный с иконкой >)
    val addressItem: FilterListItemStateColors,
    // это текст с конкретным выбором пользователя (на макетах в светлой теме он черный с иконкой X)
    val userChoice: FilterListItemStateColors,
    // текст с флагом "не показывать без зарплаты" или выбор отрасли
    val filterItemWithCheckBox: FilterListItemStateColors,

)

data class FilterListItemStateColors(
    val background: Color,
    val text: Color,
    val iconTint: Color
)

data class TextFieldColors(
    val background: Color,
    val hint: Color,
    val text: Color,
    val labelState: TextFieldLabelStateColors
)

data class TextFieldLabelStateColors(
    val unfocusedAndTextEmpty: Color,
    val unfocusedAndTextNotEmpty: Color,
    val focused: Color
)

data class TopBarColors(
    val background: Color,
    val text: Color,
    val iconBaseTint: Color,
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

data class ToastColors(
    val background: Color,
    val text: Color
)

data class VacancyDetailsColors(
    val background: Color,
    val text: Color
)
