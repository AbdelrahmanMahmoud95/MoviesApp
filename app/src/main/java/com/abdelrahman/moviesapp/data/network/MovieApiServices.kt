package com.abdelrahman.moviesapp.data.network

import com.abdelrahman.moviesapp.data.models.MoviesResponse
import com.abdelrahman.moviesapp.data.models.Results
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiServices {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): MoviesResponse

    @GET("movie/{movieID}")
    suspend fun getMovieDetails(
        @Path("movieID") movieID: Int
    ): Results


}