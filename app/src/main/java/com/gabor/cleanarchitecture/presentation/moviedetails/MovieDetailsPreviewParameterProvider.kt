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
