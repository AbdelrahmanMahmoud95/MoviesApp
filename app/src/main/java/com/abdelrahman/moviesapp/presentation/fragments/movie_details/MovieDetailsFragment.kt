package com.abdelrahman.moviesapp.presentation.fragments.movie_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.abdelrahman.moviesapp.R
import com.abdelrahman.moviesapp.databinding.FragmentMoviesBinding
import com.abdelrahman.moviesapp.presentation.base.BaseFragment
import com.abdelrahman.moviesapp.presentation.fragments.movies.MovieAdapter

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMoviesBinding>(FragmentMoviesBinding::inflate) {
    override val viewModel: MovieDetailsViewModel by viewModels()

    @Inject
    lateinit var movieAdapter: MovieAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectFlows(
            listOf(
                ::collectNowPlayingMovies,
                ::collectUiState

            )
        )

        binding.nowPlayingMoviesRecyclerView.adapter = movieAdapter

    }

    private suspend fun collectNowPlayingMovies() {
        viewModel.nowPlayingMovies.collect { nowPlayingMovies ->
            movieAdapter.differ.submitList(nowPlayingMovies)
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
                    viewModel.initRequests()
                }
            }
        }
    }
}
