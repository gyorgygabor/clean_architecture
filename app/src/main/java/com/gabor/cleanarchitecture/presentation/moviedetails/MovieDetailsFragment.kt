package com.gabor.cleanarchitecture.presentation.moviedetails

import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gabor.cleanarchitecture.presentation.theme.AppTheme
import com.gabor.cleanarchitecture.presentation.utils.MOVIE_DETAIL_PARAM
import com.gabor.cleanarchitecture.presentation.utils.statehandler.observeNetworkErrorEvents
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
                    viewState?.let { MoviesDetailsView(movieDetails = it.movieDetails) }
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
        // observe the network error events
        observeNetworkErrorEvents(viewModel)
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


}

// View state
data class MovieDetailsViewState(
    val showLoading: Boolean = false,
    val isCollectionLoaded: Boolean = false,
    val movieDetails: MovieDetailsViewItem
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

