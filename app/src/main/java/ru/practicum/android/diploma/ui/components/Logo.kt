package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.LocalCustomColors

@Composable
fun Logo(url: String?) {
    AsyncImage(
        model = url ?: "",
        contentDescription = stringResource(R.string.logo_description),
        placeholder = painterResource(R.drawable.ic_placeholder_48px),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = LocalCustomColors.current.vacancyListItemColors.logoBorder,
                shape = RoundedCornerShape(12.dp)
            ),
        error = painterResource(R.drawable.ic_placeholder_48px)
    )
}
