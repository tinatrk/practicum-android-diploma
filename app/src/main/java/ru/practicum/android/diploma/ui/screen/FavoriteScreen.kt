package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.toImmutableList
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.favorites.models.FavoritesScreenState
import ru.practicum.android.diploma.presentation.favorites.viewmodel.FavoritesViewModel
import ru.practicum.android.diploma.presentation.models.NavigationEventToVacancy
import ru.practicum.android.diploma.ui.components.ErrorMessage
import ru.practicum.android.diploma.ui.components.ProgressBar
import ru.practicum.android.diploma.ui.components.SimpleVacancyList
import ru.practicum.android.diploma.ui.components.topbar.SimpleTopBar
import ru.practicum.android.diploma.ui.theme.LocalCustomColors

@Composable
fun FavoriteScreen(
    viewModel: FavoritesViewModel = koinViewModel(),
    navigateToVacancy: (String) -> Unit
) {
    val screenState by viewModel.screenStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is NavigationEventToVacancy.OpenVacancyDetails ->
                    navigateToVacancy(event.vacancyId)
                NavigationEventToVacancy.Idle -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            SimpleTopBar(
                title = stringResource(R.string.favorites_screen_title)
            )
        },
        containerColor = LocalCustomColors.current.screenBackground
    ) {
        when (screenState) {
            is FavoritesScreenState.Loading -> {
                ProgressBar()
            }

            is FavoritesScreenState.Empty -> {
                ErrorMessage(
                    title = stringResource(R.string.im_favorites_empty_description),
                    imageId = R.drawable.im_favorites_empty,
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                )
            }

            is FavoritesScreenState.Error -> {
                ErrorMessage(
                    title = stringResource(R.string.im_lack_of_list_cat_description),
                    imageId = R.drawable.im_lack_of_list_cat,
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                )
            }

            is FavoritesScreenState.Content -> {
                SimpleVacancyList(
                    vacancies = (screenState as FavoritesScreenState.Content).data.toImmutableList(),
                    onVacancyClick = viewModel::onVacancyClick,
                    modifier = Modifier
                        .padding(it)
                        .padding(top = 16.dp)
                )
            }
        }
    }
}
