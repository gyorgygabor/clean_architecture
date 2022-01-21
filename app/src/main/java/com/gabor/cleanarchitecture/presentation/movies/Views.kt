package com.gabor.cleanarchitecture.presentation.movies

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.gabor.cleanarchitecture.R
import com.gabor.cleanarchitecture.presentation.utils.LogCompositions
import com.gabor.cleanarchitecture.presentation.utils.views.isScrolledToEnd


@ExperimentalAnimationApi
@Composable
fun MoviesListView(
    listItems: List<MovieViewItem>,
    onBottomReached: () -> Unit,
    openDetailsListener: (MovieViewItem) -> Unit
) {
    LogCompositions(TAG, "MoviesListView called ${listItems.size}")
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        items(listItems) { movie ->
            Card(
                shape = RoundedCornerShape(8.dp),
                backgroundColor = MaterialTheme.colors.surface,
                modifier = Modifier
                    .padding(8.dp)
                    .height(150.dp)
                    .fillMaxWidth()
                    .clickable {
                        openDetailsListener(movie)
                    }
            ) {
                Row() {
                    Image(
                        painter = rememberImagePainter(data = movie.posterImageUrl, builder = {
                            crossfade(true)
                            placeholder(R.drawable.ic_placeholder_movie)
                        }),
                        contentDescription = null,
                        modifier = Modifier.requiredHeight(150.dp),
                        contentScale = ContentScale.Fit
                    )
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.subtitle1.plus(
                                TextStyle(
                                    fontWeight = FontWeight.Bold
                                )
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis

                        )
                        Text(
                            text = movie.description,
                            style = MaterialTheme.typography.body2,
                            maxLines = 5,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }

    // Show the button if the first visible item is past
    // the first item. We use a remembered derived state to
    // minimize unnecessary compositions
    val isBottomReached by remember {
        derivedStateOf {
            listState.isScrolledToEnd()
        }
    }
    AnimatedVisibility(visible = isBottomReached, enter = fadeIn(), exit = fadeOut()) {
        Loader()
    }
    if (isBottomReached) {
        onBottomReached()
    }
    // todo at the first bottom reach, onBottomReached is called twice so 60 items will be loaded. Investigate why.
}

@Composable
fun Loader() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        CircularProgressIndicator(color = MaterialTheme.colors.primary)
    }
}

@Composable
fun EmptyListView(empty: Boolean) {
    if (empty) {
        Image(
            painter = painterResource(R.drawable.ic_no_collections),
            contentDescription = null,
            modifier = Modifier.requiredSize(120.dp)
        )
    }
}


@ExperimentalAnimationApi
@Preview(name = "S9 Preview", widthDp = 360, heightDp = 740)
@Composable
fun MoviesPreview(
    @PreviewParameter(MoviesPreviewParameterProvider::class) listItems: List<MovieViewItem>
) {
    MoviesListView(listItems, {}, {})
}
