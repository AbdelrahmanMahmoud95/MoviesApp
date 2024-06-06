package com.abdelrahman.moviesapp.domain.use_cases

import com.abdelrahman.moviesapp.data.models.Results
import com.abdelrahman.moviesapp.domain.repositories.MoviesRepository
import com.abdelrahman.moviesapp.utils.Resource
import com.bumptech.glide.load.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val movieRepository: MoviesRepository) {

    operator fun invoke(
        id: Int
        ): Flow<Resource<Results>> = flow {
        emit(
            try {
                movieRepository.getMovieDetails(id)
            } catch (http: HttpException) {
                throw IllegalArgumentException(http.message ?: "something is wrong!, try again")
            }
        )


    }
}