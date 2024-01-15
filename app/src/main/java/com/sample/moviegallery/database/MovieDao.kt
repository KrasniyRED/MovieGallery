package com.sample.moviegallery.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sample.moviegallery.api.MovieItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movieitem")
    fun getMovies(): Flow<List<MovieItem>>
    @Insert
    suspend fun addMovie(movie: MovieItem)

}