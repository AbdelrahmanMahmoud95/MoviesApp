package com.abdelrahman.moviesapp.domain.use_cases

import com.abdelrahman.moviesapp.domain.repositories.MoviesRepository
import com.bumptech.glide.load.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavorites @Inject constructor(
    private val movieRepository: MoviesRepository
) {
    operator fun invoke(): Flow<List<Any>> = flow {
        emit(
            try {
                movieRepository.getFavoriteMovies()
            } catch (http: HttpException) {
                throw IllegalArgumentException(http.message ?: "something is wrong!, try again")
            }
        )
    }
}