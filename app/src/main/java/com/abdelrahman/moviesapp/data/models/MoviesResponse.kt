package com.abdelrahman.moviesapp.data.models

import com.google.gson.annotations.SerializedName

data class MoviesResponse (
    @SerializedName("results") val results : List<Results>,
)

data class Results (
    @SerializedName("id") val id : Int,
    @SerializedName("original_language") val language : String,
    @SerializedName("overview") val overview : String,
    @SerializedName("popularity") val popularity : Double,
    @SerializedName("poster_path") val posterPath : String,
    @SerializedName("release_date") val releaseDate : String,
    @SerializedName("title") val title : String,
    @SerializedName("vote_average") val voteAverage : Double,
    @SerializedName("vote_count") val voteCount : Int
)




