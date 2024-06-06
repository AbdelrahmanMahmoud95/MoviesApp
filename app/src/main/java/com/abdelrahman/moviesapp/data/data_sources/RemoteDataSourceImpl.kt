package com.abdelrahman.moviesapp.data.data_sources


import com.abdelrahman.moviesapp.data.network.MovieApiServices
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiServices: MovieApiServices
) : RemoteDataSource {
    override suspend fun getNowPlayingMovies() = apiServices.getNowPlayingMovies()
    override suspend fun getMovieDetails(movieID: String) = apiServices.getMovieDetails(movieID = movieID)

}