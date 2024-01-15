package com.sample.moviegallery.api

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class MovieItem(
    @PrimaryKey val imdbID : String,
    @Json(name="Title") val title: String,
    @Json(name="Year") val year: String?,
    @Json(name ="Poster") val poster: String,
)
