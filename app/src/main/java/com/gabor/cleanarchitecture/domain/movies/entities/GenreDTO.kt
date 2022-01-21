package com.gabor.cleanarchitecture.domain.movies.entities

import com.google.gson.annotations.SerializedName

class GenreDTO(
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int
)
