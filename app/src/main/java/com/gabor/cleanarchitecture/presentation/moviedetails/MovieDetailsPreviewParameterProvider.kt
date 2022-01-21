package com.gabor.cleanarchitecture.presentation.moviedetails

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class MovieDetailsPreviewParameterProvider : PreviewParameterProvider<MovieDetailsViewItem> {
    override val values = sequenceOf(
        MovieDetailsViewItem(
            "Eternals",
            "The Eternals are a team of ancient aliens who have been living on Earth in secret for thousands of years. When an unexpected tragedy forces them out of the shadows, they are forced to reunite against mankind’s most ancient enemy, the Deviants.",
            "",
            "",
            "2021",
            3.2,
            2882,
            arrayListOf("Action", "Comedy", "Thriller", "Horror"),
            "EPZu5MA2uqI"
        )
    )
}