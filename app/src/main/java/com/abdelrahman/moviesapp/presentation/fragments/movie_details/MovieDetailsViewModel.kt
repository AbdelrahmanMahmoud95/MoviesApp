package com.abdelrahman.moviesapp.presentation.fragments.movie_details

import androidx.lifecycle.viewModelScope
import com.abdelrahman.moviesapp.data.models.Results
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
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : BaseViewModel() {

    private val _details = MutableStateFlow(Results.empty)
    val details get() = _details.asStateFlow()


    private fun fetchMovieDetails() {
        viewModelScope.launch {
            getMovieDetailsUseCase(detailId).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        (response.data).apply {
                            _details.value = this
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

    fun initRequests(movieId: Int) {
        detailId = movieId
        fetchMovieDetails()
    }
}