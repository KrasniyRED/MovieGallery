package com.sample.moviegallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.moviegallery.api.MovieItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieListViewModel : ViewModel() {

    private val movieRepository = MovieRepository.get()

    private val _movies: MutableStateFlow<List<MovieItem>> = MutableStateFlow(emptyList())
    val tasks: StateFlow<List<MovieItem>>
        get() = _movies.asStateFlow()

    init {
        viewModelScope.launch {
            movieRepository.getMovies().collect {
                _movies.value = it
            }
        }
    }



}