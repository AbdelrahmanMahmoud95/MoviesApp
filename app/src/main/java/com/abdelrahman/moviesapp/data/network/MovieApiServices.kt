package com.abdelrahman.moviesapp.data.network

import com.abdelrahman.moviesapp.data.models.MoviesResponse
import com.abdelrahman.moviesapp.data.models.Results
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiServices {
    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovies(): MoviesResponse

    @GET("3/movie/{movieID}")
    suspend fun getMovieDetails(
        @Path("movieID") movieID: String
    ): Results


}