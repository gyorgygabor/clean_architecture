package com.gabor.cleanarchitecture.presentation.moviedetails

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.gabor.cleanarchitecture.R
import com.gabor.cleanarchitecture.presentation.utils.views.StarHalf
import com.gabor.cleanarchitecture.presentation.utils.views.StarOutline

@Composable
fun RatingView(rating: Double) {
    val ratingValue: Double = rating / 2 // imdb rating to 5 star rating
    Row {
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