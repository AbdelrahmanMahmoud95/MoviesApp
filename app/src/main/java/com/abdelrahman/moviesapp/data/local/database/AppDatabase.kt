package com.abdelrahman.moviesapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abdelrahman.moviesapp.data.local.dao.MovieDao
import com.abdelrahman.moviesapp.data.local.entity.FavoriteMovieEntity

@Database(entities = [FavoriteMovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}