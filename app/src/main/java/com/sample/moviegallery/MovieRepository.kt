package com.sample.moviegallery

import android.content.Context
import com.sample.moviegallery.api.MovieItem
import com.sample.moviegallery.api.OmdBApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory



class MovieRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
){
    private val omdBApi: OmdBApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        omdBApi = retrofit.create(OmdBApi::class.java)
    }

    suspend fun searchMovies(title: String, year: String?): List<MovieItem> {
        val apiKey = "f549ee7c"
        val response = omdBApi.searchMovies(apiKey, title, year)
        if (response.isSuccessful) {
            val movies = response.body()?.movies
            if (movies != null) {
                return movies
            }
        }

        println("Unsuccessful response. Code: ${response.code()}")
        return emptyList()
    }



    companion object {
        private var INSTANCE: MovieRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = MovieRepository(context)
            }
        }
        fun get(): MovieRepository {
            return INSTANCE ?:
            throw IllegalStateException("MovieRepository must be initialized")
        }
    }
}