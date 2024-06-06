package com.abdelrahman.moviesapp.data.repositories


import com.abdelrahman.moviesapp.data.data_sources.RemoteDataSource
import com.abdelrahman.moviesapp.domain.repositories.Repository
import javax.inject.Inject

class RepositoryImpl
@Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : Repository {


}