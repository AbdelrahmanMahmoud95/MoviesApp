package com.abdelrahman.moviesapp.data.repositories

import com.abdelrahman.moviesapp.data.data_sources.RemoteDataSource
import com.abdelrahman.moviesapp.data.models.MoviesResponse
import com.abdelrahman.moviesapp.data.models.Results
import com.abdelrahman.moviesapp.domain.repositories.MoviesRepository
import com.abdelrahman.moviesapp.utils.Resource
import com.abdelrahman.moviesapp.utils.SafeApiCall
import javax.inject.Inject

class MoviesRepositoryImpl
@Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val safeApiCall: SafeApiCall

) : MoviesRepository {
    override suspend fun getNowPlayingMovies(): Resource<MoviesResponse> = safeApiCall.execute {
        remoteDataSource.getNowPlayingMovies()
    }

    override suspend fun getMovieDetails(movieID: String): Resource<Results> = safeApiCall.execute {
        remoteDataSource.getMovieDetails(movieID)
    }


}