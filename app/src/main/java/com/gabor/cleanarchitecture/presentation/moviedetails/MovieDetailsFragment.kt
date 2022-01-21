package com.gabor.cleanarchitecture.presentation.moviedetails

import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.compose.rememberImagePainter
import com.gabor.cleanarchitecture.R
import com.gabor.cleanarchitecture.presentation.theme.AppTheme
import com.gabor.cleanarchitecture.presentation.utils.LogCompositions
import com.gabor.cleanarchitecture.presentation.utils.MOVIE_DETAIL_PARAM
import com.gabor.cleanarchitecture.presentation.utils.VideoUtils
import com.gabor.cleanarchitecture.presentation.utils.statehandler.handleNetworkErrorEvent
import com.gabor.cleanarchitecture.presentation.utils.statehandler.observeState
import com.gabor.cleanarchitecture.presentation.utils.statehandler.observeViewEvent
import com.gabor.cleanarchitecture.presentation.utils.views.Star
import com.gabor.cleanarchitecture.presentation.utils.views.StarHalf
import com.gabor.cleanarchitecture.presentation.utils.views.StarOutline
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.InternalCoroutinesApi


/**
 * Movies list screen.
 */
const val TAG = "MoviesFragment"

@InternalCoroutinesApi
@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private val viewModel by viewModels<MoviesDetailsViewModel>()


    @ExperimentalAnimationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppTheme {
                    val viewState by viewModel.viewState.observeAsState()
                    viewState?.let { MoviesDetailsView(movieDetails = it) }
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        viewModel.onViewInitialised(arguments?.get(MOVIE_DETAIL_PARAM) as MovieDetailsViewItem?)
    }

    private fun setupListeners() {
        // observe the view states
        observeState(viewModel) {
            /**toggleProgressBar(it.showLoading) todo*/
        }

        // observe the view events
        observeViewEvent(viewModel) {
            // shows the corresponding error dialogs
            handleNetworkErrorEvent(it)
        }
    }


    @ExperimentalAnimationApi
    @Composable
    private fun MoviesDetailsView(movieDetails: MovieDetailsViewItem) {
        LogCompositions(TAG, "MoviesDetailsView called ${movieDetails}")
        val context = LocalContext.current
        Box(
            Modifier.fillMaxSize()
        ) {
            // Cover image
            Image(
                painter = rememberImagePainter(data = movieDetails.backdropImageUrl, builder = {
                    crossfade(true)
                    placeholder(R.drawable.ic_placeholder_movie)
                }),
                contentDescription = null,
                modifier = Modifier.requiredHeight(350.dp),
                contentScale = ContentScale.Crop
            )
            // Movie description
            Column {
                Spacer(modifier = Modifier.height(200.dp))
                Box() {
                    Gradient()
                    Column() {
                        Column(modifier = Modifier.padding(start = 200.dp, top = 50.dp)) {
                            // Title
                            Text(
                                text = movieDetails.title,
                                color = Color.White,
                                fontSize = 20.sp
                            )

                            // Star rating
                            RatingView(movieDetails.voteAvg)
                            // Imdb rating
                            Row(
                                modifier = Modifier.wrapContentHeight(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    modifier = Modifier.size(30.dp),
                                    painter = painterResource(id = R.drawable.ic_imdb),
                                    tint = Color.White,
                                    contentDescription = null // decorative element
                                )
                                Text(
                                    text = " " + movieDetails.voteAvg.toString(),
                                    color = Color.White,
                                    fontSize = 14.sp

                                )
                            }
                            // Date and category
                            Text(
                                text = movieDetails.releaseDate + " - " + movieDetails.genre.joinToString(
                                    "|"
                                ),
                                color = Color.White,
                                fontSize = 14.sp
                            )
                            // Trailer button
                            Button(
                                onClick = {
                                    VideoUtils.watchYoutubeVideo(
                                        context,
                                        movieDetails.ytTrailerKey
                                    )
                                },
                                // Uses ButtonDefaults.ContentPadding by default
                                contentPadding = PaddingValues(
                                    start = 20.dp,
                                    top = 12.dp,
                                    end = 20.dp,
                                    bottom = 12.dp
                                )
                            ) {
                                // Inner content including an icon and a text label
                                Text("Trailer")
                            }

                        }

                        Text(
                            text = "Storyline",
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(start = 25.dp, top = 25.dp)
                        )
                        Text(
                            text = movieDetails.description,
                            color = Color.White,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(25.dp)
                        )
                    }
                }


            }
            // Poster
            Column() {
                Spacer(modifier = Modifier.height(200.dp))
                Image(
                    painter = rememberImagePainter(data = movieDetails.posterImageUrl, builder = {
                        crossfade(true)
                        placeholder(R.drawable.ic_placeholder_movie)
                    }),
                    contentDescription = "null",
                    modifier = Modifier
                        .requiredWidth(175.dp)
                        .padding(start = 20.dp),
                    contentScale = ContentScale.Fit,
                )

            }

        }
    }

    @Composable
    private fun RatingView(rating: Double) {
        val ratingValue: Double = rating / 2 // imdb rating to 5 star rating
        Row() {
            val filledStarsCount = ratingValue.toInt()
            var addedStarsCount = 0

            // draw fill stars
            for (i in 1..filledStarsCount) {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = null,
                    tint = colorResource(R.color.color_star)
                )
                ++addedStarsCount
            }

            //draw half star
            if (filledStarsCount < ratingValue) {
                Icon(
                    Icons.Outlined.StarHalf,
                    contentDescription = null,
                    tint = colorResource(R.color.color_star)
                )
                ++addedStarsCount
            }

            // draw outlined stars
            while (addedStarsCount < 5) {
                Icon(
                    Icons.Outlined.StarOutline,
                    contentDescription = null,
                    tint = colorResource(R.color.color_star)
                )
                ++addedStarsCount
            }
        }
    }

    @ExperimentalAnimationApi
    @Preview(
        name = "MovieDetailsPreview Dark",
        showBackground = true,
        device = Devices.PIXEL_4_XL,
        uiMode = Configuration.UI_MODE_NIGHT_YES
    )
    @Composable
    fun MovieDetailsPreviewDark(
        @PreviewParameter(MovieDetailsPreviewParameterProvider::class) movieDetails: MovieDetailsViewItem
    ) {
        MoviesDetailsView(movieDetails)
    }

    @ExperimentalAnimationApi
    @Preview(name = "MovieDetailsPreview White", showBackground = true, device = Devices.PIXEL_4_XL)
    @Composable
    fun MovieDetailsPreview(
        @PreviewParameter(MovieDetailsPreviewParameterProvider::class) movieDetails: MovieDetailsViewItem
    ) {
        MoviesDetailsView(movieDetails)
    }

    @Composable
    fun Gradient() {
        Column() {

            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0x5201A17),
                                MaterialTheme.colors.surface
                            )
                        )
                    )
                    .requiredHeight(60.dp)
                    .fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colors.surface)
                    .fillMaxHeight()
                    .fillMaxWidth()
            )
        }

    }
}

// View state
data class MovieDetailsViewState(
    val showLoading: Boolean = false,
    val isCollectionLoaded: Boolean = false,
    val listItems: MovieDetailsViewItem
)

@Keep
@Parcelize
data class MovieDetailsViewItem(
    val title: String,
    val description: String,
    val posterImageUrl: String,
    val backdropImageUrl: String,
    val releaseDate: String,
    val voteAvg: Double,
    val voteNr: Int,
    val genre: List<String>,
    val ytTrailerKey: String
) : Parcelable

