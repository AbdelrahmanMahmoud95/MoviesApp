package com.abdelrahman.moviesapp.data.di


import com.abdelrahman.moviesapp.data.data_sources.RemoteDataSource
import com.abdelrahman.moviesapp.data.data_sources.RemoteDataSourceImpl
import com.abdelrahman.moviesapp.data.network.ApiServices
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
        apiServices: ApiServices
    ): RemoteDataSource {
        return RemoteDataSourceImpl(apiServices)
    }

}