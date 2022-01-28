/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gabor.cleanarchitecture.presentation.moviedetails

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.gabor.cleanarchitecture.R
import com.gabor.cleanarchitecture.presentation.utils.LogCompositions
import com.gabor.cleanarchitecture.presentation.utils.VideoUtils

@ExperimentalAnimationApi
@Composable
fun MoviesDetailsView(movieDetails: MovieDetailsViewItem) {
    LogCompositions(TAG, "MoviesDetailsView called $movieDetails")
    val context = LocalContext.current
    Box(
        Modifier.fillMaxSize()
    ) {
        // Cover image
        Image(
            painter = rememberImagePainter(
                data = movieDetails.backdropImageUrl,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.ic_placeholder_movie)
                }
            ),
            contentDescription = null,
            modifier = Modifier.requiredHeight(350.dp),
            contentScale = ContentScale.Crop
        )
        // Movie description
        Column {
            Spacer(modifier = Modifier.height(200.dp))
            Box() {
                GradientView()
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
                            contentPadding = PaddingValues(
                                start = 20.dp,
                                top = 12.dp,
                                end = 20.dp,
                                bottom = 12.dp
                            )
                        ) {
                            Text(stringResource(R.string.label_trailer))
                        }
                    }
                    Text(
                        text = stringResource(R.string.label_storyline),
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
                painter = rememberImagePainter(
                    data = movieDetails.posterImageUrl,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.ic_placeholder_movie)
                    }
                ),
                contentDescription = "null",
                modifier = Modifier
                    .requiredWidth(175.dp)
                    .padding(start = 20.dp),
                contentScale = ContentScale.Fit,
            )
        }
    }
}
