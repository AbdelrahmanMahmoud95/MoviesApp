package com.abdelrahman.moviesapp.data.data_sources

import com.abdelrahman.moviesapp.data.local.entity.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow


interface LocalDataSource {
    fun getFavoriteMovies(): Flow<List<FavoriteMovieEntity>>
    fun movieExists(movieId: Int): Boolean
    fun insertMovie(movie: FavoriteMovieEntity)
    fun deleteMovie(movie: FavoriteMovieEntity)
}