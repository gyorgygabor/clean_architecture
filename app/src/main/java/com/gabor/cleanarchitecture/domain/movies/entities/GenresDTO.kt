package com.gabor.cleanarchitecture.domain.movies.entities

import com.google.gson.annotations.SerializedName

data class GenresDTO(
    @SerializedName("genres") val genres: List<GenreDTO>,
)
