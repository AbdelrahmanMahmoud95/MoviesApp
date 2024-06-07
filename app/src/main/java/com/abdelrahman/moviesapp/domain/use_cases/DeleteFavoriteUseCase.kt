package com.abdelrahman.moviesapp.domain.use_cases


import com.abdelrahman.moviesapp.data.local.entity.FavoriteMovieEntity
import com.abdelrahman.moviesapp.domain.repositories.MoviesRepository
import com.bumptech.glide.load.HttpException
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val movieRepository: MoviesRepository,
) {
    suspend operator fun invoke(
        movie: FavoriteMovieEntity? = null,
    ) {
        try {
            movie?.let { movieRepository.deleteMovie(it) }
        } catch (http: HttpException) {
            throw IllegalArgumentException(http.message ?: "something is wrong!, try again")
        }
    }
}