package com.abdelrahman.moviesapp.data.repositories

import com.abdelrahman.moviesapp.data.data_sources.LocalDataSource
import com.abdelrahman.moviesapp.data.data_sources.RemoteDataSource
import com.abdelrahman.moviesapp.data.local.entity.FavoriteMovieEntity
import com.abdelrahman.moviesapp.data.models.MoviesResponse
import com.abdelrahman.moviesapp.data.models.Results
import com.abdelrahman.moviesapp.domain.repositories.MoviesRepository
import com.abdelrahman.moviesapp.utils.Resource
import com.abdelrahman.moviesapp.utils.SafeApiCall
import kotlinx.coroutines.flow.Flow
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

    override fun getFavoriteMovies(): Flow<List<FavoriteMovieEntity>> {
       return localDataSource.getFavoriteMovies()
    }

    override fun movieExists(movieId: Int): Boolean {
       return localDataSource.movieExists(movieId)
    }

    override fun insertMovie(movie: FavoriteMovieEntity) {
        localDataSource.insertMovie(movie)
    }

    override fun deleteMovie(movie: FavoriteMovieEntity) {
        localDataSource.deleteMovie(movie)
    }


}