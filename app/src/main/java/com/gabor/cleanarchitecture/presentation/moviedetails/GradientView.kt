package com.gabor.cleanarchitecture.presentation.moviedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.gabor.cleanarchitecture.R

@Composable
fun GradientView() {
    Column {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(R.color.color_gradient),
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