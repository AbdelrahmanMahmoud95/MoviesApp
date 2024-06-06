package com.abdelrahman.moviesapp.data.data_sources

import com.abdelrahman.moviesapp.data.models.MoviesResponse
import com.abdelrahman.moviesapp.data.models.Results


interface RemoteDataSource {
    suspend fun getNowPlayingMovies(): MoviesResponse
    suspend fun getMovie(movieID: String): Results

}