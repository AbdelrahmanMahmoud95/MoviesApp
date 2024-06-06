package com.abdelrahman.moviesapp.data.di


import com.abdelrahman.moviesapp.data.data_sources.RemoteDataSource
import com.abdelrahman.moviesapp.data.repositories.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideNewsRepository(
        remoteDataSource: RemoteDataSource,
    ): Repository {
        return RepositoryImpl(remoteDataSource)
    }

}