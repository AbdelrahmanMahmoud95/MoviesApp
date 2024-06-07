package com.abdelrahman.moviesapp.data.data_sources

import com.abdelrahman.moviesapp.data.local.entity.FavoriteMovieEntity


interface LocalDataSource {
    suspend fun getFavoriteMovies(): List<FavoriteMovieEntity>
    suspend fun movieExists(movieId: Int): Boolean
    suspend fun insertMovie(movie: FavoriteMovieEntity)
    suspend fun deleteMovie(movie: FavoriteMovieEntity)
}