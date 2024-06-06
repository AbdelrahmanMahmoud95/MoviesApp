package com.abdelrahman.moviesapp.data.di


import com.abdelrahman.moviesapp.data.repositories.MoviesRepositoryImpl
import com.abdelrahman.moviesapp.domain.repositories.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindMovieRepository(repository: MoviesRepositoryImpl): MoviesRepository


}