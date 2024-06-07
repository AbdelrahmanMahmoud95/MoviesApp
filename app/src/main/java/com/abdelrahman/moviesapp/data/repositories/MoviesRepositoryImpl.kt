package com.abdelrahman.moviesapp.data.repositories

import com.abdelrahman.moviesapp.data.data_sources.LocalDataSource
import com.abdelrahman.moviesapp.data.data_sources.RemoteDataSource
import com.abdelrahman.moviesapp.data.local.entity.FavoriteMovieEntity
import com.abdelrahman.moviesapp.data.models.MoviesResponse
import com.abdelrahman.moviesapp.data.models.Results
import com.abdelrahman.moviesapp.domain.repositories.MoviesRepository
import com.abdelrahman.moviesapp.utils.Resource
import com.abdelrahman.moviesapp.utils.SafeApiCall
import javax.inject.Inject

class MoviesRepositoryImpl
@Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val safeApiCall: SafeApiCall,
    private val localDataSource: LocalDataSource

) : MoviesRepository {
    override suspend fun getNowPlayingMovies(): Resource<MoviesResponse> = safeApiCall.execute {
        remoteDataSource.getNowPlayingMovies()
    }

    override suspend fun getMovieDetails(movieID: Int): Resource<Results> = safeApiCall.execute {
        remoteDataSource.getMovieDetails(movieID)
    }

    override suspend fun getFavoriteMovies(): List<FavoriteMovieEntity> {
       return localDataSource.getFavoriteMovies()
    }

    override suspend fun movieExists(movieId: Int): Boolean {
       return localDataSource.movieExists(movieId)
    }

    override suspend fun insertMovie(movie: FavoriteMovieEntity) {
        localDataSource.insertMovie(movie)
    }

    override suspend fun deleteMovie(movie: FavoriteMovieEntity) {
        localDataSource.deleteMovie(movie)
    }


}