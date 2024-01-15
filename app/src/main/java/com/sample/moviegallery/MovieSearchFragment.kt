package com.sample.moviegallery

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.moviegallery.databinding.FragmentMovieSearchBinding
import kotlinx.coroutines.launch

private const val TAG = "MovieSearchFragment"

class MovieSearchFragment : Fragment() {
    private var _binding: FragmentMovieSearchBinding? = null

    private val args: MovieSearchFragmentArgs by navArgs()

    private val movieSearchViewModel: MovieSearchViewModel by viewModels {
        MovieSearchViewModelFactory(args.searchTitle,args.searchYear)
    }

    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentMovieSearchBinding.inflate(inflater, container, false)
        binding.SearchList.layoutManager = LinearLayoutManager(context)//TODO Change layout manager if app in album orientation
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieSearchViewModel.galleryItems.collect { items ->
                    binding.SearchList.adapter = MovieSearchAdapter(items){item ->
                        setFragmentResult(REQUEST_KEY_DATE,
                            bundleOf(
                                SEARCHED_ID_KEY_DATE to item.imdbID,
                                SEARCHED_TITLE_KEY_DATE to item.title,
                                SEARCHED_YEAR_KEY_DATE to item.year,
                                SEARCHED_POSTER_KEY_DATE to item.poster
                                )
                        )
                        findNavController().navigate(
                           MovieSearchFragmentDirections.applySearched()
                        )


                    }
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val REQUEST_KEY_DATE = "REQUEST_KEY_DATE"
        const val SEARCHED_ID_KEY_DATE = "SEARCHED_ID_KEY_DATE"
        const val SEARCHED_TITLE_KEY_DATE = "SEARCHED_TITLE_KEY_DATE"
        const val SEARCHED_YEAR_KEY_DATE = "SEARCHED_YEAR_KEY_DATE"
        const val SEARCHED_POSTER_KEY_DATE = "SEARCHED_POSTER_KEY_DATE"
    }

}