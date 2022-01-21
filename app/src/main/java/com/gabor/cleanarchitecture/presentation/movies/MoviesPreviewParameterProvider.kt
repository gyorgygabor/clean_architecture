package com.gabor.cleanarchitecture.presentation.movies

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class MoviesPreviewParameterProvider : PreviewParameterProvider<List<MovieViewItem>> {
    override val values = sequenceOf(
        arrayListOf(
            MovieViewItem(
                1,
                "Spider-Man",
                "Peter Park is unmasked and no longer is able to separate his normal life",
                ""
            ),
            MovieViewItem(
                2,
                "Encanto",
                "The tale of an extraordinary family, the Madrigals, who live hidden in the mountains of Colombia, in a magical house",
                ""
            ),
            MovieViewItem(
                3,
                "Resident Evil: Welcome to Raccoon City",
                "Once the booming home of pharmaceutical giant Umbrella Corporation, Raccoon City is now a dying Midwestern town.",
                ""
            )
        )
    )
}