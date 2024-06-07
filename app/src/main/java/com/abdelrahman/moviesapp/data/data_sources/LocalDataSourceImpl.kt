package com.abdelrahman.moviesapp.data.data_sources

import com.abdelrahman.moviesapp.data.local.dao.MovieDao
import com.abdelrahman.moviesapp.data.local.entity.FavoriteMovieEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val movieDao : MovieDao) : LocalDataSource {


    override fun getFavoriteMovies(): List<FavoriteMovieEntity> {
        return  movieDao.getAllMovies()
    }

    override fun movieExists(movieId: Int): Boolean {
       return movieDao.movieExists(movieId)
    }

    override fun insertMovie(movie: FavoriteMovieEntity) {
        return movieDao.insertMovie(movie)
    }

    override fun deleteMovie(movie: FavoriteMovieEntity) {
        return movieDao.deleteMovie(movie)
    }


}