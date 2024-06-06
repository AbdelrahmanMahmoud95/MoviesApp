package com.abdelrahman.moviesapp.data.data_sources


import com.abdelrahman.moviesapp.data.network.ApiServices
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiServices: ApiServices
) : RemoteDataSource {


}