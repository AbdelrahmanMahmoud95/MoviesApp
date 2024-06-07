package com.abdelrahman.moviesapp.presentation.fragments.movies
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.abdelrahman.moviesapp.R
import com.abdelrahman.moviesapp.data.local.entity.FavoriteMovieEntity
import com.abdelrahman.moviesapp.databinding.FragmentMoviesBinding
import com.abdelrahman.moviesapp.presentation.base.BaseFragment
import com.abdelrahman.moviesapp.utils.navigate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>(FragmentMoviesBinding::inflate) {
    override val viewModel: MoviesViewModel by viewModels()

    @Inject
    lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectFlows(
            listOf(
                ::collectNowPlayingMovies,
                ::collectUiState,
                ::collectFavoriteMovies
            )
        )

        binding.nowPlayingMoviesRecyclerView.adapter = movieAdapter

        movieAdapter.setOnItemClickListener {
            navigate(
                R.id.moviesFragment,
                MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(it)
            )
        }

        movieAdapter.setOnAddToFavoriteClickListener { movieId ->
            val isFavorite = viewModel.favoriteMovies.value.any { it.id == movieId }
            if (isFavorite) {
                removeMovie(FavoriteMovieEntity(id = movieId))
            } else {
                addMovie(FavoriteMovieEntity(id = movieId))
            }
        }
    }

    private suspend fun collectFavoriteMovies() {
        viewModel.favoriteMovies.collect { favoriteMovies ->
            movieAdapter.setFavoriteMovies(favoriteMovies.map { it.id })
        }
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

    override fun onResume() {
        super.onResume()
        viewModel.fetchFavoriteMovies()
    }

    private fun removeMovie(movie: FavoriteMovieEntity) {
        viewModel.removeMovieFromFavorites(movie)
    }

    private fun addMovie(movie: FavoriteMovieEntity) {
        viewModel.addMovieToFavorites(movie)
    }
}


