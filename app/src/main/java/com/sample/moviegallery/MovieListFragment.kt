package com.sample.moviegallery

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.moviegallery.api.MovieItem
import com.sample.moviegallery.databinding.FragmentMovieListBinding
import kotlinx.coroutines.launch

private const val TAG = "MovieListFragment"
class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null

    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val movieListViewModel: MovieListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)

        binding.movieList.layoutManager = LinearLayoutManager(context)
        binding.addBtn.setOnClickListener{
            findNavController().navigate(
                MovieListFragmentDirections.addMovie()
            )
        }
        binding.deleteButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                movieListViewModel.deleteMovies()
            }
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieListViewModel.movies.collect { movies ->
                    binding.movieList.adapter = MovieListAdapter(movies){movie,state->
                        if(state){
                            movieListViewModel.addToDelete(movie)
                            Log.d(TAG,"Fire add to delete list")
                        }
                        else{
                            movieListViewModel.deleteFromDelete(movie)
                            Log.d(TAG,"Fire delete from delete list")
                        }
                    }
                    if(movies.isEmpty()){
                        binding.imgText.visibility = View.VISIBLE
                        binding.serviceImg.visibility = View.VISIBLE
                    }
                    else{
                        binding.imgText.visibility = View.GONE
                        binding.serviceImg.visibility = View.GONE
                    }

                }

            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}