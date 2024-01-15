package com.sample.moviegallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sample.moviegallery.api.MovieItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "MovieSearchViewModel"
class MovieSearchViewModel(searchTitle:String,searchYear:String?) : ViewModel() {
    private val movieRepository = MovieRepository.get()
    private val _movieItems: MutableStateFlow<List<MovieItem>> =
        MutableStateFlow(emptyList())
    val galleryItems: StateFlow<List<MovieItem>>
        get() = _movieItems.asStateFlow()
    init {
        viewModelScope.launch {
            try {
                val items = searchPhotos(searchTitle,searchYear)
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
class MovieSearchViewModelFactory(
    private val searchTitle:String,
    private val searchYear:String?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieSearchViewModel(searchTitle,searchYear) as T
    }
}