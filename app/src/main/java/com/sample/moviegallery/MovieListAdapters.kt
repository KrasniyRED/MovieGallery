package com.sample.moviegallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sample.moviegallery.api.MovieItem
import com.sample.moviegallery.databinding.MovieListItemBinding
import com.sample.moviegallery.databinding.SearchListItemBinding

class MovieSearchHolder(
    private val binding: SearchListItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movieItem: MovieItem, onMovieClicked: (movie:MovieItem) ->Unit) {
        binding.imageView.load(movieItem.poster){
        //TODO Add Placeholder

        }
        binding.root.setOnClickListener {
            onMovieClicked(movieItem)
        }
        binding.movieTitle.text = movieItem.title
        binding.movieYear.text = movieItem.year
        binding.filmGenre.text = "" //TODO Need to grab genre from response, change it
    }
}

class MovieListHolder(
    private val binding: MovieListItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movieItem: MovieItem) {
        binding.imageView.load(movieItem.poster){
        //TODO Add Placeholder

        }
        binding.movieTitle.text = movieItem.title
        binding.movieYear.text = movieItem.year
        //TODO Add listener for checkbox

    }
}

class MovieSearchAdapter(
    private val movieItems: List<MovieItem>,
    private val onMovieClicked: (movie:MovieItem) -> Unit
) : RecyclerView.Adapter<MovieSearchHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieSearchHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = SearchListItemBinding.inflate(inflater, parent, false)
        return MovieSearchHolder(binding)
    }
    override fun onBindViewHolder(holder: MovieSearchHolder, position: Int) {
        val item = movieItems[position]
        holder.bind(item,onMovieClicked)
    }
    override fun getItemCount() = movieItems.size
}

class MovieListAdapter(
    private val movieItems: List<MovieItem>,
) : RecyclerView.Adapter<MovieListHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieListItemBinding.inflate(inflater,parent, false)
        return MovieListHolder(binding)
    }
    override fun onBindViewHolder(holder: MovieListHolder, position: Int) {
        val item = movieItems[position]
        holder.bind(item)
    }
    override fun getItemCount() = movieItems.size
}
