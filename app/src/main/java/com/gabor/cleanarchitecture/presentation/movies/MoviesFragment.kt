package com.gabor.cleanarchitecture.presentation.movies

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import coil.compose.rememberImagePainter
import com.gabor.cleanarchitecture.R
import com.gabor.cleanarchitecture.presentation.utils.statehandler.*
import dagger.hilt.android.AndroidEntryPoint

import com.gabor.cleanarchitecture.presentation.utils.LocaleUtils
import com.gabor.cleanarchitecture.presentation.utils.toggleProgressBar


/**
 * Movies list screen.
 */

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val viewModel by viewModels<MoviesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    val viewState by viewModel.viewState.observeAsState()

                    MoviesListView(viewState?.listItems.orEmpty())
                    EmptyListView(viewState?.listItems.isNullOrEmpty())
                }
            }
        }

    }

    @Composable
    private fun EmptyListView(empty: Boolean) {
        if (empty) {
            Image(
                painter = painterResource(R.drawable.ic_no_collections),
                contentDescription = null,
                modifier = Modifier.requiredSize(120.dp)
            )
        }
    }

    @Composable
    private fun MoviesListView(listItems: List<MovieViewItem>) {
        LazyColumn {
            items(listItems) { movie ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = MaterialTheme.colors.surface,
                    modifier = Modifier
                        .padding(8.dp)
                        .height(150.dp)
                        .fillMaxWidth()
                ) {
                    Row() {
                        Image(
                            painter = rememberImagePainter(data = movie.imageUrl, builder = {
                                crossfade(true)
                                placeholder(R.drawable.ic_placeholder_movie)
                            }),
                            contentDescription = null,
                            modifier = Modifier.requiredSize(150.dp),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.subtitle1.plus(
                                    TextStyle(
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    )
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis

                            )
                            Text(
                                text = movie.description,
                                style = MaterialTheme.typography.body1,
                                maxLines = 5,
                                overflow = TextOverflow.Ellipsis
                            )
                        }


                    }
                }
            }
        }
    }

    @Preview(name = "S9 Preview", widthDp = 360, heightDp = 740)
    @Composable
    fun MoviesPreview(
        @PreviewParameter(MoviesPreviewParameterProvider::class) listItems: List<MovieViewItem>
    ) {
        MoviesListView(listItems)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        viewModel.onViewInitialised()
    }

    private fun setupViews() {

        // observe the view states
        observeState(viewModel) {
            toggleProgressBar(it.showLoading)
        }

        // observe the view events
        observeViewEvent(viewModel) {
            // shows the corresponding error dialogs
            handleNetworkErrorEvent(it)
        }

        // For navigation we can create a navEvent using the SingleLiveEvent and observe the navigation events here.
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        viewModel.onConfigurationChanged(newConfig.locales.get(0) ?: LocaleUtils.getCurrentLocale())
    }
}

// View state
data class MoviesViewState(
    val showLoading: Boolean = false,
    val listItems: List<MovieViewItem> = arrayListOf()
)

data class MovieViewItem(val title: String, val description: String, val imageUrl: String)

class MoviesPreviewParameterProvider : PreviewParameterProvider<List<MovieViewItem>> {
    override val values = sequenceOf(
        arrayListOf(
            MovieViewItem("Spider-Man", "Peter Park is unmasked and no longer is able to separate his normal life", ""),
            MovieViewItem("Encanto", "The tale of an extraordinary family, the Madrigals, who live hidden in the mountains of Colombia, in a magical house", ""),
            MovieViewItem("Resident Evil: Welcome to Raccoon City", "Once the booming home of pharmaceutical giant Umbrella Corporation, Raccoon City is now a dying Midwestern town.", "")
        )
    )
}