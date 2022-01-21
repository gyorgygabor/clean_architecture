package com.gabor.cleanarchitecture.domain.movies.entities

import com.google.gson.annotations.SerializedName

class VideosDTO(
    @SerializedName("results") val results: List<VideoDetailsDTO>,
)

class VideoDetailsDTO(
    @SerializedName("key") val ytKey: String,
    @SerializedName("official") val official: Boolean,
)