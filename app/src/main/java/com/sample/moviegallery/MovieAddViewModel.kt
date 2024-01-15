package com.sample.moviegallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.moviegallery.api.MovieItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieAddViewModel : ViewModel() {
    private val movieRepository = MovieRepository.get()

    private val _movie: MutableStateFlow<MovieItem?> = MutableStateFlow(null)
    val movie: StateFlow<MovieItem?> = _movie.asStateFlow()

    init {
        viewModelScope.launch {
            _movie.value = MovieItem(
            imdbID = "",
            title = "",
            year = "",
            poster = ""
        )
        }
    }


//    var movie = MovieItem(
//        imdbID = "",
//        title = "",
//        year = "",
//        poster = ""
//    )

    fun updateMovie(onUpdate: (MovieItem) -> MovieItem) {
        _movie.update { oldMovieItem ->
            oldMovieItem?.let { onUpdate(it) }
        }
    }




}