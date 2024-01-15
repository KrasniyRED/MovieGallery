package com.sample.moviegallery

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.sample.moviegallery.api.MovieItem
import com.sample.moviegallery.databinding.FragmentMovieAddBinding
import kotlinx.coroutines.launch
import java.util.Date

class MovieAddFragment: Fragment() {

    private val movieAddViewModel:MovieAddViewModel by viewModels()


    private var _binding: FragmentMovieAddBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentMovieAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    movieAddViewModel.movie.collect { movie ->
                        movie?.let { updateUi(it) }
                    }
                }
            }

            setFragmentResultListener(
                MovieSearchFragment.REQUEST_KEY_DATE
            ) { _, bundle ->
                movieAddViewModel.updateMovie { it.copy(
                    imdbID = bundle.getSerializable(MovieSearchFragment.SEARCHED_ID_KEY_DATE) as String,
                    title = bundle.getSerializable(MovieSearchFragment.SEARCHED_TITLE_KEY_DATE) as String,
                    year = bundle.getSerializable(MovieSearchFragment.SEARCHED_YEAR_KEY_DATE) as String,
                    poster = bundle.getSerializable(MovieSearchFragment.SEARCHED_POSTER_KEY_DATE) as String) }
                
            }

            searchBtn.setOnClickListener {
                findNavController().navigate(
                    MovieAddFragmentDirections.searchMovie(movieTitle.text.toString(),movieYear.text.toString())
                )
            }

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi(movie: MovieItem) {
        binding.apply {
            movieTitle.setText(movie.title)
            movieYear.setText(movie.year)
            moviePoster.load(movie.poster){

            }
        }
    }



}