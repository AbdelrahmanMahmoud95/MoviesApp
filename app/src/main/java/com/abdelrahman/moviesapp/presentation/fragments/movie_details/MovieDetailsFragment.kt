package com.abdelrahman.moviesapp.presentation.fragments.movie_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.abdelrahman.moviesapp.R
import com.abdelrahman.moviesapp.databinding.FragmentMovieDetailsBinding
import com.abdelrahman.moviesapp.presentation.base.BaseFragment
import com.abdelrahman.moviesapp.utils.Constants
import com.bumptech.glide.Glide

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsFragment :
    BaseFragment<FragmentMovieDetailsBinding>(FragmentMovieDetailsBinding::inflate) {
    override val viewModel: MovieDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initRequests(detailId)
        binding.backTextView.setOnClickListener {
            requireActivity().onBackPressed()
        }
        checkFavorite()
        collectFlows(
            listOf(
                ::collectDetails,
                ::collectUiState
            )
        )
    }

    @SuppressLint("SetTextI18n")
    private suspend fun collectDetails() {
        viewModel.details.collect { details ->
            Glide.with(binding.movieBannerImageView.context)
                .load(Constants.IMAGE_URL + details.posterPath)
                .into(binding.movieBannerImageView)
            binding.movieDateTextView.text = details.releaseDate
            binding.movieTitleTextView.text = details.title
            binding.movieDescriptionTextView.text = details.overview
            binding.movieRatingTextView.text = details.voteAverage.toString()
            binding.movieLanguageTextView.text = "Language: " + details.language
        }
    }

    private fun checkFavorite() {
        if (viewModel.isInFavorites.value) {
            binding.favoriteImageView.setImageResource(R.drawable.favorite_selected)
        } else {
            binding.favoriteImageView.setImageResource(R.drawable.favorite_unselected)
        }

        binding.favoriteImageView.setOnClickListener {
            viewModel.updateFavorites()
            if (viewModel.isInFavorites.value) {
                binding.favoriteImageView.setImageResource(R.drawable.favorite_selected)
            } else {
                binding.favoriteImageView.setImageResource(R.drawable.favorite_unselected)
            }
        }

    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) showSnackbar(
                message = state.errorText!!,
                actionText = getString(R.string.button_retry),
                anchor = true
            ) {
                viewModel.retryConnection {
                    viewModel.initRequests(detailId)
                }
            }
        }
    }
}
