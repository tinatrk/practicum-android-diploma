package ru.practicum.android.diploma.ui.screen

import android.util.Log
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
    // navigationEvent
) {
    val screenState by viewModel.screenStateFlow.collectAsStateWithLifecycle()
    val navigationState =
        viewModel.navigationEvent.collectAsStateWithLifecycle(initialValue = NavigationEventToVacancy.Idle)

    LaunchedEffect(navigationState.value) {
        when (navigationState.value) {
            is NavigationEventToVacancy.OpenVacancyDetails -> {
                // navigateToVacancyDetails(vacancyId)
            }

            else -> {}
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
                Log.d("MyTag", "favoritesScreen -> loading")
                ProgressBar()
            }

            is FavoritesScreenState.Empty -> {
                Log.d("MyTag", "favoritesScreen -> empty")
                ErrorMessage(
                    title = stringResource(R.string.im_favorites_empty_description),
                    imageId = R.drawable.im_favorites_empty,
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                )
            }

            is FavoritesScreenState.Error -> {
                Log.d("MyTag", "favoritesScreen -> error")
                ErrorMessage(
                    title = stringResource(R.string.im_lack_of_list_cat_description),
                    imageId = R.drawable.im_lack_of_list_cat,
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                )
            }

            is FavoritesScreenState.Content -> {
                Log.d("MyTag", "favoritesScreen -> content")
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
