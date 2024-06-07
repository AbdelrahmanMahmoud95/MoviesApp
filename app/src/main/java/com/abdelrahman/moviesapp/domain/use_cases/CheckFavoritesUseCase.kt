package com.abdelrahman.moviesapp.domain.use_cases

import com.abdelrahman.moviesapp.domain.repositories.MoviesRepository
import com.bumptech.glide.load.HttpException
import javax.inject.Inject

class CheckFavoritesUseCase @Inject constructor(
    private val movieRepository: MoviesRepository
) {
    suspend operator fun invoke(
        id: Int
    ): Boolean =
        try {
            movieRepository.movieExists(id)
        } catch (http: HttpException) {
            throw IllegalArgumentException(http.message ?: "something is wrong!, try again")
        }

}