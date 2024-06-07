package com.abdelrahman.moviesapp.data.di


import com.abdelrahman.moviesapp.data.data_sources.LocalDataSource
import com.abdelrahman.moviesapp.data.data_sources.LocalDataSourceImpl
import com.abdelrahman.moviesapp.data.data_sources.RemoteDataSource
import com.abdelrahman.moviesapp.data.data_sources.RemoteDataSourceImpl
import com.abdelrahman.moviesapp.data.local.dao.MovieDao
import com.abdelrahman.moviesapp.data.network.MovieApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideNewsRemoteDataSource(
        apiServices: MovieApiServices
    ): RemoteDataSource {
        return RemoteDataSourceImpl(apiServices)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        movieDao: MovieDao
    ): LocalDataSource {
        return LocalDataSourceImpl(movieDao)
    }

}