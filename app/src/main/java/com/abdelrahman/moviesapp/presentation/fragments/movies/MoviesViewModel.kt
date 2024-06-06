package com.abdelrahman.moviesapp.presentation.fragments.movies

import androidx.lifecycle.viewModelScope
import com.abdelrahman.moviesapp.data.models.MoviesResponse
import com.abdelrahman.moviesapp.data.models.Results
import com.abdelrahman.moviesapp.domain.use_cases.GetMoviesListUseCase
import com.abdelrahman.moviesapp.presentation.UiState
import com.abdelrahman.moviesapp.presentation.base.BaseViewModel
import com.abdelrahman.moviesapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesListUseCase: GetMoviesListUseCase
) : BaseViewModel() {

    private val _nowPlayingMovies = MutableStateFlow(emptyList<Results>())
    val nowPlayingMovies get() = _nowPlayingMovies.asStateFlow()

    init {
        initRequests()
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


    fun getNowPlayingMovies() {
        _uiState.value = UiState.loadingState(isInitial)
        _nowPlayingMovies.value = emptyList()

        viewModelScope.launch {
            coroutineScope { fetchMoviesList() }
            setUiState()
        }
    }

    private fun initRequests() {
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