package ru.practicum.android.diploma.ui.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.LocalCustomColors

@Composable
fun Logo(url: String?) {
    AsyncImage(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(url)
            .setHeader("User-Agent", "Example/1.0")
            .decoderFactory(SvgDecoder.Factory())
            .listener(
                onSuccess = { _, _ -> Log.d("IMG", "Download success") },
                onError = { _, t -> Log.e("IMG", "Download error", t.throwable) }
            )
            .build(),
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
