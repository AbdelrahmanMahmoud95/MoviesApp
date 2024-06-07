package com.abdelrahman.moviesapp.presentation.fragments.movie_details

import androidx.lifecycle.viewModelScope
import com.abdelrahman.moviesapp.data.local.entity.FavoriteMovieEntity
import com.abdelrahman.moviesapp.data.models.Results
import com.abdelrahman.moviesapp.domain.use_cases.AddFavoriteUseCase
import com.abdelrahman.moviesapp.domain.use_cases.CheckFavoritesUseCase
import com.abdelrahman.moviesapp.domain.use_cases.DeleteFavoriteUseCase
import com.abdelrahman.moviesapp.domain.use_cases.GetMovieDetailsUseCase
import com.abdelrahman.moviesapp.presentation.UiState
import com.abdelrahman.moviesapp.presentation.base.BaseViewModel
import com.abdelrahman.moviesapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val checkFavorites: CheckFavoritesUseCase,
    private val deleteFavorite: DeleteFavoriteUseCase,
    private val addFavorite: AddFavoriteUseCase
) : BaseViewModel() {

    private val _details = MutableStateFlow(Results.empty)
    val details get() = _details.asStateFlow()

    private val _isInFavorites = MutableStateFlow(false)
    val isInFavorites get() = _isInFavorites.asStateFlow()

    private lateinit var favoriteMovie: FavoriteMovieEntity

    private fun fetchMovieDetails() {
        viewModelScope.launch {
            getMovieDetailsUseCase(detailId).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        (response.data).apply {
                            _details.value = this
                            favoriteMovie = FavoriteMovieEntity(
                                id = id
                            )
                        }
                        _uiState.value = UiState.successState()
                    }

                    is Resource.Error -> {
                        _uiState.value = UiState.errorState(errorText = response.message)
                    }
                }
            }
        }
    }
    private fun checkFavorites() {
        viewModelScope.launch {
            _isInFavorites.value = checkFavorites(detailId)
        }
    }

    fun updateFavorites() {
        viewModelScope.launch {
            if (_isInFavorites.value) {
                deleteFavorite(movie = favoriteMovie)
                _isInFavorites.value = false
            } else {
                addFavorite(movie = favoriteMovie)
                _isInFavorites.value = true
            }
        }
    }
    fun initRequests(movieId: Int) {
        detailId = movieId
        fetchMovieDetails()
        checkFavorites()
    }
}