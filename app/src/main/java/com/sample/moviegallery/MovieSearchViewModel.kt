package com.sample.moviegallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.moviegallery.api.MovieItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "MovieSearchViewModel"
class MovieSearchViewModel : ViewModel() {
    private val movieRepository = MovieRepository.get()
    private val _movieItems: MutableStateFlow<List<MovieItem>> =
        MutableStateFlow(emptyList())
    val galleryItems: StateFlow<List<MovieItem>>
        get() = _movieItems.asStateFlow()
    init {
        viewModelScope.launch {
            try {
                val items = searchPhotos("fast","")
                _movieItems.value = items
            } catch (ex: Exception) {
                Log.e(TAG, "Failed to load films", ex)
            }
        }
    }
    private suspend fun searchPhotos(title: String, year: String?): List<MovieItem> {
        return movieRepository.searchMovies(title, year)
    }

}