package com.sample.moviegallery

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

private const val TAG = "MovieSearchFragment"
class MovieSearchFragment : Fragment() {

    companion object {
        fun newInstance() = MovieSearchFragment()
    }

    private lateinit var viewModel: MovieSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieSearchViewModel::class.java)
        // TODO: Use the ViewModel
        val movieRepository = MovieRepository.get()
        viewLifecycleOwner.lifecycleScope.launch {
            val response = movieRepository.searchMovies("fast", "")
            Log.d(TAG, "Response received: $response")
        }


    }

}