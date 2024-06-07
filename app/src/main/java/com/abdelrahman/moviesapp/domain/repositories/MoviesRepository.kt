package com.abdelrahman.moviesapp.domain.repositories

import com.abdelrahman.moviesapp.data.local.entity.FavoriteMovieEntity
import com.abdelrahman.moviesapp.data.models.MoviesResponse
import com.abdelrahman.moviesapp.data.models.Results
import com.abdelrahman.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {
    suspend fun getNowPlayingMovies(): Resource<MoviesResponse>
    suspend fun getMovieDetails(movieID: Int): Resource<Results>
    fun getFavoriteMovies(): Flow<List<FavoriteMovieEntity>>
    fun movieExists(movieId: Int): Boolean
    fun insertMovie(movie: FavoriteMovieEntity)
    fun deleteMovie(movie: FavoriteMovieEntity)
}