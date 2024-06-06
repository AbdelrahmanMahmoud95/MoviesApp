package com.abdelrahman.moviesapp.presentation.fragments.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdelrahman.moviesapp.data.models.Results
import com.abdelrahman.moviesapp.databinding.ItemMovieBinding
import com.abdelrahman.moviesapp.utils.Constants.IMAGE_URL
import com.bumptech.glide.Glide
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class MovieAdapter @Inject constructor(
) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private val callback = object : DiffUtil.ItemCallback<Results>() {
        override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val article = differ.currentList[position]

        holder.bind(article)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class MovieViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(results: Results) {
            binding.movieDateTextView.text = results.releaseDate
            binding.movieTitleTextView.text = results.title
            binding.movieRatingTextView.text = results.voteAverage.toString()
            Glide.with(binding.movieImageView.context).load(IMAGE_URL + results.posterPath)
                .into(binding.movieImageView)

            binding.root.setOnClickListener {
                onItemClick?.let {
                    it(results.id)
                }
            }

            binding.favoriteImageView.setOnClickListener {
                onBookmarkClick?.let {
                    it(results)
                }
            }
        }

    }

    private var onBookmarkClick: ((Results) -> Unit)? = null

    private var onItemClick: ((Int) -> Unit)? = null
    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClick = listener
    }
}