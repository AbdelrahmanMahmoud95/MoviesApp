package com.abdelrahman.moviesapp.presentation.fragments.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdelrahman.moviesapp.data.models.Results
import com.abdelrahman.moviesapp.databinding.ItemMovieBinding

internal class MovieAdapter(private val trainings: List<Results?>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindData(trainings[position])
    }

    override fun getItemCount() = trainings.size

    inner class MovieViewHolder(private val itemMovieBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemMovieBinding.root) {
        fun bindData(results: Results?) {
            itemMovieBinding.movieDateTextView.text = results?.releaseDate ?: "---"
            itemMovieBinding.movieTitleTextView.text = results?.title ?: "---"
            itemMovieBinding.movieRatingTextView.text = results?.voteAverage.toString()
        }
    }
}