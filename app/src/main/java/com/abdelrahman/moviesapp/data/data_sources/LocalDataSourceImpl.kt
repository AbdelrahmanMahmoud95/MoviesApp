package com.abdelrahman.moviesapp.data.data_sources

import com.abdelrahman.moviesapp.data.local.dao.MovieDao
import com.abdelrahman.moviesapp.data.local.entity.FavoriteMovieEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val movieDao : MovieDao) : LocalDataSource {


    override suspend fun getFavoriteMovies(): List<FavoriteMovieEntity> {
        return  movieDao.getAllMovies()
    }

    override suspend fun movieExists(movieId: Int): Boolean {
       return movieDao.movieExists(movieId)
    }

    override suspend fun insertMovie(movie: FavoriteMovieEntity) {
        return movieDao.insertMovie(movie)
    }

    override suspend fun deleteMovie(movie: FavoriteMovieEntity) {
        return movieDao.deleteMovie(movie)
    }


}