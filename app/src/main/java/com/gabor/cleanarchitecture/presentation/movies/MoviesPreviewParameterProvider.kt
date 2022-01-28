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
