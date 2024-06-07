package com.abdelrahman.moviesapp.data.local.dao

import androidx.room.*
import com.abdelrahman.moviesapp.data.local.entity.FavoriteMovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM favorite ORDER BY release_date DESC")
    fun getAllMovies(): List<FavoriteMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movieEntity: FavoriteMovieEntity)

    @Delete
    fun deleteMovie(movieEntity: FavoriteMovieEntity)

    @Query("SELECT EXISTS (SELECT * FROM favorite WHERE id=:id)")
    fun movieExists(id: Int): Boolean
}