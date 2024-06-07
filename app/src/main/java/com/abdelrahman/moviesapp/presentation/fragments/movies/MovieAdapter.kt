package com.abdelrahman.moviesapp.presentation.fragments.movies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdelrahman.moviesapp.R
import com.abdelrahman.moviesapp.data.models.Results
import com.abdelrahman.moviesapp.databinding.ItemMovieBinding
import com.abdelrahman.moviesapp.utils.Constants.IMAGE_URL
import com.bumptech.glide.Glide
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class MovieAdapter @Inject constructor() : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Results>() {
        override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)
    private val favoriteMovieIds = mutableSetOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(results: Results) {
            binding.movieDateTextView.text = results.releaseDate
            binding.movieTitleTextView.text = results.title
            binding.movieRatingTextView.text = String.format("%.1f", results.voteAverage).toDouble().toString()
            Glide.with(binding.movieImageView.context).load(IMAGE_URL + results.posterPath).into(binding.movieImageView)

            updateFavoriteIcon(results.id)

            binding.root.setOnClickListener {
                onItemClick?.let {
                    it(results.id)
                }
            }

            binding.favoriteImageView.setOnClickListener {
                toggleFavorite(results.id)
                onAddToFavorite?.let {
                    it(results.id)
                }
            }
        }

        private fun updateFavoriteIcon(movieId: Int) {
            if (favoriteMovieIds.contains(movieId)) {
                binding.favoriteImageView.setImageResource(R.drawable.favorite_selected)
            } else {
                binding.favoriteImageView.setImageResource(R.drawable.favorite_unselected)
            }
        }

        private fun toggleFavorite(movieId: Int) {
            if (favoriteMovieIds.contains(movieId)) {
                favoriteMovieIds.remove(movieId)
            } else {
                favoriteMovieIds.add(movieId)
            }
            updateFavoriteIcon(movieId)
        }
    }

    private var onAddToFavorite: ((Int) -> Unit)? = null
    private var onItemClick: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClick = listener
    }

    fun setOnAddToFavoriteClickListener(listener: (Int) -> Unit) {
        onAddToFavorite = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFavoriteMovies(favoriteMovies: List<Int>) {
        favoriteMovieIds.clear()
        favoriteMovieIds.addAll(favoriteMovies)
        notifyDataSetChanged()
    }
}

