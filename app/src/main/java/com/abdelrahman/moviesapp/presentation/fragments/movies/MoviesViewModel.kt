package com.abdelrahman.moviesapp.presentation.fragments.movies

import androidx.lifecycle.viewModelScope
import com.abdelrahman.moviesapp.data.local.entity.FavoriteMovieEntity
import com.abdelrahman.moviesapp.data.models.Results
import com.abdelrahman.moviesapp.domain.use_cases.AddFavoriteUseCase
import com.abdelrahman.moviesapp.domain.use_cases.DeleteFavoriteUseCase
import com.abdelrahman.moviesapp.domain.use_cases.GetFavoritesUseCase
import com.abdelrahman.moviesapp.domain.use_cases.GetMoviesListUseCase
import com.abdelrahman.moviesapp.presentation.base.BaseViewModel
import com.abdelrahman.moviesapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesListUseCase: GetMoviesListUseCase,
    private val deleteFavorite: DeleteFavoriteUseCase,
    private val addFavorite: AddFavoriteUseCase,
    private val getFavorites: GetFavoritesUseCase,

    ) : BaseViewModel() {

    private val _nowPlayingMovies = MutableStateFlow(emptyList<Results>())
    val nowPlayingMovies get() = _nowPlayingMovies.asStateFlow()

    private val _favoriteMovies = MutableStateFlow(emptyList<FavoriteMovieEntity>())
    val favoriteMovies get() = _favoriteMovies.asStateFlow()

    init {
        initRequests()
        fetchFavoriteMovies()

    }

    private suspend fun fetchMoviesList() {
        getMoviesListUseCase(
        ).collect { response ->
            when (response) {
                is Resource.Success -> {
                    val movieList = response.data.results
                    _nowPlayingMovies.value += movieList
                    areResponsesSuccessful.add(true)
                    isInitial = false
                }

                is Resource.Error -> {
                    errorText = response.message
                    areResponsesSuccessful.add(false)
                }
            }
        }
    }

    fun removeMovieFromFavorites(movie: FavoriteMovieEntity) {
        viewModelScope.launch {
            deleteFavorite(movie = movie)
            fetchFavoriteMovies()
        }
    }

    fun addMovieToFavorites(movie: FavoriteMovieEntity) {
        viewModelScope.launch {
            addFavorite(movie = movie)
            fetchFavoriteMovies()
        }
    }

    fun fetchFavoriteMovies() {
        viewModelScope.launch {
            getFavorites().collect {
                _favoriteMovies.value = it as List<FavoriteMovieEntity>
            }
        }
    }

    internal fun initRequests() {
        viewModelScope.launch {
            coroutineScope {
                launch {
                    fetchMoviesList()
                }
            }
            setUiState()
        }
    }
}