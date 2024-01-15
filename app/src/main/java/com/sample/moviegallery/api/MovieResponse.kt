package com.sample.moviegallery.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class MovieResponse(
    @Json(name="Search") val movies: List<MovieItem>?,
    @Json(name="Error") val error: String?
)
